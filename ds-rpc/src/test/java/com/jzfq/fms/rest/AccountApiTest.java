package com.jzfq.fms.rest;

import com.jzfq.AbstractTest;
import com.jzfq.fms.domain.Account;
import com.jzfq.fms.domain.QuotaApproval;
import com.jzfq.fms.service.IAccountService;
import junit.framework.TestCase;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.SourceExtractor;

import javax.xml.ws.soap.Addressing;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhishuo on 12/7/16.
 */
public class AccountApiTest extends AbstractTest {

    private Logger logger= LoggerFactory.getLogger(AccountApiTest.class);

    @Autowired
    private IAccountService accountService;

    /**
     * 初始化额度账户 测试
     * @throws Exception
     */
    @Test
    public void initialAccountTest() throws Exception {

        //额度控制信息
        QuotaApproval quotaApproval = new QuotaApproval();
        quotaApproval.setOrderId("121218");
        quotaApproval.setCustomerId(123458);
        quotaApproval.setApplicationTime(new Date(System.currentTimeMillis()));
        quotaApproval.setPhone("18831308976");
        quotaApproval.setIncomingProduct("2");
        quotaApproval.setUsername("试试");
        quotaApproval.setIdCard("130856199976542345");
        quotaApproval.setCustomerType("1");
        quotaApproval.setApprovalStatus("1");
        quotaApproval.setIsActived(0);
        quotaApproval.setMaxAmount(new BigDecimal(20000));

        //账户信息
        Account account = new Account();
        account.setCustomerId(100001);
        account.setCreditId(111);
        account.setClosingDate(new Date(System.currentTimeMillis()));
        account.setAccountDate(new Date());
        account.setAllInitAmount(new BigDecimal(12000));
        account.setCashInitAmount(new BigDecimal(4000));
        account.setConsumeInitAmount(new BigDecimal(8000));
        account.setAllValidAmount(new BigDecimal(10000));
        account.setCashValidAmount(new BigDecimal(4000));
        account.setConsumeValidAmount(new BigDecimal(6000));
        account.setMaxAmount(new BigDecimal(30000));
        JSONObject json1 = JSONObject.fromObject(quotaApproval);
        JSONObject json2 = JSONObject.fromObject(account);
        System.out.println(json1);
        System.out.println("json2="+json2);
        //boolean result = accountService.initialAccountAndLog(account,quotaApproval);
        //TestCase.assertEquals(true,result);
    }

    public void testValidamount() throws Exception {

    }

}