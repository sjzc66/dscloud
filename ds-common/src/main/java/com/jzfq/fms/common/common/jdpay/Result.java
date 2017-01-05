package com.jzfq.fms.common.common.jdpay;


public class Result {
	/**
	 * 响应码
	 */
	private String code;
	/**
	 * 响应信息
	 */
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Result [code=");
		builder.append(code);
		builder.append(", desc=");
		builder.append(desc);
		builder.append("]");
		return builder.toString();
	}

}
