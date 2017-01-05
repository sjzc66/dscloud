package com.jzfq.fms.common.common.yijipay;

import java.io.Serializable;

/**
 * Created by liu on 2016/12/19.
 */
public class YijiPayBaseResponse implements Serializable {

    private static final long serialVersionUID = -2831610169280507154L;

    /**
     * 协议类型
     */
    private String protocol;

    /**
     * 服务代码
     */
    private String service;

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
     * 返回码
     */
    private String resultCode;

    /**
     * 返回码描述信息
     */
    private String resultMessage;

    /**
     * 成功标识 true：成功 false：失败
     */
    private String success;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
