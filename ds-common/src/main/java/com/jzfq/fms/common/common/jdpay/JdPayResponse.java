package com.jzfq.fms.common.common.jdpay;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;


public class JdPayResponse implements Serializable {
    private static final long serialVersionUID = -3710892106643060052L;
    /**
     * 版本号
     */
    @XStreamAlias("version")
    private String version;
    /**
     * 商户号
     */
    @XStreamAlias("merchant")
    private String merchant;

    /**
     * 响应结果
     */
    @XStreamAlias("result")
    private Result result;
    /**
     * 加密后的响应内容
     */
    @XStreamAlias("encrypt")
    private String encrypt;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

}
