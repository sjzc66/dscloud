package com.jzfq.fms.domain;

import java.util.Date;

public class TransDetail {
    private Integer id;

    private String transType;

    private Float amount;

    private Date transTime;

    private String transStatus;

    private String payPattern;

    private String currency;

    private Integer orderid;

    private Integer periods;

    private String transChannel;

    private String transNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    public String getPayPattern() {
        return payPattern;
    }

    public void setPayPattern(String payPattern) {
        this.payPattern = payPattern == null ? null : payPattern.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public String getTransChannel() {
        return transChannel;
    }

    public void setTransChannel(String transChannel) {
        this.transChannel = transChannel == null ? null : transChannel.trim();
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }
}