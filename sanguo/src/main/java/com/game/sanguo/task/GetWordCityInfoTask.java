package com.game.sanguo.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.game.sanguo.domain.CityInfo;
import com.game.sanguo.domain.Pair;
import com.game.sanguo.domain.ScanResource;
import com.game.sanguo.domain.UserBean;
import com.game.sanguo.util.GameUtil;
import com.game.sanguo.util.ResourceConfig;
import com.game.sanguo.util.UserConfig;
import com.game.sanguo.util.XlsFileUtil;

public class GetWordCityInfoTask extends GameTask {

	ResourceConfig resourceConfig = null;
	UserConfig userConfig = null;

	public GetWordCityInfoTask(UserBean userBean, ResourceConfig resourceConfig) {
		super();
		this.userBean = userBean;
		this.resourceConfig = resourceConfig;
	}

	public void doAction() {
		try {
			ScanResource scan = userBean.getConfigure().getScanResource();
			List<Pair<String, List<CityInfo>>> createDataList = new ArrayList<Pair<String, List<CityInfo>>>();
			if (scan.getTreasure() == 1) {
				logger.info("开始计算元宝山资源信息");
				List<CityInfo> resultList = getResourceDetailInfo(resourceConfig.getTreasureAreaIdList());
				createDataList.add(Pair.makePair("元宝山", resultList));
			} else {
				logger.info("配置禁止扫描元宝山资源");
			}
			if (scan.getMarket() == 1) {
				logger.info("开始计算黑市资源信息");
				List<CityInfo> resultList = getResourceDetailInfo(resourceConfig.getMarketAreaIdList());
				createDataList.add(Pair.makePair("黑市", resultList));
			} else {
				logger.info("配置禁止扫描黑市资源");
			}
			if (scan.getGold() == 1) {
				logger.info("开始计算金矿资源信息");
				List<CityInfo> resultList = getResourceDetailInfo(resourceConfig.getGoldAreaIdList());
				createDataList.add(Pair.makePair("金矿", resultList));
			} else {
				logger.info("配置禁止扫描金矿资源");
			}
			if (scan.getSolider() == 1) {
				logger.info("开始计算兵营资源信息");
				List<CityInfo> resultList = getResourceDetailInfo(resourceConfig.getSoliderAreaIdList());
				createDataList.add(Pair.makePair("兵营", resultList));
			} else {
				logger.info("配置禁止扫描兵营资源");
			}
			XlsFileUtil.createSearchResultFile(createDataList, userBean.getConfigure().getScanResource().getSortType());
		} catch (Exception e) {
			logger.error("获取资源信息异常", e);
		}
	}

	private List<CityInfo> getResourceDetailInfo(List<String> zuobiaoList) {
		List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
		if (zuobiaoList != null) {
			logger.info("共有 " + zuobiaoList.size() + " 个资源信息");
			for (String zuobiao : zuobiaoList) {
				if (!zuobiao.equals("")) {
					CityInfo ciInfo = msgIdWorldCityInfo(zuobiao);
					if (null != ciInfo) {
						cityInfoList.add(ciInfo);
					}
					sleep(userBean.getConfigure().getScanResource().getWaitTime(), TimeUnit.MILLISECONDS);
				}
			}
		}
		return cityInfoList;
	}

	private CityInfo msgIdWorldCityInfo(String zuobiao) {
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
		postMethod.addParameter(new NameValuePair("c0-e1", "number:" + zuobiao));
		postMethod.addParameter(new NameValuePair("c0-e2", "string:msgTypeWorld"));
		postMethod.addParameter(new NameValuePair("c0-e3", "string:msgIdWorldCityInfo"));
		postMethod.addParameter(new NameValuePair("c0-e4", "string:"));
		postMethod.addParameter(new NameValuePair("c0-param1", "Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", "" + userBean.getBatchId()));
		doRequest(postMethod);
		CityInfo cityInfo = null;
		try {
			cityInfo = convert(postMethod.getResponseBodyAsStream());
			logCityInfo(cityInfo);
		} catch (Exception e) {
			logger.error("转换异常", e);
		}

		return cityInfo;
	}

	private void logCityInfo(CityInfo cityInfo) {
		if (cityInfo.getOccupierId() == 0) {
			logger.info(cityInfo.getId() + " 类别" + decodeResourceType(cityInfo.getTypeAsInt()) + "资源为空");
			return;
		}
		Date currTime = new Date(System.currentTimeMillis());
		long rage = 0;
		if (cityInfo.getOccupierVipLv() == 9) {
			rage = 24 * 60 * 60 * 1000 * 3;
		} else if (cityInfo.getOccupierVipLv() == 8) {
			rage = 24 * 60 * 60 * 1000 * 2;
		} else {
			rage = 24 * 60 * 60 * 1000;
		}
		long relayTime = rage - (currTime.getTime() - cityInfo.getOccupyTime().getTime());
		long realayHour = relayTime / (60 * 60 * 1000);
		long realayMins = (relayTime % (60 * 60 * 1000)) / (60 * 1000);
		long realaySec = (relayTime % (60 * 1000)) / (1000);

		logger.info(String.format("坐标[%s]，占领者[%s]，占领者VIP级别[%s] ,资源类别[%s],占领时间[%s],剩余时间[%s:%s:%s]", cityInfo.getId(), GameUtil.parseUnicode(cityInfo.getOccupierName()), cityInfo.getOccupierVipLv(),
				decodeResourceType(cityInfo.getTypeAsInt()), GameUtil.parseDate(cityInfo.getOccupyTime()), realayHour, realayMins, realaySec));
	}

	private Object decodeResourceType(long typeAsInt) {

		if (typeAsInt == 6) {
			return "兵营";
		} else if (typeAsInt == 5) {
			return "点将";
		} else if (typeAsInt == 4) {
			return "黑市";
		} else if (typeAsInt == 3) {
			return "元宝山";
		} else if (typeAsInt == 2) {
			return "金矿";
		}
		return "";
	}

	/**
	 * 做出转换
	 * 
	 * @param io
	 * @return
	 * @throws Exception
	 */
	private CityInfo convert(InputStream io) throws Exception {
		CityInfo cityInfo = new CityInfo();
		BufferedReader br = new BufferedReader(new InputStreamReader(io));
		String s1 = null;
		while ((s1 = br.readLine()) != null) {
			if (s1.startsWith("var")) {
				break;
			}
		}
		int s0Idx = s1.indexOf("s0.");
		s1 = s1.substring(s0Idx);
		s1 = s1.replaceAll("s0[.]", "");
		cityInfo = initBeanInfo(CityInfo.class, s1,';','=');
		return cityInfo;
	}
}
