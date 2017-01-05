package com.jzfq.fms.rest;

import com.jzfq.AbstractTest;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.service.IValidOrderService;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by liu on 2016/12/8.
 */
public class ValidOrderApiTest extends AbstractTest {
    private Logger logger = LoggerFactory.getLogger(ValidOrderApiTest.class);

    @Autowired
    private IValidOrderService validOrderService;

    @Test
    /**
     * 依据商城已发货订单添加有效订单 测试
     */
    public void addValidOrderTest() throws Exception{
        ValidOrder validOrder = new ValidOrder();
        validOrder.setOrderId(3652325);
        validOrder.setRate(new BigDecimal(0.002));
        validOrder.setAmount(new BigDecimal(14000));
        validOrder.setPeriod((byte)12);
        validOrder.setAlreadyRepaidPeriod((byte)0);
        validOrder.setType((byte)2);    //产品类型 1:随手借 2：白条  3：商城
        validOrder.setRepayDate(new Date());
        validOrder.setStoreOrderTime(new Date(System.currentTimeMillis()));
        validOrder.setBehead((byte)0);
        validOrder.setIsDeal((byte)0);  //是否已经处理  1：已经处理此订单生成还款计划     0：没有
        validOrder.setDelFlag((byte)0);
        validOrder.setFreeInterest((byte)0);    //是否免息 1是 0 否
        validOrder.setSource((byte)0);  //来源0:pc 1:h5 2:app
        validOrder.setCapitalSide((byte)2); //资金方0自营 1海航 2海尔
        validOrder.setCustomerId(123424);

        //JSONObject json = JSONObject.fromObject(validOrder);
        //System.out.println(json);
        validOrderService.addValidOrderAndAddRepayPlan(validOrder);
    }
}
