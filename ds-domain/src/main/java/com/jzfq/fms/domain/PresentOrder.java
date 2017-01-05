package com.jzfq.fms.domain;

import java.math.BigDecimal;
import java.util.Date;

public class PresentOrder {
    private Integer id;

    private String orderId;

    private String customerId;

    private Date orderTime;

    private Date repayDate;

    private BigDecimal loanLimit;

    private Integer periods;

    private BigDecimal interest;

    private String productCode;

    private BigDecimal monthly;

    private String username;

    private String phone;

    private Byte source;

    private Byte orderStatus;

    private String bankCode;

    private String bankName;

    private String bankAddress;

    private String recommendCode;

    private Byte whitebarAccount;

    private Byte mallAccount;

    private String productName;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Byte getWhitebarAccount() {
        return whitebarAccount;
    }

    public void setWhitebarAccount(Byte whitebarAccount) {
        this.whitebarAccount = whitebarAccount;
    }

    public Byte getMallAccount() {
        return mallAccount;
    }

    public void setMallAccount(Byte mallAccount) {
        this.mallAccount = mallAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public BigDecimal getLoanLimit() {
        return loanLimit;
    }

    public void setLoanLimit(BigDecimal loanLimit) {
        this.loanLimit = loanLimit;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }



    public BigDecimal getMonthly() {
        return monthly;
    }

    public void setMonthly(BigDecimal monthly) {
        this.monthly = monthly;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress == null ? null : bankAddress.trim();
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode == null ? null : recommendCode.trim();
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }
}