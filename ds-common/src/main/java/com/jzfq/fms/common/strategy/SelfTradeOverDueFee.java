package com.jzfq.fms.common.strategy;

import com.jzfq.fms.common.util.Arith;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhishuo on 11/28/16.
 * 自营商品 计算逾期费
 */
public class SelfTradeOverDueFee implements OverDueFeeStrategy {

    private static DateTime intervalStart1 = new DateTime("2014-07-01");
    private static DateTime intervalEnd1 = new DateTime("2015-03-01");

    private static DateTime intervalStart2 = new DateTime("2015-03-01");
    private static DateTime intervalEnd2 = new DateTime("2016-09-01");

    private static BigDecimal ten = new BigDecimal("10");
    private static BigDecimal five = new BigDecimal("5");

    private static BigDecimal overDueRate = new BigDecimal("0.005");

    @Override
    public BigDecimal calOverDueFee(Date orderDate, int overDueDay, BigDecimal amount,
                                    BigDecimal remainPrincipal, BigDecimal contractAmount, BigDecimal remainRepayAmount, boolean isOverDueTwo, BigDecimal repayMoney) {

        BigDecimal result = BigDecimal.ZERO;
        Interval interval1 = new Interval(intervalStart1, intervalEnd1);
        Interval interval2 = new Interval(intervalStart2, intervalEnd2);
        //如果在区间1 每日10元 无宽限期
        if (interval1.contains(new DateTime(orderDate))) {
            result = new Arith(ten).multiply(new BigDecimal(overDueDay)).getRound();
            //如果在区间2 每日5元 无宽限期
        } else if (interval2.contains(new DateTime(orderDate))) {
            result = new Arith(five).multiply(new BigDecimal(overDueDay)).getRound();
        } else if (intervalEnd2.isBefore(new DateTime(orderDate))||intervalEnd2.equals(new DateTime(orderDate))) {
            //宽限期3天
            if (isOverDueTwo==false&&overDueDay <= 3) {
                return BigDecimal.ZERO;
            }
            if(isOverDueTwo){
                result = new Arith(remainRepayAmount).multiply(overDueRate).multiply(new BigDecimal(overDueDay)).getRound();
            }else{
                result = new Arith(remainRepayAmount).multiply(overDueRate).multiply(new BigDecimal(overDueDay - 3)).getRound();
            }
        }else{
            result = new Arith(overDueDay).multiply(ten).getRound();
        }

        return result;
    }

    public static void main(String[] args) {
        DateTime orderDate = new DateTime("2016-10-11");
        BigDecimal a = new SelfTradeOverDueFee().calOverDueFee(orderDate.toDate(), 3, new BigDecimal("2000"), new BigDecimal("5000")
                , new BigDecimal("6000"), new BigDecimal("6500"),true, null);
        System.out.println(a);
    }
}
