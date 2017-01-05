package com.jzfq.fms.service.impl;

import com.jzfq.AbstractTest;
import com.jzfq.fms.common.enums.AccountType;
import com.jzfq.fms.domain.Account;
import com.jzfq.fms.service.IAccountService;
import com.jzfq.fms.service.IRepayService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhishuo on 11/15/16.
 */

public class RepayServiceImplTest extends AbstractTest{

    @Autowired
    private IRepayService repayService;


    @Test
    public void whetherOverdueTest() {
        boolean bd = repayService.whetherOverdue(111);
        System.out.println("查询是否逾期:"+ bd +"===>whetherOverdueTest--->执行完毕！");
    }

}