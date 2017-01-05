package com.jzfq.fms.service.impl;

import com.jzfq.AbstractTest;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.service.IValidOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhishuo on 11/15/16.
 */

public class ValidOrderServiceImplTest extends AbstractTest{

    @Autowired
    private IValidOrderService validOrderService;


    @Test
    public void getRepayPlanByValidOrderTest() {
        ValidOrder validOrder = new ValidOrder();
        validOrder.setAmount(new BigDecimal(10000));
        validOrder.setBehead((byte)0);
        validOrder.setCapitalSide((byte)0);
        validOrder.setCustomerId(1111);
        validOrder.setFreeInterest((byte)0);
        validOrder.setOrderId(1111);
        validOrder.setPeriod((byte)12);
        validOrder.setRate(new BigDecimal(0.005));
        validOrder.setRepayDate(new Date());
        validOrder.setStoreOrderTime(new Date());
        validOrder.setSource((byte)1);
        validOrder.setType((byte)3);
        List<RepayPlan> list = validOrderService.getRepayPlansByValidOrder(validOrder);
        System.out.println("依据商场已发货订单生成还款计划并返回:"+ list.size() +"===>getRepayPlanByValidOrderTest--->执行完毕！");
    }

    @Test
    public void addValidOrderAndAddRepayPlanTest() {
        ValidOrder validOrder = new ValidOrder();
        validOrder.setAmount(new BigDecimal(10000));
        validOrder.setBehead((byte)0);
        validOrder.setCapitalSide((byte)0);
        validOrder.setCustomerId(1111);
        validOrder.setFreeInterest((byte)0);
        validOrder.setOrderId(1111);
        validOrder.setPeriod((byte)12);
        validOrder.setRate(new BigDecimal(0.005));
        validOrder.setRepayDate(new Date());
        validOrder.setStoreOrderTime(new Date());
        validOrder.setSource((byte)1);
        validOrder.setType((byte)3);
        validOrderService.addValidOrderAndAddRepayPlan(validOrder);
        System.out.println("1、依据已发货订单插入有效订单  2、依据有效订单生成还款计划:===>addValidOrderAndAddRepayPlanTest--->执行完毕！");
    }

}