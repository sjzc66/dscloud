package com.jzfq.fms.rest;

import com.jzfq.AbstractTest;
import com.jzfq.fms.common.enums.QuotaApprovalStatus;
import com.jzfq.fms.domain.QuotaApproval;
import com.jzfq.fms.service.IQuotaApprovalService;
import junit.framework.TestCase;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by zhishuo on 12/7/16.
 */
public class QuotaApprovalApiTest extends AbstractTest {

    private Logger logger= LoggerFactory.getLogger(QuotaApprovalApiTest.class);

    @Autowired
    private IQuotaApprovalService quotaApprovalService;

    @Test
    /**
     * 额度审批单录入 测试
     */
    public void insertQuotaApprovalAndCustomerTest() throws Exception {
        QuotaApproval quotaApproval = new QuotaApproval();
        quotaApproval.setOrderId("121217");
        quotaApproval.setCustomerId(123456);
        quotaApproval.setApplicationTime(new Date(System.currentTimeMillis()));
        quotaApproval.setPhone("18731308976");
        quotaApproval.setIncomingProduct("2");
        quotaApproval.setUsername("试试");
        quotaApproval.setIdCard("130856199976542345");
        quotaApproval.setCustomerType("1");
        quotaApproval.setApprovalStatus("1");
        JSONObject json = JSONObject.fromObject(quotaApproval);
        System.out.println(json);
        //boolean result = quotaApprovalService.insertQuotaApprovalAndCustomer(quotaApproval);
        //TestCase.assertEquals(true,result);
    }

    /**
     * 额度审批单状态修改 测试
     * @throws Exception
     */
    @Test
    public void updateQuotaApprovalAuditStatusByOrderIdTest() throws Exception{
        QuotaApproval quotaApproval = new QuotaApproval();
        String orderId ="121214";
        String auditStatus ="2";
        boolean result = quotaApprovalService.updateQuotaApprovalAuditStatusByOrderId(orderId,auditStatus);
        TestCase.assertEquals(true,result);
    }


}