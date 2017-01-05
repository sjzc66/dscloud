package com.jzfq.fms.service.impl;

import com.jzfq.fms.common.PlanClient;
import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.common.Pageable;
import com.jzfq.fms.common.constants.Constants;
import com.jzfq.fms.common.enums.CapitalType;
import com.jzfq.fms.common.enums.OrderType;
import com.jzfq.fms.common.enums.RepayState;
import com.jzfq.fms.common.enums.RepayType;
import com.jzfq.fms.common.strategy.OverDueClient;
import com.jzfq.fms.common.util.Arith;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.common.util.ServiceValidate;
import com.jzfq.fms.dao.ProductMapper;
import com.jzfq.fms.dao.RepayPlanMapper;
import com.jzfq.fms.dao.ValidOrderMapper;
import com.jzfq.fms.domain.Product;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.TransFlow;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.manager.OrderManager;
import com.jzfq.fms.manager.RepayPlanManager;
import com.jzfq.fms.manager.TransFlowManager;
import com.jzfq.fms.service.IRepayService;
import com.jzfq.fms.vo.RepayPlanInfo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhishuo on 11/8/16.
 */
@Service
public class RepayServiceImpl implements IRepayService {

    private Logger logger = LoggerFactory.getLogger(RepayServiceImpl.class);
    @Autowired
    private RepayPlanMapper repayPlanMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ValidOrderMapper validOrderMapper;
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private RepayPlanManager repayPlanManager;
    @Autowired
    private TransFlowManager transFlowManager;


    @Override
    public void calOverDueFee() {
        //查询今天未还或者逾期的还款计划
        List<RepayPlan> overDuePlans = repayPlanMapper.getOverDueList();
        logger.info("开始执行未还或者逾期扫描，未还或者逾期数量为：{}", overDuePlans.size());
        Date now = DateUtils.getNow();
        List<RepayPlan> plans = new ArrayList();
        for (RepayPlan overDuePlan : overDuePlans) {
            logger.info("--->开始计算未还或者逾期订单ID的滞纳金：{}", overDuePlan.getOrderId());
            try {
                ValidOrder order = validOrderMapper.getOrderByOrderId(overDuePlan.getOrderId());
                BigDecimal remainPrincipal = BigDecimal.ZERO;
                BigDecimal remianRepayAmount = BigDecimal.ZERO;
                int overDueDay = 0;
                boolean isOverDueTwo = false;

                //剩余本金 剩余还款额度
                List<RepayPlan> orderPlan = repayPlanMapper.getPlansByOrder(order.getOrderId());
                for (RepayPlan plan : orderPlan) {
                    //计算未还金额
                    if (plan.getState() == RepayState.NOMORE_STATE.getState() || plan.getState() == RepayState.OVERDUE_STATE.getState()) {
                        remainPrincipal = new Arith(remainPrincipal).add(plan.getPrincipal()).getRound();
                        remianRepayAmount = new Arith(remianRepayAmount).add(plan.getRepayMoney()).getRound();
                    }
                }
                //查询此期的上期还款计划
                RepayPlan repayPlan = new RepayPlan();
                repayPlan.setPeriod(overDuePlan.getPeriod() - 1);
                RepayPlan rpBefore = repayPlanMapper.getPlanByOrderIdAndPeriod(repayPlan);
                if (rpBefore != null && rpBefore.getState() == 10) {
                    isOverDueTwo = true;
                }
                //查询此期的逾期天数
                overDueDay = DateUtils.daysBetween(overDuePlan.getRepayday(), now);

                String type = order.getCapitalSide() + "_" + order.getType();
                BigDecimal overDueFee = OverDueClient.calOverDueFee(order.getStoreOrderTime(), overDueDay, overDuePlan.getAmount(),
                        remainPrincipal, overDuePlan.getRepayMoney(), remianRepayAmount, type, isOverDueTwo, overDuePlan.getRepayMoney());
                //修改还款逾期天数 计算滞纳金
                RepayPlan.RepayPlanBuilder builder = new RepayPlan.RepayPlanBuilder();
                builder.id(overDuePlan.getId()).state(RepayState.OVERDUE_STATE.getState()).overDueDay(overDueDay)
                        .overDueFee(overDueFee).updateTime(DateUtils.getNow());
                RepayPlan plan = builder.build();
                plans.add(plan);
                logger.info("--->结束计算未还或者逾期订单ID的滞纳金：{}", overDuePlan.getOrderId());
                //TODO 给催收推数据
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("计算逾期发生异常，id：{}", overDuePlan.getId());
            }
        }
        //更新数据
        repayPlanManager.updatePlans(plans);

        logger.info("执行逾期扫描完成");


    }

