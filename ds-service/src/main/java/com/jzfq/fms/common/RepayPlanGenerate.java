package com.jzfq.fms.common;

import com.google.common.collect.Lists;

import com.jzfq.fms.common.enums.OrderType;
import com.jzfq.fms.common.util.Arith;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by zhishuo on 11/8/16.
 */
public class RepayPlanGenerate implements GeneratePlan {

    /**
     * @param vo
     * @param productId
     * @return
     */
    public List<RepayPlan> generate(ValidOrder vo, int productId) {
        List<RepayPlan> plans = Lists.newArrayList();
        BigDecimal period = new BigDecimal(vo.getPeriod());
        DateTime repdayDate = new DateTime(vo.getRepayDate()).dayOfMonth().roundFloorCopy();
        DateTime now = DateUtils.getDateTimeNow();
        BigDecimal sumPrincipal = BigDecimal.ZERO;
        BigDecimal sumInterest = BigDecimal.ZERO;
        BigDecimal sumAmount = BigDecimal.ZERO;

        BigDecimal dValueForMonthIterest = BigDecimal.ZERO;

        for (int i = 1; i < vo.getPeriod() + 1; i++) {
            BigDecimal monthIterest = new BigDecimal(0);

            if (vo.getBehead() != 1) {
                monthIterest = new Arith(vo.getAmount()).multiply(vo.getRate()).getRound();

                BigDecimal monthIterestRest= new Arith(vo.getAmount()).multiply(vo.getRate()).getResult();
                //每月利息差值 = 月利息-四舍五入的月利息
                dValueForMonthIterest = new Arith(monthIterestRest).sub(monthIterest).getResult();
            }
            BigDecimal monthPrincipal = new Arith(vo.getAmount()).divide(period).getRound();
            sumAmount = new Arith(sumAmount).add(monthPrincipal).getRound();

            //如果是最后一期，处理误差
            if (vo.getPeriod().intValue() == i) {
                boolean isBigger = Arith.isBigger(sumAmount, vo.getAmount());
                //如果应还总本金大于 原始申请金额 减掉
                if (isBigger) {
                    monthPrincipal = new Arith(monthPrincipal).sub(new Arith(sumAmount).sub(vo.getAmount()).getRound()).getRound();
                } else {
                    monthPrincipal = new Arith(monthPrincipal).add(new Arith(vo.getAmount()).sub(sumAmount).getRound()).getRound();
                }
                //总总月利息差值合计到最后一期月利息当中
                BigDecimal SumDValueForMonthIterest = new Arith(dValueForMonthIterest).multiply(new BigDecimal(vo.getPeriod())).getResult();
                monthIterest = monthIterest.add(SumDValueForMonthIterest);
            }

            BigDecimal repayMoney = new Arith(monthPrincipal).add(monthIterest).getRound();
            RepayPlan.RepayPlanBuilder builder = new RepayPlan.RepayPlanBuilder();
            RepayPlan plan = builder.interest(monthIterest).principal(monthPrincipal)
                    .period(i).repayDay(repdayDate.toDate()).orderId(vo.getOrderId())
                    .createTime(now.toDate()).updateTime(now.toDate())
                    .storeMonthRate(vo.getRate()).amount(vo.getAmount())
                    .actualPeriod(vo.getPeriod()).customerId(vo.getCustomerId())
                    .repayMoney(repayMoney).productId(productId).build();

            if (OrderType.TYPES.get(vo.getType()) == OrderType.LOAN_TYPE) {
                repdayDate = repdayDate.plusDays(1);
            } else {
                repdayDate = repdayDate.plusMonths(1);
            }

            sumInterest = new Arith(monthIterest).add(sumInterest).getRound();
            sumPrincipal = new Arith(monthPrincipal).add(sumPrincipal).getRound();

            plans.add(plan);
        }

        for (RepayPlan plan : plans) {
            plan.setSumInterest(sumInterest);
            plan.setSumPrincipal(sumPrincipal);
        }

        return plans;
    }

    public static void main(String[] args) {
        System.out.println("商城分期301 常用9期");
        //商城分期301 常用9期
//        new RepayPlanGenerate().generatePlan(new BigDecimal("0.01300000"),
//                new BigDecimal("1498"), 9, new Date(),
//                OrderType.STORE_TYPE, 3, "285771",false);
        System.out.println(Arith.isBigger(new BigDecimal(100),new BigDecimal(99)));
    }

}
