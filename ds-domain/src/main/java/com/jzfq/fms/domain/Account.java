package com.jzfq.fms.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Account {
    private Integer id;

    private Integer customerId;

    private Integer creditId;

    private Date closingDate;

    private Date accountDate;

    private Byte activated;

    private BigDecimal allValidAmount;

    private BigDecimal cashValidAmount;

    private BigDecimal consumeValidAmount;

    private BigDecimal allInitAmount;

    private BigDecimal cashInitAmount;

    private BigDecimal consumeInitAmount;

    private BigDecimal cachFrozenAmount;

    private BigDecimal consumeFrozenAmount;

    /**
     * 最大额度
     */
    private BigDecimal maxAmount;

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCreditId() {
        return creditId;
    }

    public void setCreditId(Integer creditId) {
        this.creditId = creditId;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public Byte getActivated() {
        return activated;
    }

    public void setActivated(Byte activated) {
        this.activated = activated;
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

    public BigDecimal getAllInitAmount() {
        return allInitAmount;
    }

    public void setAllInitAmount(BigDecimal allInitAmount) {
        this.allInitAmount = allInitAmount;
    }

    public BigDecimal getCashInitAmount() {
        return cashInitAmount;
    }

    public void setCashInitAmount(BigDecimal cashInitAmount) {
        this.cashInitAmount = cashInitAmount;
    }

    public BigDecimal getConsumeInitAmount() {
        return consumeInitAmount;
    }

    public void setConsumeInitAmount(BigDecimal consumeInitAmount) {
        this.consumeInitAmount = consumeInitAmount;
    }

    public BigDecimal getCachFrozenAmount() {
        return cachFrozenAmount;
    }

    public void setCachFrozenAmount(BigDecimal cachFrozenAmount) {
        this.cachFrozenAmount = cachFrozenAmount;
    }

    public BigDecimal getConsumeFrozenAmount() {
        return consumeFrozenAmount;
    }

    public void setConsumeFrozenAmount(BigDecimal consumeFrozenAmount) {
        this.consumeFrozenAmount = consumeFrozenAmount;
    }
}