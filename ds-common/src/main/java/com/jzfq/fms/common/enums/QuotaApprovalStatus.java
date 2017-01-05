package com.jzfq.fms.common.enums;

/**
 * Created by huyinglin on 2016/11/22.
 */
public enum QuotaApprovalStatus {

    ALREADY_ACTIVATED("已激活"),

    TOBE_UPLOADED("待资料上传"),

    ACTIVATION_FAILED("激活失败"),

    DATA_RETRANSMISSION("资料重传"),

    PRELIMINARY_EXAMINATION("初审中"),

    INTHE_FINAL("终审中");

    String quotaApprovalStatusName;

    QuotaApprovalStatus(String quotaApprovalStatusName){
        this.quotaApprovalStatusName=quotaApprovalStatusName;
    }

    public String getQuotaApprovalStatusName() {
        return quotaApprovalStatusName;
    }

    public void setQuotaApprovalStatusName(String quotaApprovalStatusName) {
        this.quotaApprovalStatusName = quotaApprovalStatusName;
    }
}
