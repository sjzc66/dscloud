package com.jzfq.fms.common.enums;

/**
 * Created by zhishuo on 11/25/16.
 */
public enum RepayType {

    NORMAL(1),//正常还款 
    OVER_DUE(2),//逾期还款
    SYSTEM_AUTO(10);//系统初始化数据还款

    private int type;

    private RepayType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }


}
