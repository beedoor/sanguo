package com.game.sanguo.task;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.game.sanguo.domain.CitySearchInfo;
import com.game.sanguo.domain.GoldSearchInfo;
import com.game.sanguo.domain.UserBean;
import com.game.sanguo.util.GameUtil;

/**
 * 元宝定时任务
 * 
 * @author beedoor
 * 
 */
public class CitySearchAndGoldTask extends GameTask {

	public CitySearchAndGoldTask(UserBean userBean) {
		super();
		this.userBean = userBean;
	}

	public void run() {
		try {
			if (userBean.getConfigure().getSearchResource() == 1) {
				msgIdGetGold();
				msgIdCitySearch();
			}
		} catch (Throwable e) {
			logger.error("定时搜索资源任务异常", e);
		}
	}

	private void msgIdGetGold() {
		PostMethod postMethod = new PostMethod(String.format("%s/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid=%s;mid=%s", userBean.getUrlPrx(), userBean.getSessionId(),
				userBean.getSessionId()));
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		// 使用系统提供的默认的恢复策略
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", userBean.getSessionId()));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + userBean.getNumberId()));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:0"));
		postMethod.addParameter(new NameValuePair("c0-e2", "string:msgTypeCity"));
		postMethod.addParameter(new NameValuePair("c0-e3", "string:msgIdGetGold"));
		postMethod.addParameter(new NameValuePair("c0-e4", "string:"));
		postMethod.addParameter(new NameValuePair("c0-param1", "Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", "" + userBean.getBatchId()));
		doRequest(postMethod);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			String s1 = null;
			while ((s1 = br.readLine()) != null) {
				if (s1.startsWith("dwr")) {
					break;
				}
			}
			GoldSearchInfo goldInfo = initBeanInfo(GoldSearchInfo.class, s1);
			logger.info("当前金币[{}],获得金币[{}]", new Object[] { goldInfo.getGold(), goldInfo.getAddGold() });
		} catch (Exception e) {
			logger.error("转换异常", e);
		}
	}

	private void msgIdCitySearch() {
		PostMethod postMethod = new PostMethod(String.format("%s/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid=%s;mid=%s", userBean.getUrlPrx(), userBean.getSessionId(),
				userBean.getSessionId()));
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		// 使用系统提供的默认的恢复策略
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", userBean.getSessionId()));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + userBean.getNumberId()));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:0"));
		postMethod.addParameter(new NameValuePair("c0-e2", "string:msgTypeWorld"));
		postMethod.addParameter(new NameValuePair("c0-e3", "string:msgIdCitySearch"));
		postMethod.addParameter(new NameValuePair("c0-e4", "string:"));
		postMethod.addParameter(new NameValuePair("c0-param1", "Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", "" + userBean.getBatchId()));
		doRequest(postMethod);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			String s1 = null, content = null, hero = null, item = null;
			while ((s1 = br.readLine()) != null) {
				if (s1.startsWith("dwr")) {
					content = s1;
				}
				if (s1.startsWith("s1")) {
					hero = s1;
				}
				if (s1.startsWith("s2")) {
					item = s1;
				}
			}
			CitySearchInfo cityInfo = initBeanInfo(CitySearchInfo.class, content);
			cityInfo.setHeros(hero);
			cityInfo.setItems(item);
			logger.info("当前金币[{}],获得金币[{}],武将[{}],物品[{}]",
					new Object[] { cityInfo.getGold(), cityInfo.getSearchGold(), GameUtil.ingoreNull(cityInfo.getHeros()), GameUtil.ingoreNull(cityInfo.getItems()) });
		} catch (Exception e) {
			logger.error("转换异常", e);
		}
	}
}
