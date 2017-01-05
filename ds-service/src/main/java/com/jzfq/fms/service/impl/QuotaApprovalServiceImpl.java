package com.jzfq.fms.service.impl;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.strategy.HttpGetData;
import com.jzfq.fms.common.util.ServiceValidate;
import com.jzfq.fms.dao.CustomerMapper;
import com.jzfq.fms.dao.QuotaApprovalMapper;
import com.jzfq.fms.domain.Customer;
import com.jzfq.fms.domain.QuotaApproval;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.IQuotaApprovalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huyinglin on 2016/11/23.
 */
@Service
public class QuotaApprovalServiceImpl extends HttpGetData implements IQuotaApprovalService{

    private static ExecutorService noticeRiskThreadPool = Executors.newFixedThreadPool(10);

    private Logger logger= LoggerFactory.getLogger(QuotaApprovalServiceImpl.class);

    @Autowired
    private QuotaApprovalMapper quotaApprovalMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public PageList<QuotaApproval> queryQuotaApprovalList(PageVo vo) {
        return quotaApprovalMapper.findQuotaApprovalList(vo,vo.getPageable());
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertQuotaApprovalAndCustomer(QuotaApproval quotaApproval) {
        logger.info("==接受风控系统传入额度审批数据和用户数据开始==");
        ServiceValidate.isTrue(!StringUtils.isEmpty(quotaApproval),"额度审批记录对象不能为空！");
        ServiceValidate.notNull(quotaApproval.getOrderId(),"订单号不能为空！");
        ServiceValidate.notNull(quotaApproval.getCustomerId(),"用户ID不能为空！");
        ServiceValidate.notNull(quotaApproval.getUsername(),"用户名不能为空！");
        ServiceValidate.notNull(quotaApproval.getPhone(),"手机号不能为空！");
        ServiceValidate.notNull(quotaApproval.getIdCard(),"身份证号不能为空！");

        quotaApproval.setCreateTime(new Date(System.currentTimeMillis()));
        //插入额度审批
        boolean isQuotaApproval=quotaApprovalMapper.insert(quotaApproval)>0?true:false;
        //插入用户信息
        Customer customer = new Customer();
        customer.setCustomerId(quotaApproval.getCustomerId());//用户ID
        customer.setUsername(quotaApproval.getUsername());//用户名
        customer.setPhone(quotaApproval.getPhone());//手机号
        customer.setIdCard(quotaApproval.getIdCard());//身份证号
        customer.setRecommendationCode(quotaApproval.getRecommendationCode());//推荐码
        customer.setCustomerType(quotaApproval.getCustomerType());//客户类型
        customer.setCreateTime(quotaApproval.getCreateTime());//注册时间 接受风控传过来的
        customer.setWhiteLine(quotaApproval.getWhiteLine());//白条额度
        boolean isCustomer=customerMapper.insert(customer)>0?true:false;
        if(isQuotaApproval&&isCustomer){
            logger.info("==接受风控系统传入额度审批数据和用户数据并插入额度审批表和用户表成功结束==");
            //通知风控中心
            noticeRisk(quotaApproval);
            return true;
        }
        logger.info("==插入额度审批和用户数据异常==");
        return false;
    }
    @Override
    public void noticeRisk(QuotaApproval quotaApproval){
        try {
            noticeRiskThreadPool.submit(new NoticeRiskServiceRunner(quotaApproval));
        }catch (Exception e){
            String strErrorMsg="风控推送失败，订单号="+quotaApproval.getOrderId();
            logger.error(strErrorMsg,e);
        }
    }

    @Override
    protected Object processData(Object obj) {
        return null;
    }

    private class NoticeRiskServiceRunner implements Runnable{
        private QuotaApproval quotaApproval;

        public NoticeRiskServiceRunner(QuotaApproval quotaApproval) {
            this.quotaApproval = quotaApproval;
        }

        @Override
        public void run() {
            try {
                //TODO:调用风控的接口，推送审批单quotaApproval
                Map<Object,Object> map = new HashMap<>();
                String url="";
                Object msg = doPost(url,map);
                if(msg!=null){
                    logger.info("调用风控返回信息："+msg.toString());
                }else{
                    logger.info("调用风控接口异常");
                }

                //更新数据库is_pushed是否推送成功 0是 1否
                quotaApproval.setIsPushed(1);
                logger.info("quotaApproval="+quotaApproval);
                quotaApprovalMapper.updateIsPushed(quotaApproval);
            }catch (Exception e){
                String strErrorMsg="审批单推送风控中心失败：订单号="+quotaApproval.getOrderId();
                logger.error(strErrorMsg,e);
                return;
            }
        }
    }

    @Override
    public boolean updateQuotaApprovalAuditStatusByOrderId(String orderId,String auditStatus) {
        logger.info("==接受风控传入的订单号和审核状态更新额度审批表开始==");
        ServiceValidate.notNull(orderId,"订单号不能为空！");
        ServiceValidate.notNull(auditStatus,"审核状态不能为空");
        QuotaApproval quotaApproval=new QuotaApproval();
        quotaApproval.setOrderId(orderId);
        quotaApproval.setAuditStatus(auditStatus);
        boolean result=quotaApprovalMapper.updateQuotaApprovalByOrderId(quotaApproval)>0?true:false;
        return result;
    }

    @Override
    public QuotaApproval selectQuotaApprovalById(Integer id) {
        return quotaApprovalMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateQuotaApprovalMaxAcount(QuotaApproval quotaApproval) {
        logger.info("更新额度审批表开始：是否激活账户、最大额度");
        boolean result = quotaApprovalMapper.updateQuotaApprovalMaxAcount(quotaApproval)>0?true:false;
        return result;
    }

    @Override
    public List<QuotaApproval> queryQuotaApprovalByIsPushed() {
        return quotaApprovalMapper.queryQuotaApprovalByIsPushed();
    }

}
