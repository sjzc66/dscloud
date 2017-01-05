package com.jzfq.fms.domain;

import java.math.BigDecimal;
import java.util.Date;

public class PayLog {
    private Integer id;

    private String version;

    private String merchant;

    private String tradenum;

    private String tradename;

    private Date tradetime;

    private BigDecimal amount;

    private String ordertype;

    private String currency;

    private String callbackurl;

    private String notifyurl;

    private String userid;

    private String usertype;

    private String sign;

    private String orderid;

    private String period;

    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant == null ? null : merchant.trim();
    }

    public String getTradenum() {
        return tradenum;
    }

    public void setTradenum(String tradenum) {
        this.tradenum = tradenum == null ? null : tradenum.trim();
    }

    public String getTradename() {
        return tradename;
    }

    public void setTradename(String tradename) {
        this.tradename = tradename == null ? null : tradename.trim();
    }

    public Date getTradetime() {
        return tradetime;
    }

    public void setTradetime(Date tradetime) {
        this.tradetime = tradetime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype == null ? null : ordertype.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl == null ? null : callbackurl.trim();
    }

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl == null ? null : notifyurl.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype == null ? null : usertype.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
}