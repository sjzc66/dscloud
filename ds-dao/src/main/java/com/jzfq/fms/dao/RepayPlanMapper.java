package com.jzfq.fms.dao;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.common.Pageable;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.interceptor.PageList;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RepayPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RepayPlan record);

    int insertSelective(RepayPlan record);

    RepayPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RepayPlan record);

    int updateByPrimaryKey(RepayPlan record);

    int countByProductIdOrderIdPeriod(RepayPlan record);

    List<RepayPlan> getPlansByOrder(Integer order);

    List<RepayPlan> getPlansByUser(@Param("userId") Integer userId, @Param("state") Integer state);

    PageList<RepayPlan> getPlan(@Param("vo") PageVo vo, Pageable pageable);

    PageList<RepayPlan> getPlanList(@Param("vo") PageVo vo, Pageable pageable);

    List<RepayPlan> getOverDueList();

    List<RepayPlan> selectListByCustomerId(Integer customerId);

    int updateStateByOrderId(RepayPlan plan);

    RepayPlan getPlanByOrderIdAndPeriod(RepayPlan record);

    List<RepayPlan> getOverdueRepayPlanForDay();

    List<RepayPlan> getOverdueRepayPlanForHistory(Date date);
    
    RepayPlan getFirstUnRepay(Integer customerId);
}