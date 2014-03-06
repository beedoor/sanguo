package com.game.sanguo.domain;

public class ContinuousLoginDaysRewardInfo {
	Long errCode = 0L;
	String errMsg;
	Long gemLeft = 0L;
	Long goldLeft = 0L;
	Long result = 0L;
	String rewardHeros;
	String rewardItems;
	public Long getErrCode() {
		return errCode;
	}
	public void setErrCode(Long errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Long getGemLeft() {
		return gemLeft;
	}
	public void setGemLeft(Long gemLeft) {
		this.gemLeft = gemLeft;
	}
	public Long getGoldLeft() {
		return goldLeft;
	}
	public void setGoldLeft(Long goldLeft) {
		this.goldLeft = goldLeft;
	}
	public Long getResult() {
		return result;
	}
	public void setResult(Long result) {
		this.result = result;
	}
	public String getRewardHeros() {
		return rewardHeros;
	}
	public void setRewardHeros(String rewardHeros) {
		this.rewardHeros = rewardHeros;
	}
	public String getRewardItems() {
		return rewardItems;
	}
	public void setRewardItems(String rewardItems) {
		this.rewardItems = rewardItems;
	}
	@Override
	public String toString() {
		return "ContinuousLoginDaysRewardInfo [errCode=" + errCode + ", errMsg=" + errMsg + ", gemLeft=" + gemLeft + ", goldLeft=" + goldLeft + ", result=" + result + ", rewardHeros=" + rewardHeros
				+ ", rewardItems=" + rewardItems + "]";
	}
}
