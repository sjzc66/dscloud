package com.jzfq.fms.rest;


import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.domain.QuotaApproval;
import com.jzfq.fms.service.IQuotaApprovalService;

import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


/**
 * Created by zhishuo on 11/9/16.
 */
@RestController
@RequestMapping("/api/quotaapproval")
public class QuotaApprovalApi extends BaseApi {

    @Autowired
    private IQuotaApprovalService quotaApprovalService;

    /**
     * 接受风控额度审批和用户信息保存额度审批表和用户表
     *
     * @param quotaApproval
     * @return
     */
    @RequestMapping(value = "/insertapproval",method = {RequestMethod.POST})
    public JsonResult insertQuotaApprovalAndCutomer(@RequestBody QuotaApproval quotaApproval) {
        quotaApprovalService.insertQuotaApprovalAndCustomer(quotaApproval);
        return returnSuccess(quotaApproval.getOrderId(), "此订单号的额度审批和用户信息录入成功");
    }

    /**
     * 根据风控传入的订单号更新额度审批表中的审核状态
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/updateapproval",method = {RequestMethod.POST})
    public JsonResult updateQuotaApprovalAuditStatusByOrderId(@RequestParam("orderId") String orderId,@RequestParam("auditStatus") String auditStatus){

        quotaApprovalService.updateQuotaApprovalAuditStatusByOrderId(orderId,auditStatus);
        return returnSuccess(orderId, "此订单号的审核状态已更新成功");
    }


}
