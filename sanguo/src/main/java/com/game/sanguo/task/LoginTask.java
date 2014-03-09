package com.game.sanguo.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.game.sanguo.domain.ClientUpdateInfo;
import com.game.sanguo.domain.GameAreaInfo;
import com.game.sanguo.domain.LoginByEmailInfo;
import com.game.sanguo.domain.UserBean;
import com.game.sanguo.util.LoginGameInfo;

public class LoginTask extends GameTask {

	private int delay;
	private TimeUnit timeUnit;

	public LoginTask(UserBean userBean, int delay, TimeUnit timeUnit) {
		super();
		this.delay = delay;
		this.timeUnit = timeUnit;
		this.userBean = userBean;
	}

	public static void main(String[] args) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
	}

	public void doAction() {
		try {
			logger.info(delay + " 秒后重新登陆");
			if (delay != 0) {
				sleep(delay, timeUnit);
			}
			userBean.reSetNumberIdAndBatchId();

			getBaseInfoNew();
			loginByEmail();
			sleep(5);// 游戏加载
			getServerList();
			clientUpdate();
			loginGame();
			sleep(5);// 游戏加载
			startChat();
		} catch (Throwable e) {
			logger.error("登录异常", e);
		}
	}

	private void getBaseInfoNew() {
		PostMethod postMethod = new PostMethod("http://118.26.192.76:8080/portal/dwr//call/plaincall/UserBean.getBaseInfoNew.dwr;jsessionid=");
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Connection", "Keep-Alive");

		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", ""));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "UserBean"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "getBaseInfoNew"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "string:"));
		postMethod.addParameter(new NameValuePair("c0-param1", "string:ANDK"));
		postMethod.addParameter(new NameValuePair("c0-param2", "string:10"));
		postMethod.addParameter(new NameValuePair("c0-param3", "string:com.noumena.android.olcn.of"));
		postMethod.addParameter(new NameValuePair("batchId", "" + userBean.getBatchId()));

		doRequest(postMethod);
	}

	private void loginByEmail() {
		PostMethod postMethod = new PostMethod("http://118.26.192.76:8080/portal/dwr//call/plaincall/UserBean.loginByEmail.dwr;jsessionid=");
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Connection", "Keep-Alive");

		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", ""));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "UserBean"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "loginByEmail"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "string:" + userBean.getUserName()));
		postMethod.addParameter(new NameValuePair("c0-param1", "string:" + userBean.getPassword()));
		postMethod.addParameter(new NameValuePair("batchId", "" + userBean.getBatchId()));

		doRequest(postMethod);

		try {
			LoginByEmailInfo beanInfo = initBeanInfo(LoginByEmailInfo.class, postMethod.getResponseBodyAsStream(), "dwr");
			logger.info(beanInfo.toString());
			userBean.setCheckId(beanInfo.getCheckId());
			userBean.setAreaId(beanInfo.getServerId());
			userBean.setUserID(beanInfo.getUserId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loginGame() {
		PostMethod postMethod = new PostMethod(String.format("%s/hero/dwr/call/plaincall/DwrGame.loginGame.dwr;jsessionid=", userBean.getUrlPrx()));
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Connection", "Keep-Alive");

		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", ""));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrGame"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "loginGame"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + userBean.getUserID()));
		postMethod.addParameter(new NameValuePair("c0-param1", "string:ANDK"));
		postMethod.addParameter(new NameValuePair("c0-param2", "number:" + userBean.getAreaId()));
		postMethod.addParameter(new NameValuePair("c0-param3", "string:" + userBean.getCheckId()));
		postMethod.addParameter(new NameValuePair("c0-param4", "string:602102200"));
		postMethod.addParameter(new NameValuePair("c0-param5", "string:"));
		postMethod.addParameter(new NameValuePair("c0-param6", "string:official"));
		postMethod.addParameter(new NameValuePair("batchId", "" + userBean.getBatchId()));

		doRequest(postMethod);

		try {
			LoginGameInfo beanInfo = initBeanInfo(LoginGameInfo.class, postMethod.getResponseBodyAsStream(), "dwr");
			logger.info(beanInfo.toString());
			userBean.setLoginGameInfo(beanInfo);
			userBean.setSessionId(beanInfo.getSessionId());
		} catch (Exception e) {
			logger.error("登录服务器异常",e);
		}
	}

	public void getServerList() {
		// 创建GET方法的实例
		PostMethod postMethod = new PostMethod("http://118.26.192.76:8080/VersionServerIOS/dwr//call/plaincall/DwrEntry.getServerList.dwr;jsessionid");
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", ""));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrEntry"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "getServerList"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "string:8"));
		postMethod.addParameter(new NameValuePair("batchId", "" + userBean.getBatchId()));
		doRequest(postMethod);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			String s1 = null;
			while ((s1 = br.readLine()) != null) {
				if (s1.startsWith("s")) {
					// 游戏服务器了
					int prex = s1.indexOf(".");
					String areaIdStr = s1.substring(0, prex);
					// 进行替换咯
					s1 = s1.replaceAll(areaIdStr + "[.]", "");
					s1 = s1.replaceAll("[:]", "#");
					s1 = s1.replaceAll("[=]", ":");
					s1 = s1.replaceAll("[;]", ",");
					GameAreaInfo gameArea = initBeanInfo(GameAreaInfo.class, "{" + s1 + "}");
					String url = gameArea.getUrl().replaceAll("#", ":");
					gameArea.setUrl(url);
					logger.info(gameArea.toString());
					userBean.putGameAreaInfo(gameArea);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clientUpdate() {
		// 创建GET方法的实例
		PostMethod postMethod = new PostMethod("http://118.26.192.76:8080/VersionServer/dwr//call/plaincall/DwrEntry.clientUpdate.dwr;jsessionid");
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", ""));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrEntry"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "clientUpdate"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "string:SC"));
		postMethod.addParameter(new NameValuePair("c0-param1", "string:2"));
		postMethod.addParameter(new NameValuePair("c0-param2", "string:460"));
		postMethod.addParameter(new NameValuePair("batchId", "" + userBean.getBatchId()));
		doRequest(postMethod);

		try {
			ClientUpdateInfo clientInfo = initBeanInfo(ClientUpdateInfo.class, postMethod.getResponseBodyAsStream(), "dwr");
			userBean.setClientInfo(clientInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startChat() {
		PostMethod postMethod = new PostMethod(String.format("%s/ChatServer/dwr/call/plaincall/DwrChat.startChat.dwr;jsessionid", userBean.getUrlPrx()));
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", ""));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrChat"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "startChat"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + userBean.getAreaId()));
		postMethod.addParameter(new NameValuePair("c0-param1", "number:1"));
		postMethod.addParameter(new NameValuePair("batchId", "" + userBean.getBatchId()));
		doRequest(postMethod);

		Header header = postMethod.getResponseHeader("Set-Cookie");
		HeaderElement[] h = header.getElements();
		if (h.length > 0) {
			logger.info("获取新的聊天sessionID：" + h[0].getValue());
			userBean.setChatSessionId(h[0].getValue());
		} else {
			logger.error("登录异常");
		}
	}
}
