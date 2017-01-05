package com.jzfq.fms.domain;

import java.math.BigDecimal;
import java.util.Date;

public class AccountLog {
    private Integer id;

    private Date createTime;

    private Date startTime;

    private Date endTime;

    private Integer accountId;

    private Integer customerId;

    private BigDecimal modifyAmount;

    private BigDecimal allValidAmount;

    private BigDecimal cashValidAmount;

    private BigDecimal consumeValidAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getModifyAmount() {
        return modifyAmount;
    }

    public void setModifyAmount(BigDecimal modifyAmount) {
        this.modifyAmount = modifyAmount;
    }

    public BigDecimal getAllValidAmount() {
        return allValidAmount;
    }

    public void setAllValidAmount(BigDecimal allValidAmount) {
        this.allValidAmount = allValidAmount;
    }

    public BigDecimal getCashValidAmount() {
        return cashValidAmount;
    }

    public void setCashValidAmount(BigDecimal cashValidAmount) {
        this.cashValidAmount = cashValidAmount;
    }

    public BigDecimal getConsumeValidAmount() {
        return consumeValidAmount;
    }

    public void setConsumeValidAmount(BigDecimal consumeValidAmount) {
        this.consumeValidAmount = consumeValidAmount;
    }
}