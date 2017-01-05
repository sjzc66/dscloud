package com.jzfq.fms.domain;

import java.util.Date;

public class SysRole {

    private Integer id;

    private String name;

    private String description;

    private Date createTime;

    private Date updateTime;

    private Byte delFlag = 0;

    private Byte roleCode;

    private Byte productCode;

    private String authIds;

    private String[] idList;

    @SuppressWarnings("unused")
    private String productType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Byte getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Byte roleCode) {
        this.roleCode = roleCode;
    }

    public Byte getProductCode() {
        return productCode;
    }

    public void setProductCode(Byte productCode) {
        this.productCode = productCode;
    }

    public String getAuthIds() {
        return authIds;
    }

    public void setAuthIds(String authIds) {
        this.authIds = authIds;
    }
    
    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String[] getIdList() {
        return idList;
    }

    public void setIdList(String[] idList) {
        this.idList = idList;
    }

    public String getProductType() {
        return productType;
    }
}