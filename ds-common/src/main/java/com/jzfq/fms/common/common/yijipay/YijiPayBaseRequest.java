package com.jzfq.fms.common.common.yijipay;

import java.io.Serializable;

/**
 * Created by liu on 2016/12/19.
 */
public class YijiPayBaseRequest implements Serializable {

    private static final long serialVersionUID = -1915986186062607270L;

    /**
     * 协议类型
     */
    private String protocol;

    /**
     * 服务版本
     */
    private String version;

    /**
     * 商户ID
     */
    private  String partnerId;

    /**
     * 请求流水号
     */
    private  String orderNo;

    /**
     * 商户订单号
     */
    private String merchOrderNo;

    /**
     * 签名方式
     */
    private String signType;

    /**
     * 签名
     */
    private String sign;

    /**
     * 页面跳转返回URL
     */
    private String returnUrl;

    /**
     * 异步通知URL
     */
    private  String notifyUrl;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMerchOrderNo() {
        return merchOrderNo;
    }

    public void setMerchOrderNo(String merchOrderNo) {
        this.merchOrderNo = merchOrderNo;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

}
