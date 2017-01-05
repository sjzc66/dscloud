package com.jzfq.fms.rest;

import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.service.IRepayService;
import com.jzfq.fms.service.IValidOrderService;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * Created by zhishuo on 11/9/16.
 */
@RestController
@RequestMapping("/api/validorder")
public class ValidOrderApi extends BaseApi {

    @Autowired
    private IValidOrderService validOrderService;
    @Autowired
    private IRepayService repayService;

    /**
     * 依据商城已发货订单添加有效订单--->(提供给PHP商城)
     * 1、添加数据到有效订单
     * 2、生成还款计划插入到数据库
     *
     * @param validOrder
     * @return
     */
    @RequestMapping(value = "/addvalidorder", method = RequestMethod.POST)
    public JsonResult addValidOrder(ValidOrder validOrder) {
        validOrderService.addValidOrderAndAddRepayPlan(validOrder);
        return returnSuccess(validOrder.getOrderId(), "依据商城已发货订单添加有效订单");
    }

    /**
     * 依据商城已通过订单生成还款计划并返回--->(提供给PHP商城)
     * 依据通过订单生成还款计划并返回给PHP商城
     *
     * @param validOrder
     * @return
     */
    @RequestMapping(value = "/getrepayplans")
    public JsonResult getrepayplan(ValidOrder validOrder) {
        List<RepayPlan> repayPlanList = validOrderService.getRepayPlansByValidOrder(validOrder);
        return returnSuccess(repayPlanList, "依据商城已通过订单生成还款计划并返回");
    }


    /**
     * 依据客户ID查询是否有过逾期--->(提供给PHP商城)
     *
     * @param customerId
     * @return
     */
    @RequestMapping("/repayplan/whetheroverdue")
    public JsonResult whetherOverdue(Integer customerId) {
        boolean b = repayService.whetherOverdue(customerId);
        return returnSuccess(b, "此客户是否逾期查询完毕");
    }


}
