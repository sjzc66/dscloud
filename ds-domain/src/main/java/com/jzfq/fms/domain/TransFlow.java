package com.jzfq.fms.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TransFlow {
    private Integer id;

    private Integer planId;

    private Integer periods;

    private Integer pattern;

    private BigDecimal amount;

    private Byte payType;

    private Date flowTime;

    private String payid;

    private Integer customerId;

    private Integer orderId;

    private String personer;

    private Integer personerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public Integer getPattern() {
        return pattern;
    }

    public void setPattern(Integer pattern) {
        this.pattern = pattern;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Date getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Date flowTime) {
        this.flowTime = flowTime;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid == null ? null : payid.trim();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPersoner() {
        return personer;
    }

    public void setPersoner(String personer) {
        this.personer = personer == null ? null : personer.trim();
    }

    public Integer getPersonerId() {
        return personerId;
    }

    public void setPersonerId(Integer personerId) {
        this.personerId = personerId;
    }
}