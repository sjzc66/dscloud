package com.jzfq.fms.common.enums;

/**
 * Created by huyinglin on 2016/12/16.
 */
public enum JdPaySource {

    PC("pc端"),

    MOBILE("移动端");

    String JdPaySourceName;

    JdPaySource(String JdPaySourceName){
        this.JdPaySourceName=JdPaySourceName;
    }

    public String getJdPaySourceName() {
        return JdPaySourceName;
    }


}
