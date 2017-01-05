package com.jzfq.fms.common.enums;

/**
 * Created by huyinglin on 2016/12/1.
 */
public enum TransFlowRepayType {

    ACTIVE_REPAY("主动还款"),

    AUTOMATIC_REPAY("自动还款"),

    LINE_REPAY("线下还款");

    String type;

    private TransFlowRepayType(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }

}