    /*@Override
    public void calOverDueFee() {
        //查询今天未还或者逾期的还款计划
        List<RepayPlan> overDuePlans = repayPlanMapper.getOverDueList();
        logger.info("开始执行未还或者逾期扫描，未还或者逾期数量为：{}", overDuePlans.size());
        Date now = DateUtils.getNow();
        for (RepayPlan overDuePlan : overDuePlans) {
            logger.info("--->开始计算未还或者逾期订单ID的滞纳金：{}", overDuePlan.getOrderId());
            try {
                ValidOrder order = validOrderMapper.getOrderByOrderId(overDuePlan.getOrderId());
                BigDecimal remainPrincipal = BigDecimal.ZERO;
                BigDecimal remianRepayAmount = BigDecimal.ZERO;
                int overDueDay = 0;
                boolean isOverDueTwo = false;

                //剩余本金 剩余还款额度
                List<RepayPlan> orderPlan = repayPlanMapper.getPlansByOrder(order.getOrderId());
                for (RepayPlan plan : orderPlan) {
                    //计算未还金额
                    if (plan.getState() == RepayState.NOMORE_STATE.getState()||plan.getState() == RepayState.OVERDUE_STATE.getState()) {
                        remainPrincipal = new Arith(remainPrincipal).add(plan.getPrincipal()).getRound();
                        remianRepayAmount = new Arith(remianRepayAmount).add(plan.getRepayMoney()).getRound();
                    }
                }
                //查询此期的下期还款计划
                RepayPlan repayPlan = new RepayPlan();
                repayPlan.setOrderId(overDuePlan.getOrderId());
                repayPlan.setPeriod(overDuePlan.getPeriod()+1);
                RepayPlan rpAfter = repayPlanMapper.getPlanByOrderIdAndPeriod(repayPlan);
                //查询此期的上期还款计划
                repayPlan.setPeriod(overDuePlan.getPeriod()-1);
                RepayPlan rpBefore = repayPlanMapper.getPlanByOrderIdAndPeriod(repayPlan);
                if(rpBefore!=null&&rpBefore.getState()==10){
                    isOverDueTwo = true;
                }
                if(rpAfter==null){//说明此期是最后一期
                    overDueDay = DateUtils.daysBetween(overDuePlan.getRepayday(),now);
                }else{//说明此期不是最后一期
                    int flag = DateUtils.compareDateToNow(new DateTime(rpAfter.getRepayday()));
                    if(flag==-1||flag==0){//下期应还日期=<当前日期    (并且 此期应还日期<当前日期)
                        overDueDay = DateUtils.daysBetween(overDuePlan.getRepayday(),rpAfter.getRepayday());
                    }else{//下期应还日期>当前日期   (并且 此期应还日期<当前日期)
                        overDueDay = DateUtils.daysBetween(overDuePlan.getRepayday(),now);
                    }
                }

                String type = order.getCapitalSide() + "_" + order.getType();
                BigDecimal overDueFee = OverDueClient.calOverDueFee(order.getStoreOrderTime(), overDueDay, overDuePlan.getAmount(),
                        remainPrincipal, overDuePlan.getRepayMoney(), remianRepayAmount, type, isOverDueTwo,overDuePlan.getRepayMoney());
                //修改还款逾期天数 计算滞纳金
                RepayPlan.RepayPlanBuilder builder = new RepayPlan.RepayPlanBuilder();
                builder.id(overDuePlan.getId()).state(RepayState.OVERDUE_STATE.getState()).overDueDay(overDueDay)
                        .overDueFee(overDueFee).updateTime(DateUtils.getNow());
                RepayPlan plan = builder.build();
                //更新数据
                repayPlanManager.updatePlan(plan);
                logger.info("--->结束计算未还或者逾期订单ID的滞纳金：{}", overDuePlan.getOrderId());
                //TODO 给催收推数据
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("计算逾期发生异常，id：{}", overDuePlan.getId());
            }

        }

        logger.info("执行逾期扫描完成");


    }*/

