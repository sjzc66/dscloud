package com.jzfq.fms.controller;

import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.common.enums.TransFlowRepayType;
import com.jzfq.fms.common.util.DateEnum;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.domain.SysUser;
import com.jzfq.fms.domain.TransFlow;
import com.jzfq.fms.service.ITransFlowService;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by huyinglin on 2016/11/30.
 */
@Controller
@RequestMapping("/transflow")
public class TransFlowController extends BaseController {

    private Logger logger= LoggerFactory.getLogger(TransFlowController.class);

    @Autowired
    private ITransFlowService transFlowService;


    /**
     * 保存线下还款流水
     * 同步PHP还款期数
     * 修改还款计划状态
     * @param
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public JsonResult addTransFlow(HttpServletRequest request){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        TransFlow trans=new TransFlow();
        trans.setOrderId(Integer.parseInt(request.getParameter("orderId")));
        trans.setPayid(request.getParameter("payid"));
        trans.setPlanId(Integer.parseInt(request.getParameter("planid")));
        trans.setPeriods(Integer.parseInt(request.getParameter("period")));
        trans.setPattern(Integer.parseInt(request.getParameter("pattern")));
        trans.setAmount(new BigDecimal(request.getParameter("amount")));
        trans.setPayType((byte)TransFlowRepayType.LINE_REPAY.ordinal());//线下还款
        trans.setFlowTime(DateUtils.toDate(request.getParameter("flowTime"), DateEnum.DATE_SIMPLE));
        trans.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        trans.setPersoner(user.getUsername());
        trans.setPersonerId(user.getId());
        boolean result=transFlowService.addTransFlow(trans);
        if(result){
            return returnSuccess("保存还款成功");
        }
       return JsonResult.createErrorMsg("保存还款失败");
    }

}
