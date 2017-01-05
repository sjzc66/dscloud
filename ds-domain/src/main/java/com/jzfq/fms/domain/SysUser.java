package com.jzfq.fms.domain;


import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {
    private static final long serialVersionUID = -5395005303986269023L;

    private Integer id;

    private String username;

    private String realName;

    private String password;

    private String position;

    private Date createTime;

    private Date updateTime;

    private Byte delFlag;

    private String roleIds;

    //private SysRole role;

    private Integer[] roleIdList;

    //vo属性
    private String roleName;
    private Byte roleCode;
    private Byte productCode;

    //志硕为了安全加密添加的2个字段
    private String salt;//密码盐
    private String credentialsSalt;//用户或+盐


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt() {
        return credentialsSalt;
    }

    public void setCredentialsSalt(String credentialsSalt) {
        this.credentialsSalt = credentialsSalt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
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

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public Integer[] getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(Integer[] roleIdList) {
        this.roleIdList = roleIdList;
    }
}