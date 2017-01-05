package com.jzfq.fms.rest;

import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.IRepayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liu on 2016/12/9.
 */
@RestController
@RequestMapping("/api/plan")
public class RepayPlanApi extends BaseApi {

    @Autowired
    private IRepayService repayService;

    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public JsonResult getPlanByOrder(@RequestParam Integer order) {
        List<RepayPlan> planList = repayService.getPlanByOrder(order);
        return returnSuccess(planList, "根据订单号查询生成的还款计划成功执行");
    }


    @RequestMapping(value = "/list/byuser", method = {RequestMethod.POST})
    public JsonResult getPlanByUser(@RequestParam(value = "userId") Integer userId, Integer state,
                                    @RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        PageList<RepayPlan> plans = repayService.getPlanByUser(userId, state, currentPage, pageSize);
        return returnSuccess(plans);
    }
}
