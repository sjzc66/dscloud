package com.jzfq.fms.manager;

import com.jzfq.fms.dao.RepayPlanMapper;
import com.jzfq.fms.domain.RepayPlan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhishuo on 11/29/16.
 */
@Component
public class RepayPlanManager {
    @Autowired
    private RepayPlanMapper repayPlanMapper;


    @Transactional
    public void updatePlans(List<RepayPlan> planList) {
        for (RepayPlan plan : planList) {
            repayPlanMapper.updateByPrimaryKeySelective(plan);
        }
    }

    @Transactional
    public int updatePlan(RepayPlan plan) {
        return repayPlanMapper.updateStateByOrderId(plan);
    }
}
