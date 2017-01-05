package com.jzfq.fms.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Integer id;

    private String prductId;

    private String productName;

    private Integer period;

    private BigDecimal monthRate;

    private BigDecimal yearRate;

    private Integer applyProduct;

    private String productDesc;

    private Date createTime;

    private Date updateTime;

    private int isDeleted;

    private Integer parentId;

    private String parentName;

    private String creater;

    private BigDecimal dayRate;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrductId() {
        return prductId;
    }

    public void setPrductId(String prductId) {
        this.prductId = prductId == null ? null : prductId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(BigDecimal monthRate) {
        this.monthRate = monthRate;
    }

    public BigDecimal getYearRate() {
        return yearRate;
    }

    public void setYearRate(BigDecimal yearRate) {
        this.yearRate = yearRate;
    }

    public Integer getApplyProduct() {
        return applyProduct;
    }

    public void setApplyProduct(Integer applyProduct) {
        this.applyProduct = applyProduct;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc == null ? null : productDesc.trim();
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public BigDecimal getDayRate() {
        return dayRate;
    }

    public void setDayRate(BigDecimal dayRate) {
        this.dayRate = dayRate;
    }
}