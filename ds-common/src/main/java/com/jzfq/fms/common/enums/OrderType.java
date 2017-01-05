package com.jzfq.fms.common.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by zhishuo on 11/9/16.
 */
public enum OrderType {
    //白条
    CREDIT_TYPE((byte) 2),
    //商城
    STORE_TYPE((byte) 3),
    //随手借
    LOAN_TYPE((byte) 1);

    private byte type;

    private OrderType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public static final Map<Byte, OrderType> TYPES = Maps.newHashMap();

    static {
        TYPES.put(CREDIT_TYPE.getType(), CREDIT_TYPE);
        TYPES.put(STORE_TYPE.getType(), STORE_TYPE);
        TYPES.put(LOAN_TYPE.getType(), LOAN_TYPE);
    }
}
