package com.jzfq.fms.common.enums;

/**
 * Created by huyinglin on 2016/11/30.
 */
public enum RepayPattern {

    ALIPAY("支付宝"),

    WECHAT("微信"),

    JINGDONGPAY("京东快捷支付"),

    BANK("银行账户");

    String pattern;

    private RepayPattern(String pattern){
        this.pattern=pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
