package com.jzfq.fms.manager;

import com.jzfq.fms.dao.RepayPlanMapper;
import com.jzfq.fms.dao.ValidOrderMapper;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhishuo on 11/25/16.
 */
@Component
public class OrderManager {

    @Autowired
    private ValidOrderMapper validOrderMapper;
    @Autowired
    private RepayPlanMapper repayPlanMapper;

    @Transactional
    public void save(ValidOrder order, List<RepayPlan> plans) {
        validOrderMapper.insertSelective(order);
        for (RepayPlan plan : plans) {
            repayPlanMapper.insertSelective(plan);
        }

    }
}
