package com.jzfq.fms.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * Created by huyinglin on 2016/11/22.
 *
 */
public enum PresentOrderStatus {

    SUBMIT_TOBE_CONFIRMED("已提交，待确认"),

    PENDING_PAYMENT("待打款"),

    ALREADY_PAYMENT("已打款，待到账"),

    ARRIVAL_ACCOUNT("已到账，分期还款中"),

    REPAYMENT_END("分期还款结束"),

    FAILURE_TO_PLAY("打款失败"),

    ORDER_CANCELLATION("订单取消");

    String presentOrderStatusName;

    PresentOrderStatus(String presentOrderStatusName) {
        this.presentOrderStatusName = presentOrderStatusName;
    }

    public void setPresentOrderStatusName(String presentOrderStatus) {
        this.presentOrderStatusName = presentOrderStatus;
    }

    public String getPresentOrderStatusName() {
        return presentOrderStatusName;
    }

    public static PresentOrderStatus getByOrdinal(int i) {
        PresentOrderStatus[] values = PresentOrderStatus.values();
        for (PresentOrderStatus presentOrderStatus : values) {
            if (i == presentOrderStatus.ordinal()) {
                return presentOrderStatus;
            }
        }
        return null;
    }
    public static PresentOrderStatus getByName(String name) {
        PresentOrderStatus[] values = PresentOrderStatus.values();
        for (PresentOrderStatus presentOrderStatus : values) {
            if (StringUtils.equalsIgnoreCase(name, presentOrderStatus.name())) {
                return presentOrderStatus;
            }
        }
        return null;
    }
}
