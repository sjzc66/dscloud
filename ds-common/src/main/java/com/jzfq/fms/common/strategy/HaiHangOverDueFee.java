package com.jzfq.fms.common.strategy;

import com.jzfq.fms.common.util.Arith;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhishuo on 11/28/16.
 * 海航逾期计算
 */
public class HaiHangOverDueFee implements OverDueFeeStrategy {

    private static BigDecimal rate = new BigDecimal("0.001");

    @Override
    public BigDecimal calOverDueFee(Date orderDate,  int overDueDay, BigDecimal amount, BigDecimal remainPrincipal,
                                    BigDecimal contractAmount, BigDecimal remainRepayAmount, boolean isOverDueTwo,BigDecimal repayMoney) {
        if (isOverDueTwo==false&&overDueDay <= 3) {
            return BigDecimal.ZERO;
        }
        BigDecimal overdueFee = BigDecimal.ZERO;
        if(isOverDueTwo){
            overdueFee = new Arith(BigDecimal.ONE).add(rate).pow(overDueDay).multiply(amount).sub(amount).getRound();
        }else{
            overdueFee = new Arith(BigDecimal.ONE).add(rate).pow(overDueDay - 3).multiply(amount).sub(amount).getRound();
        }

        return overdueFee;
    }
}
