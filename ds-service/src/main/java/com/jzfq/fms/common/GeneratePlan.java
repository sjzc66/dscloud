package com.jzfq.fms.common;

import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;

import java.util.List;

/**
 * Created by zhishuo on 11/18/16.
 */
public interface GeneratePlan {

    /**
     * 生成还款计划
     * @param vo 数据
     * @param productId 对应的产品id
     * @return
     */
    List<RepayPlan> generate(ValidOrder vo, int productId);
}
