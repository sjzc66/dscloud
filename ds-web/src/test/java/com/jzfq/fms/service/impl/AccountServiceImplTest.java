package com.jzfq.fms.service.impl;

import com.jzfq.AbstractTest;
import com.jzfq.fms.common.enums.AccountType;
import com.jzfq.fms.domain.Account;
import com.jzfq.fms.domain.Product;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.service.IAccountService;
import com.jzfq.fms.service.IProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhishuo on 11/15/16.
 */

public class AccountServiceImplTest extends AbstractTest{
    
    @Autowired
    private IAccountService accountService;

    @Test
    public void initialAccountAndLogTest() {
        Account at = new Account();
        at.setCustomerId(111);
        at.setCashValidAmount(new BigDecimal(10000));
        //at.setAllValidAmount(new BigDecimal(10000));
        at.setCashInitAmount(new BigDecimal(10000));
        //at.setAllInitAmount(new BigDecimal(10000));
        at.setAccountDate(new Date());
        //accountService.initialAccountAndLog(at);
        System.out.println("初始化initialAccountAndLogTest--->执行完毕！");
    }

    @Test
    public void enchashmentFrozenAccountAndLogTest() {
        accountService.enchashmentFrozenAccountAndLog(111,new BigDecimal(-100), AccountType.CASH_TYPE);
        System.out.println("提现冻结enchashmentFrozenAccountAndLogTest--->执行完毕！");
    }

    @Test
    public void enchashmentFinishAccountAndLogTest() {
        accountService.enchashmentFinishAccountAndLog(111,new BigDecimal(-100),AccountType.CASH_TYPE);
        System.out.println("提现完成enchashmentFinishAccountAndLogTest--->执行完毕！");
    }

    @Test
    public void repaymentAccountAndLogTest() {
        accountService.repaymentAccountAndLog(111,new BigDecimal(100),AccountType.CASH_TYPE);
        System.out.println("还款repaymentAccountAndLogTest--->执行完毕！");
    }

    @Test
    public void getValidamountTest() {
        BigDecimal bd = accountService.getValidamount(111);
        System.out.println("获取额度:"+ bd.doubleValue() +"===>getValidamountTest--->执行完毕！");
    }

}