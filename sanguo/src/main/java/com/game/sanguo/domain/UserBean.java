package com.game.sanguo.domain;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.game.sanguo.util.LoginGameInfo;

public class UserBean {

	private AtomicInteger numberId = new AtomicInteger(0);
	private AtomicInteger batchId = new AtomicInteger(0);
	private String sessionId;
	private String chatSessionId;
	private String userName;
	private String password;
	private Long userID=0L;
	private String checkId;
	private Long areaId;
	private Long reLoginTime=0L;
	private ClientUpdateInfo clientInfo;

	private LoginGameInfo loginGameInfo;
	
	private Boolean isSuspend=false;
	private Map<Long,GameAreaInfo> gameAreaInfoMap = new HashMap<Long,GameAreaInfo>();
	
	private Configure configure;
	public UserBean() {
		super();
	}

	public UserBean(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public LoginGameInfo getLoginGameInfo() {
		return loginGameInfo;
	}

	public void setLoginGameInfo(LoginGameInfo loginGameInfo) {
		this.loginGameInfo = loginGameInfo;
	}

	public void reSetNumberIdAndBatchId()
	{
		numberId.set(0);
//		batchId.set(0);
	}
	public Configure getConfigure() {
		return configure;
	}

	public void setConfigure(Configure configure) {
		this.configure = configure;
	}

	public boolean isSuspend() {
		return isSuspend;
	}

	public Long getReLoginTime() {
		return reLoginTime;
	}

	public void setReLoginTime(Long reLoginTime) {
		this.reLoginTime = reLoginTime;
	}

	public void setSuspend(boolean isSuspend) {
		this.isSuspend = isSuspend;
	}

	public GameAreaInfo getGameAreaInfo(String areaId) {
		return gameAreaInfoMap.get(areaId);
	}

	public ClientUpdateInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientUpdateInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public String getUrlPrx()
	{
		return String.format("http://%s", gameAreaInfoMap.get(areaId).getUrl());
	}
	public void putGameAreaInfo(GameAreaInfo gameAreaInfo) {
		gameAreaInfoMap.put(gameAreaInfo.getId(), gameAreaInfo);
	}

	public Long getAreaId() {
		return areaId;
	}


	public void setAreaId(Long areaId) {
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
	public int getBatchIdNoIncrement() {
		return batchId.get();
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

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "UserBean [numberId=" + numberId + ", batchId=" + batchId + ", sessionId=" + sessionId + ", chatSessionId=" + chatSessionId + ", userName=" + userName + ", password=" + password
				+ ", userID=" + userID + ", checkId=" + checkId + ", areaId=" + areaId + ", reLoginTime=" + reLoginTime + ", clientInfo=" + clientInfo + ", loginGameInfo=" + loginGameInfo
				+ ", isSuspend=" + isSuspend + ", gameAreaInfoMap=" + gameAreaInfoMap + ", configure=" + configure + "]";
	}

}
