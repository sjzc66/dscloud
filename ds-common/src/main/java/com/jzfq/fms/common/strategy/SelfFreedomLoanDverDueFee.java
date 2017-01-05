package com.jzfq.fms.common.strategy;

import com.jzfq.fms.common.util.Arith;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhishuo on 11/28/16.
 */
public class SelfFreedomLoanDverDueFee implements OverDueFeeStrategy {

    private static DateTime basis = new DateTime("2016-09-01");

    @Override
    public BigDecimal calOverDueFee(Date orderDate, int overDueDay, BigDecimal amount, BigDecimal remainPrincipal,
                                    BigDecimal contractAmount, BigDecimal remainRepayAmount, boolean isOverDueTwo, BigDecimal repayMoney) {

        DateTime orderTime = new DateTime(orderDate);
        BigDecimal result = BigDecimal.ZERO;
        if (orderTime.isBefore(basis)) {
            result = new Arith(remainPrincipal).multiply(new BigDecimal("0.01")).getRound();
        } else {
            if (isOverDueTwo==false&&overDueDay <= 3) {
                return result;
            } else {
                result = new Arith(remainPrincipal).multiply(new BigDecimal("0.01")).getRound();
            }
        }
        return result;
    }
}
