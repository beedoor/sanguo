package com.game.sanguo.domain;

import java.util.Date;

public class GoldSearchInfo {
	Long addGold=new Long(0);
	String cityIdList;
	String errCode;
	String errMsg;
	Long gold=new Long(0);
	Date resDate;
	public long getAddGold() {
		return addGold;
	}
	public void setAddGold(long addGold) {
		this.addGold = addGold;
	}
	public String getCityIdList() {
		return cityIdList;
	}
	public void setCityIdList(String cityIdList) {
		this.cityIdList = cityIdList;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public long getGold() {
		return gold;
	}
	public void setGold(long gold) {
		this.gold = gold;
	}
	public Date getResDate() {
		return resDate;
	}
	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}
	@Override
	public String toString() {
		return "GoldSearchInfo [addGold=" + addGold + ", cityIdList=" + cityIdList + ", errCode=" + errCode + ", errMsg=" + errMsg + ", gold=" + gold + "]";
	}
	
}
