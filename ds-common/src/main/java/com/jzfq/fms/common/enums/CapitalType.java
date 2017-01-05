package com.jzfq.fms.common.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by zhishuo on 11/9/16.
 */
public enum CapitalType {
    //资金方0自营 1海航 2海尔
    //0自营
    ZY_TYPE((byte) 0),
    //1海航
    HH_TYPE((byte) 1),
    //2海尔
    HE_TYPE((byte) 2),
    //3众网
    ZW_TYPE((byte) 3);

    private byte type;

    private CapitalType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public static final Map<Byte, CapitalType> TYPES = Maps.newHashMap();

    static {
        TYPES.put(ZY_TYPE.getType(), ZY_TYPE);
        TYPES.put(HH_TYPE.getType(), HH_TYPE);
        TYPES.put(HE_TYPE.getType(), HE_TYPE);
        TYPES.put(ZW_TYPE.getType(), ZW_TYPE);
    }
}
