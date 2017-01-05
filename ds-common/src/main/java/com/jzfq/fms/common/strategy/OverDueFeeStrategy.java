package com.jzfq.fms.common.strategy;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhishuo on 11/28/16.
 * 计算滞纳金策略 因为不同的资方 不同的时间 都会有不同的算法
 */
public interface OverDueFeeStrategy {

    /**
     * @param orderDate         订单下单日
     * @param overDueDay        逾期天数
     * @param amount            月供金额
     * @param remainPrincipal   剩余本金
     * @param contractAmount    合同金额(此参数废弃-20161215)
     * @param remainRepayAmount 剩余还款金额
     * @return 逾期金额 如果不符合规则 返回0
     */
    BigDecimal calOverDueFee(Date orderDate, int overDueDay, BigDecimal amount,
                             BigDecimal remainPrincipal, BigDecimal contractAmount, BigDecimal remainRepayAmount, boolean isOverDueDay, BigDecimal repayMoney);
}
