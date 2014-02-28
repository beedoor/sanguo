package com.game.sanguo.domain;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserBean {

	private AtomicInteger numberId = new AtomicInteger(0);
	private AtomicInteger batchId = new AtomicInteger(0);
	private String sessionId;
	private String chatSessionId;
	private String userName;
	private String password;
	private long userID;
	private String checkId;
	private String areaId;

	private Map<String,GameAreaInfo> gameAreaInfoMap = new HashMap<String,GameAreaInfo>();
	public UserBean(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public GameAreaInfo getGameAreaInfo(String areaId) {
		return gameAreaInfoMap.get(areaId);
	}

	public String getUrlPrx()
	{
		return String.format("http://%s", gameAreaInfoMap.get(areaId).getUrl());
	}
	public void putGameAreaInfo(GameAreaInfo gameAreaInfo) {
		gameAreaInfoMap.put(String.valueOf(gameAreaInfo.getId()), gameAreaInfo);
	}

	public String getAreaId() {
		return areaId;
	}


	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}


	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getChatSessionId() {
		return chatSessionId;
	}

	public void setChatSessionId(String chatSessionId) {
		this.chatSessionId = chatSessionId;
	}

	public int getNumberId() {
		return numberId.getAndIncrement();
	}
	public int getNumberIdNoIncrement() {
		return numberId.get();
	}


	public int getBatchId() {
		return batchId.getAndIncrement();
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "UserBean [numberId=" + numberId + ", batchId=" + batchId + ", sessionId=" + sessionId + ", chatSessionId=" + chatSessionId + ", userName=" + userName + ", password=" + password
				+ ", userID=" + userID + ", checkId=" + checkId + ", areaId=" + areaId + ", gameAreaInfoMap=" + gameAreaInfoMap + "]";
	}
	
	
}
