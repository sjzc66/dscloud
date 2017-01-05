package com.jzfq.fms.rest;

import com.jzfq.AbstractTest;
import com.jzfq.fms.domain.PresentOrder;
import com.jzfq.fms.service.IPresentOrderService;
import com.jzfq.fms.template.OrderHttpGetData;
import junit.framework.TestCase;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by 星星 on 2016/12/9.
 */
public class PresentOrderApiTest extends AbstractTest{

    private Logger logger = LoggerFactory.getLogger(PresentOrderApiTest.class);

    @Autowired
    private IPresentOrderService presentOrderService;

    @Test
    /**
     * 获取提现订单列表 测试
     */
    public void insertPersentOrderTest(){
        PresentOrder presentOrder = new PresentOrder();
        presentOrder.setBankAddress("北京市朝阳区奥林匹克");
        presentOrder.setBankCode("62087654789612");
        presentOrder.setBankName("中国民生银行北京首体支行");
        presentOrder.setInterest(new BigDecimal(30));
        presentOrder.setLoanLimit(new BigDecimal(10000));
        presentOrder.setMallAccount((byte)1);
        presentOrder.setMonthly(new BigDecimal(900));
        presentOrder.setOrderId("3634877");
        presentOrder.setOrderStatus((byte) 1);
        presentOrder.setOrderTime(new Date(System.currentTimeMillis()));
        presentOrder.setPeriods(12);
        presentOrder.setPhone("18738764225");
        presentOrder.setProductCode("2");
        presentOrder.setRecommendCode("1233434");
        presentOrder.setRepayDate(new Date(System.currentTimeMillis()));
        presentOrder.setSource((byte)2);
        presentOrder.setUsername("aaa");
        presentOrder.setWhitebarAccount((byte)0);
        presentOrder.setCustomerId("267342");

        JSONObject jsonObject = JSONObject.fromObject(presentOrder);
        System.out.println(jsonObject);
        //boolean result = presentOrderService.insertPresentOrder(presentOrder);
        //TestCase.assertEquals(true,result);
    }

}
