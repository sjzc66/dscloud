package com.jzfq.fms.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ValidOrder {
    private Integer id;

    private Integer orderId;

    private BigDecimal rate;

    private BigDecimal amount;

    private Byte period;

    private Byte type;

    private Date repayDate;

    private Date storeOrderTime;

    private Byte behead;

    private Byte isDeal;

    private Date createTime;

    private Date updateTime;

    private Byte delFlag;

    private Byte freeInterest;

    private Byte source;

    private Byte alreadyRepaidPeriod;

    private Byte capitalSide;
    
    private Integer customerId;

    private String customerJson;
    
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Byte getPeriod() {
        return period;
    }

    public void setPeriod(Byte period) {
        this.period = period;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Date getStoreOrderTime() {
        return storeOrderTime;
    }

    public void setStoreOrderTime(Date storeOrderTime) {
        this.storeOrderTime = storeOrderTime;
    }

    public Byte getBehead() {
        return behead;
    }

    public void setBehead(Byte behead) {
        this.behead = behead;
    }

    public Byte getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Byte isDeal) {
        this.isDeal = isDeal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Byte getFreeInterest() {
        return freeInterest;
    }

    public void setFreeInterest(Byte freeInterest) {
        this.freeInterest = freeInterest;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Byte getAlreadyRepaidPeriod() {
        return alreadyRepaidPeriod;
    }

    public void setAlreadyRepaidPeriod(Byte alreadyRepaidPeriod) {
        this.alreadyRepaidPeriod = alreadyRepaidPeriod;
    }

    public Byte getCapitalSide() {
        return capitalSide;
    }

    public void setCapitalSide(Byte capitalSide) {
        this.capitalSide = capitalSide;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerJson() {
        return customerJson;
    }

    public void setCustomerJson(String customerJson) {
        this.customerJson = customerJson;
    }
}