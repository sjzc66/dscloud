package com.jzfq.fms.service;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.vo.RepayPlanInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by zhishuo on 11/8/16.
 */
public interface IRepayService {

    void generateRepayPlan(ValidOrder vo);

    List<RepayPlan> getPlanByOrder(Integer orderId);

    PageList<RepayPlan> getPlanByUser(Integer userId, Integer state, Integer currentPage, Integer pageSize);

    PageList<RepayPlan> getPlan(PageVo vo);

    PageList<RepayPlan> getPlanList(PageVo vo);

    /**
     * 保存订单数据，并且生成还款计划
     *
     * @param orders
     */
    void saveDataAndRepayPlan(List<ValidOrder> orders);

    void calOverDueFee();

    List<RepayPlan> selectRepayPlanByOrderId(Integer orderId);

    boolean whetherOverdue(Integer customerId);

    List<RepayPlanInfo> getOverdueRepayPlanForDay();

    List<RepayPlanInfo> getOverdueRepayPlanForHistory(Date date);
}