    @Override
    public void saveDataAndRepayPlan(List<ValidOrder> orders) {
        for (ValidOrder order : orders) {
            try {
                //查询产品
                logger.info("商城传递过来的数据：订单ID" + order.getOrderId() + "---类型" + order.getType() + "---期数" + order.getPeriod());
                Product product = productMapper.getProduct(order.getType(), order.getPeriod());
                ServiceValidate.notNull(product, "该产品不存在");
                //根据传入的产品生成还款计划
                List<RepayPlan> plans = PlanClient.generate(order, product.getId(), order.getType());
                Date now = DateUtils.getNow();
                for (int i = 0; i < plans.size(); i++) {
                    RepayPlan plan = plans.get(i);
                    if (i + 1 <= order.getAlreadyRepaidPeriod()) {
                        plan.setState(Constants.ONE);
                        plan.setRepidTime(now);
                        plan.setRepayType(RepayType.SYSTEM_AUTO.getType());
                    }
                }
                //保存订单信息 和还款计划
                orderManager.save(order, plans);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateRepayPlan(ValidOrder vo) {
        ServiceValidate.notNull(vo.getOrderId(), "订单号不能为空");
        ServiceValidate.notNull(vo.getRepayDate(), "还款日不能为空");
        ServiceValidate.notNull(vo.getPeriod(), "期数不能为空");
        ServiceValidate.notNull(vo.getType(), "类型不能为空");
        ServiceValidate.notNull(vo.getRate(), "利率不能为空");
        ServiceValidate.notNull(vo.getAmount(), "申请金额不能为空");

        //查询产品
        Product product = productMapper.getProduct(vo.getType(), vo.getPeriod());
        ServiceValidate.notNull(product, "该产品不存在");
        //根据传入的产品生成还款计划
        List<RepayPlan> plans = PlanClient.generate(vo, product.getId(), vo.getType());
        for (RepayPlan plan : plans) {
            int count = repayPlanMapper.countByProductIdOrderIdPeriod(plan);
            if (count > 0) {
                ServiceValidate.isTrue(false, "还款计划重复");
            }
            repayPlanMapper.insertSelective(plan);
        }

        //状态变更为已经处理
        vo.setIsDeal(Constants.ONE);
        vo.setUpdateTime(new Date());
        int i = validOrderMapper.updateByPrimaryKeySelective(vo);
        ServiceValidate.isTrue(i == 1, "依据有效订单生产还款计划之后，此订单状态变更失败");

    }

    @Override
    public PageList<RepayPlan> getPlanByUser(Integer userId, Integer state, Integer currentPage, Integer pageSize) {
        ServiceValidate.notNull(userId, "用户ID不能为空");
        PageVo vo = new PageVo();
        Pageable pageable = new Pageable();
        pageable.setCurrentPage(currentPage);
        pageable.setPageSize(pageSize);
        vo.setPageable(pageable);
        vo.getParameters().put("customerId", userId);
        vo.getParameters().put("state", state);
        PageList<RepayPlan> plans = repayPlanMapper.getPlan(vo, vo.getPageable());
        planProcess(plans);
        return plans;
    }

    @Override
    public List<RepayPlan> getPlanByOrder(Integer orderId) {
        ServiceValidate.notNull(orderId, "订单不能为空");
        List<RepayPlan> plans = repayPlanMapper.getPlansByOrder(orderId);
        planProcess(plans);
        return plans;
    }

    private List<RepayPlan> planProcess(List<RepayPlan> plans) {
        //懒得在sql里写了。。
        boolean flag = true;
        for (RepayPlan plan : plans) {
            ValidOrder order = validOrderMapper.getOrderByOrderId(plan.getOrderId());
            TransFlow flow = transFlowManager.getFlowByPlanId(plan.getId());
            //还款相关信息
            if (null != flow) {
                plan.setPayId(flow.getPayid());
                plan.setPayType(flow.getPayType().toString());
                plan.setPayTime(flow.getFlowTime());
            }

            //交易标题
            String title = order == null ? "" : order.getTitle();
            plan.setTitle(title);
            if (canRepay(order.getCapitalSide())) {
                plan.setCanRepay(Constants.STR_ONE);
            } else {
                plan.setCanRepay(Constants.STR_ZERO);
            }
            RepayPlan firstUnRepay = repayPlanMapper.getFirstUnRepay(plan.getCustomerId());
            //是否可以还款状态 
            // 因为涉及有分页，所以会拿当期去和第一期未还比较，如果没有第一期未还，证明所有状态都不可以还款
            if (plan.getState() == RepayState.NOMORE_STATE.getState() && flag
                    && firstUnRepay != null && firstUnRepay.getId().equals(plan.getId())) {
                flag = false;
                plan.setSelfIsCanRepay(Constants.STR_ONE);
            } else {
                plan.setSelfIsCanRepay(Constants.STR_ZERO);
            }
        }
        return plans;
    }

    private boolean canRepay(byte type) {
        if (type == 0)
            return true;
        return false;
    }

    @Override
    public PageList<RepayPlan> getPlan(PageVo vo) {
        return repayPlanMapper.getPlan(vo, vo.getPageable());
    }

    @Override
    public PageList<RepayPlan> getPlanList(PageVo vo) {
        return repayPlanMapper.getPlanList(vo, vo.getPageable());
    }

    @Override
    public List<RepayPlan> selectRepayPlanByOrderId(Integer orderId) {
        return repayPlanMapper.getPlansByOrder(orderId);
    }

    /**
     * 查询客户是否有过逾期
     *
     * @param customerId
     * @return
     */
    @Override
    public boolean whetherOverdue(Integer customerId) {
        List<RepayPlan> list = repayPlanMapper.selectListByCustomerId(customerId);
        for (RepayPlan rp : list) {
            if (rp.getState() == RepayState.OVERDUE_STATE.getState()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public List<RepayPlanInfo> getOverdueRepayPlanForDay() {
        List<RepayPlan> list = repayPlanMapper.getOverdueRepayPlanForDay();
        return getRepayPlanInfoList(list);
    }

    @Override
    public List<RepayPlanInfo> getOverdueRepayPlanForHistory(Date date) {
        List<RepayPlan> list = repayPlanMapper.getOverdueRepayPlanForHistory(date);
        return getRepayPlanInfoList(list);
    }

    private List<RepayPlanInfo> getRepayPlanInfoList(List<RepayPlan> list) {
        List<RepayPlanInfo> infoList = new ArrayList<>();
        HashMap<Integer, ValidOrder> map = new HashMap<>();
        for (RepayPlan rp : list) {
            if (!map.keySet().contains(rp.getOrderId())) {
                ValidOrder vo = validOrderMapper.getOrderByOrderId(rp.getOrderId());
                RepayPlanInfo info = this.setRepayPlanInfo(vo, rp);
                infoList.add(info);
                map.put(rp.getOrderId(), vo);
            } else {
                ValidOrder vo = map.get(rp.getOrderId());
                RepayPlanInfo info = this.setRepayPlanInfo(vo, rp);
                infoList.add(info);
            }
        }
        return infoList;
    }

    private RepayPlanInfo setRepayPlanInfo(ValidOrder vo, RepayPlan rp) {
        RepayPlanInfo info = new RepayPlanInfo();
        String customerJsonStr = vo.getCustomerJson();
        if (!StringUtils.isEmpty(customerJsonStr)) {
            String[] customerKeyValues = customerJsonStr.split(",");
            HashMap<String, String> map = new HashMap<>();
            for (String customerKeyValue : customerKeyValues) {
                String[] unit = customerKeyValue.split(":");
                map.put(unit[0], unit.length == 1 ? "" : unit[1]);
            }

            String address = mapGetStr(map, "现居地址");
            if (!StringUtils.isEmpty(address)) {
                String[] addressArray = address.split("-");
                info.setBusinessAreaProvince(addressArray[0]);//业务地区-省
                info.setBusinessAreaCity(addressArray[1]);//业务地区-市
            }

            String contacts = mapGetStr(map, "紧急联系人1");
            if (!StringUtils.isEmpty(contacts)) {
                String[] contactsArray = contacts.split("-");
                info.setContactPersonType(contactsArray[0]);
                info.setContactPersonName(contactsArray[1]);
                info.setContactPersonPhone(contactsArray[2]);
            }

            info.setCustomerName(mapGetStr(map, "姓名"));
            info.setCustomerType(map.get("单位名称") == null ? (byte) 0 : (byte) 1);
            info.setCustomerTypeName(map.get("单位名称") == null ? "学生" : "白领");

            info.setBankCardNo(map.get("工资卡号"));
            //info.setCashAmount();//提现金额
            info.setCashCardNo(map.get("工资卡号"));
            info.setIdCardNo(map.get("身份证号"));
            info.setOpenBank(map.get("工资卡开户行"));
            info.setPhoneNumber(map.get("电话"));
            //info.setResidenceAddress();
            info.setSchoolPhone(map.get("学校电话") == null ? "" : map.get("学校电话"));
            info.setSchoolAddress(map.get("学校地址") == null ? "" : map.get("学校地址"));
            //info.setSex();//性别
            //info.setSurplusAmount();//剩余未还金额
            //info.setTodayPayAmount();//今日应还金额
            info.setUnitAddress(map.get("单位地址") == null ? "" : map.get("单位地址"));
            info.setUnitPhone(map.get("单位电话") == null ? "" : map.get("单位电话"));
        }

        info.setCreateOrderTime(vo.getStoreOrderTime());
        info.setCapital(vo.getCapitalSide());
        info.setCapitalName(CapitalType.TYPES.get(vo.getCapitalSide()).name());
        info.setCaseId(rp.getId());
        info.setFirstRepayDay(vo.getRepayDate());
        //当期应还本息和X总期数
        info.setIouAmount(new Arith(rp.getRepayMoney()).multiply(new BigDecimal(vo.getPeriod())).getResult());
        info.setLateDays(rp.getOverDueDay());
        info.setPaidPeriods(vo.getAlreadyRepaidPeriod());
        info.setOrderId(vo.getOrderId());
        info.setProductLine(vo.getType());
        info.setProductLineName(OrderType.TYPES.get(vo.getType()).name());
        info.setRepayMoneyTime(rp.getRepayday());
        info.setUserId(vo.getCustomerId());
        info.setTotalPeriods(vo.getPeriod());

        return info;
    }

    private String mapGetStr(Map map, String key) {
        Object obj = map.get(key);
        return obj == null ? "" : obj.toString();
    }


}
