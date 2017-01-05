package com.jzfq.fms.common;

import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;

import org.apache.commons.collections.map.HashedMap;

import java.util.List;
import java.util.Map;

/**
 * Created by zhishuo on 11/18/16.
 */
public class PlanClient {
    //随手借 
    public static final int FREE_LOAN = 1;

    public static final int IOUS = 2;

    public static final int STORE = 3;

    private static final Map<Integer, GeneratePlan> TYPE_MAP = new HashedMap(10);

    static {
        TYPE_MAP.put(FREE_LOAN, new FreeLoanPlanGenerate());
        TYPE_MAP.put(IOUS, new RepayPlanGenerate());
        TYPE_MAP.put(STORE, new RepayPlanGenerate());
    }

    public static List<RepayPlan> generate(ValidOrder vo, int productId, int type) {
        return TYPE_MAP.get(type).generate(vo, productId);
    }
}
