package com.jzfq.fms.vo.jdpay;


import com.jzfq.fms.common.common.jdpay.JdPayBaseResponse;

import java.util.List;

public class AsynNotifyResponse extends JdPayBaseResponse {

	private static final long serialVersionUID = -4212178959586736782L;

	/**
     * 版本号
     */
    private String version;
    /**
     * 商户号
     */
    private String merchant;
    /**
     * 设备号
     */
    private String device;
    /**
     * 交易流水  数字或字母
     */
    private String tradeNum;
    /**
     * 0:消费,1:退款
     */
    private String tradeType;
    
    /**
     * 交易列表
     */
    private List<PayTradeVo> payList;

	private String amount;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

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
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getTradeNum() {
		return tradeNum;
	}
	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}
	public List<PayTradeVo> getPayList() {
		return payList;
	}
	public void setPayList(List<PayTradeVo> payList) {
		this.payList = payList;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AsynNotifyResponse [version=");
		builder.append(version);
		builder.append(", merchant=");
		builder.append(merchant);
		builder.append(", device=");
		builder.append(device);
		builder.append(", tradeNum=");
		builder.append(tradeNum);
		builder.append(", tradeType=");
		builder.append(tradeType);
		builder.append(", payList=");
		builder.append(payList);
		builder.append(", oTradeNum=");
		builder.append("]");
		return builder.toString();
	}
    
}
