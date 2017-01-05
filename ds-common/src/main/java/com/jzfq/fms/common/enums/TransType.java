package com.jzfq.fms.common.enums;

/**
 * Created by huyinglin on 2016/12/13.
 */
public enum TransType {
    //消费
    CONSUME(0),
    //退款
    REFUND(1);

    int type;

    private TransType(int type){
        this.type=type;
    }

    public int getType() {
        return type;
    }
}
