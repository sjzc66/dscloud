package com.jzfq.fms.common.enums;

/**
 * Created by zhishuo on 11/8/16.
 */
public enum RepayState {

    NOMORE_STATE(0),//未还状态
    ALREADY_STATE(1),//已还状态
    EARLY_STATE(2), //提前还款
    OVERDUE_STATE(10);//逾期状态

    private int state;

    private RepayState(int state) {
        this.state = state;
    }

    public byte getState() {
        return (byte) state;
    }
}
