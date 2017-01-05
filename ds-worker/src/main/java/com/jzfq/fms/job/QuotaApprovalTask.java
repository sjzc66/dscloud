package com.jzfq.fms.job;

import com.jzfq.fms.domain.QuotaApproval;
import com.jzfq.fms.service.IQuotaApprovalService;
import com.jzfq.fms.service.impl.QuotaApprovalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 将未推送给风控的审批单，重新推送
 *
 * Created by liu on 2016/12/13.
 */
@Component("quotaApprovalTask")
public class QuotaApprovalTask {
    private Logger logger = LoggerFactory.getLogger(QuotaApprovalTask.class);

    @Autowired
    private IQuotaApprovalService quotaApprovalService;

    public void execute(){
        //获取所有未推送的审批单
        List<QuotaApproval> quotaApprovalList = quotaApprovalService.queryQuotaApprovalByIsPushed();
        for (QuotaApproval quotaApproval : quotaApprovalList) {
            try {
                quotaApprovalService.noticeRisk(quotaApproval);
            }catch (Exception e){
                String strErrorMsg="定时补偿：推送风控审批单失败，订单号="+quotaApproval.getOrderId();
                logger.error(strErrorMsg,e);
            }

        }
        logger.info("定时补偿：成功推送风控审批单");
    }
}
