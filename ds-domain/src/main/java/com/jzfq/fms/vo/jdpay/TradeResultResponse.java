package com.jzfq.fms.vo.jdpay;

public class TradeResultResponse {
	/**
	 * 交易流水号
	 */
	private String tradeNum;
	/**
	 * 交易金额
	 */
	private String amount;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 交易时间
	 */
	private String tradeTime;
	/**
	 * 交易备注
	 */
	private String note;
	/**
	 * 交易状态
	 */
	private String status;
	
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * @return the tradeNum
	 */
	public String getTradeNum() {
		return tradeNum;
	}
	/**
	 * @param tradeNum the tradeNum to set
	 */
	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the tradeTime
	 */
	public String getTradeTime() {
		return tradeTime;
	}
	/**
	 * @param tradeTime the tradeTime to set
	 */
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	@Override
	public String toString() {
		return "TradeResultResponse [tradeNum=" + tradeNum + ", amount=" + amount + ", currency=" + currency + ", tradeTime="
				+ tradeTime + ", note=" + note + ", status=" + status + ", sign=" + sign + "]";
	}

}
