package com.jzfq.fms.common.strategy;

import com.jzfq.fms.common.util.Arith;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhishuo on 11/28/16.
 */
public class HaierOverDueFee implements OverDueFeeStrategy {

    private static DateTime basis = new DateTime("2016-04-30");

    private static BigDecimal rate = new BigDecimal("0.0005");
    private static BigDecimal rate1 = new BigDecimal("0.05");

    @Override
    public BigDecimal calOverDueFee(Date orderDate, int overDueDay, BigDecimal amount, BigDecimal remainPrincipal,
                                    BigDecimal contractAmount, BigDecimal remainRepayAmount, boolean isOverDueTwo, BigDecimal repayMoney) {
        BigDecimal result = BigDecimal.ZERO;

        if (isOverDueTwo==false&&overDueDay <= 3) {
            return result;
        }

        DateTime orderTime = new DateTime(orderDate);
        if (orderTime.isBefore(basis)) {
            BigDecimal temp = new Arith(repayMoney).multiply(rate1).getRound();
            if(isOverDueTwo){
                result = new Arith(repayMoney).multiply(rate).multiply(new BigDecimal(overDueDay)).add(temp).getRound();
            }else{
                result = new Arith(repayMoney).multiply(rate).multiply(new BigDecimal(overDueDay-3)).add(temp).getRound();
            }
        } else {
            BigDecimal temp = new Arith(repayMoney).multiply(rate1).getRound();
            result = new Arith(repayMoney).multiply(new BigDecimal("0.001")).multiply(new BigDecimal(overDueDay-3)).add(temp).getRound();
            if(result.compareTo(BigDecimal.valueOf(30))==0||result.compareTo(BigDecimal.valueOf(30))==-1){
                result = new BigDecimal(30);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        DateTime orderDate = new DateTime("2016-10-11");
        BigDecimal a = new HaierOverDueFee().calOverDueFee(orderDate.toDate(), 42, new BigDecimal("1359.00"), null
                , null
                , null,false, new BigDecimal("105.55"));
        System.out.println(a);
    }

}
