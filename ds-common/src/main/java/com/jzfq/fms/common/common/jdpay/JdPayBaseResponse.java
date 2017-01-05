package com.jzfq.fms.common.common.jdpay;


import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;


public class JdPayBaseResponse implements Serializable {
	private static final long serialVersionUID = -6199514389973480177L;
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
	 * 设备号
	 */
	@XStreamAlias("device")
	private String device;

	/**
	 * 签名
	 */
	@XStreamAlias("sign")
	private String sign;
	/**
	 * 响应结果
	 */
	@XStreamAlias("result")
	private Result result;

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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JdPayBaseResponse [version=");
		builder.append(version);
		builder.append(", merchant=");
		builder.append(merchant);
		builder.append(", device=");
		builder.append(device);
		builder.append(", sign=");
		builder.append(sign);
		builder.append(", result=");
		builder.append(result);
		builder.append("]");
		return builder.toString();
	}

}
