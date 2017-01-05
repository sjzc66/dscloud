package com.jzfq.fms.common.strategy;

import org.apache.commons.collections.map.HashedMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhishuo on 11/28/16.
 */
public abstract class OverDueClient {

    private static Map<String, OverDueFeeStrategy> overDueMap = new HashedMap(10);

    static {
        // 0自营 1 海航 2 海尔 3众网    0自营还分1随手借 2白条 3商城      其他只有3商城
        overDueMap.put("0_1", new SelfFreedomLoanDverDueFee());
        overDueMap.put("0_2", new SelfTradeOverDueFee());
        overDueMap.put("0_3", new SelfTradeOverDueFee());
        overDueMap.put("1_3", new HaiHangOverDueFee());
        overDueMap.put("2_3", new HaierOverDueFee());
        overDueMap.put("3_3", new SelfTradeOverDueFee());
    }

    /**
     * 计算逾期费用
     *
     * @param orderDate         订单下单日
     * @param overDueDay        逾期天数
     * @param amount            月借金额
     * @param remainPrincipal   剩余未还本金
     * @param contractAmount    合同金额 也就是申请金额
     * @param remainRepayAmount 剩余未还金额 本金+利息
     * @param type              类型
     * @return
     */
    public static BigDecimal calOverDueFee(Date orderDate, int overDueDay, BigDecimal amount, BigDecimal remainPrincipal,
                                           BigDecimal contractAmount, BigDecimal remainRepayAmount, String type, boolean isOverDueTwo, BigDecimal repayMoney) {
        return overDueMap.get(type).calOverDueFee(orderDate, overDueDay, amount, remainPrincipal, contractAmount, remainRepayAmount, isOverDueTwo, repayMoney);

    }

}
