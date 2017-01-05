package com.jzfq.fms.common;

import com.jzfq.fms.common.constants.Constants;
import com.jzfq.fms.common.util.Arith;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhishuo on 11/18/16.
 * 随手借生成还款计划
 */
public class FreeLoanPlanGenerate implements GeneratePlan {

    private static Logger log = LoggerFactory.getLogger(FreeLoanPlanGenerate.class);

    @Override
    public List<RepayPlan> generate(ValidOrder vo, int productId) {
        List<RepayPlan> planList = new ArrayList<>();
        int period = vo.getPeriod();
        BigDecimal rate = vo.getRate();
        BigDecimal money = vo.getAmount();
        Date now = DateUtils.getNow();

        Date repayDay = new DateTime(vo.getStoreOrderTime()).plusDays(vo.getPeriod()).minusDays(1).toDate();
        RepayPlan.RepayPlanBuilder builder = new RepayPlan.RepayPlanBuilder();
        builder.createTime(now).updateTime(now).productId(productId)
                .orderId(vo.getOrderId()).actualPeriod(vo.getPeriod()).period(vo.getPeriod())
                .storeMonthRate(vo.getRate()).customerId(vo.getCustomerId())
                .repayDay(repayDay);

        //如果是砍头息 直接生成没有利息的就可以
        if (vo.getBehead() == Constants.ONE) {
            RepayPlan plan = builder.amount(vo.getAmount()).repayMoney(vo.getAmount())
                    .sumPrincipal(vo.getAmount()).sumInterest(BigDecimal.ZERO)
                    .principal(vo.getAmount()).interest(BigDecimal.ZERO).build();
            planList.add(plan);

        } else if (vo.getBehead() == Constants.ZERO) {
            BigDecimal interest = new Arith(vo.getAmount()).multiply(vo.getRate()).multiply(new BigDecimal(vo.getPeriod())).getRound();
            BigDecimal repayMoney = new Arith(vo.getAmount()).add(interest).getRound();
            RepayPlan plan = builder.amount(vo.getAmount()).repayMoney(repayMoney)
                    .sumPrincipal(vo.getAmount()).sumInterest(interest)
                    .principal(vo.getAmount()).interest(interest).build();
            planList.add(plan);

        } else {
            log.error("砍头息不支持的类型：订单号：{}，类型：{}", vo.getOrderId(), vo.getBehead());
            throw new RuntimeException("不支持的类型");
        }
        return planList;
    }
}
