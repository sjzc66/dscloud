package com.jzfq.fms.common.enums;

/**
 * Created by huyinglin on 2016/11/23.
 */
public enum CustomerType {

    STUDENT("学生"),

    WHITE_COLLAR("白领");

    String customerTypeName;

    CustomerType(String customerTypeName){
        this.customerTypeName=customerTypeName;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

}
