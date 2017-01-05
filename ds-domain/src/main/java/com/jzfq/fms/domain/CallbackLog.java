package com.jzfq.fms.domain;

import java.util.Date;

public class CallbackLog {
    private Integer id;

    private String tradenum;

    private String tradetype;

    private String status;

    private String paytype;

    private Long amount;

    private String currency;

    private Date tradetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTradenum() {
        return tradenum;
    }

    public void setTradenum(String tradenum) {
        this.tradenum = tradenum == null ? null : tradenum.trim();
    }

    public String getTradetype() {
        return tradetype;
    }

    public void setTradetype(String tradetype) {
        this.tradetype = tradetype == null ? null : tradetype.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public Date getTradetime() {
        return tradetime;
    }

    public void setTradetime(Date tradetime) {
        this.tradetime = tradetime;
    }
}