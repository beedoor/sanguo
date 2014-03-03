package com.game.sanguo.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
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

	public void run() {
		try {
			ScanResource scan = userBean.getConfigure().getScanResource();
			List<Pair<String,List<CityInfo>>> createDataList = new ArrayList<Pair<String,List<CityInfo>>>();
			if (scan.getTreasure() == 1) {
				logger.info("开始计算元宝山资源信息");
				List<CityInfo> resultList = getResourceDetailInfo(resourceConfig.getTreasureAreaIdList());
				createDataList.add(Pair.makePair("元宝山",resultList));
			} else {
				logger.info("配置禁止扫描元宝山资源");
			}
			if (scan.getMarket() == 1) {
				logger.info("开始计算黑市资源信息");
				List<CityInfo> resultList = getResourceDetailInfo(resourceConfig.getMarketAreaIdList());
				createDataList.add(Pair.makePair("黑市",resultList));
			} else {
				logger.info("配置禁止扫描黑市资源");
			}
			if (scan.getGold() == 1) {
				logger.info("开始计算金矿资源信息");
				List<CityInfo> resultList = getResourceDetailInfo(resourceConfig.getGoldAreaIdList());
				createDataList.add(Pair.makePair("金矿",resultList));
			} else {
				logger.info("配置禁止扫描金矿资源");
			}
			
			XlsFileUtil.createSearchResultFile(createDataList,userBean.getConfigure().getScanResource().getSortType());
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
					if(null != ciInfo)
					{
						cityInfoList.add(ciInfo);
					}
					sleep(userBean.getConfigure().getScanResource().getWaitTime(),TimeUnit.MILLISECONDS);
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
			logger.debug(cityInfo.toString());
			processData(cityInfo);
		} catch (Exception e) {
			logger.error("转换异常", e);
		}
		
		return cityInfo;
	}

	private void processData(CityInfo cityInfo) {
		if (cityInfo.getOccupierId().equals("0") || cityInfo.getOccupierId() == null || cityInfo.getOccupierId().equals("null") || cityInfo.getOccupierId().equals("")) {
			logger.info(cityInfo.getId() + " 类别" + decodeResourceType(cityInfo.getTypeAsInt()) + "资源为空");
			return;
		}
		Date occupyTime = new Date(Long.parseLong(cityInfo.getOccupyTime()));
		int level = Integer.parseInt(cityInfo.getOccupierVipLv());
		Date currTime = new Date(System.currentTimeMillis());
		long rage = 0;
		if (level == 9) {
			rage = 24 * 60 * 60 * 1000 * 3;
		} else if (level == 8) {
			rage = 24 * 60 * 60 * 1000 * 2;
		} else {
			rage = 24 * 60 * 60 * 1000;
		}
		long relayTime = rage - (currTime.getTime() - occupyTime.getTime());
		long realayHour = relayTime / (60 * 60 * 1000);
		long realayMins = (relayTime % (60 * 60 * 1000)) / (60 * 1000);
		long realaySec = (relayTime % (60 * 1000)) / (1000);

		logger.info(String.format("坐标[%s]，占领者[%s]，占领者VIP级别[%s] ,资源类别[%s],占领时间[%s],剩余时间[%s:%s:%s]", cityInfo.getId(), GameUtil.parseUnicode(cityInfo.getOccupierName()), cityInfo.getOccupierVipLv(),
				decodeResourceType(cityInfo.getTypeAsInt()), GameUtil.parseDate(new Date(Long.parseLong(cityInfo.getOccupyTime()))), realayHour, realayMins, realaySec));
	}

	private Object decodeResourceType(String typeAsInt) {

		if (typeAsInt.equals("6")) {
			return "点将";
		} else if (typeAsInt.equals("5")) {
			return "兵营";
		} else if (typeAsInt.equals("4")) {
			return "黑市";
		} else if (typeAsInt.equals("3")) {
			return "元宝山";
		} else if (typeAsInt.equals("2")) {
			return "金矿";
		}
		return "";
	}

	private CityInfo convert(InputStream io) throws Exception {
		CityInfo cityInfo = new CityInfo();
		BufferedReader br = new BufferedReader(new InputStreamReader(io));
		String s1 = null;
		while ((s1 = br.readLine()) != null) {
			if (s1.startsWith("var")) {
				break;
			}
		}
		String[] s1Array = s1.split(";");
		for (String s : s1Array) {
			String[] stypeArray = s.split("=");
			if (stypeArray.length != 2) {
				continue;
			}
			String value = stypeArray[1];
			if (value.startsWith("new Date")) {
				// 日期格式
				value = value.substring(value.indexOf("(") + 1, value.indexOf(")"));
			}
			if(value.startsWith("\"") && value.endsWith("\""))
			{
				value = value.substring(1,value.length()-1);
			}
			String name = stypeArray[0];
			String[] nameSplit = name.split("[.]");
			if (nameSplit.length != 2) {
				continue;
			}
			String filedName = nameSplit[1];
			try {
				Field [] fileds= CityInfo.class.getDeclaredFields();
				for(Field f:fileds)
				{
					if(f.getName().equals(filedName))
					{
						f.setAccessible(true);
						f.set(cityInfo, value);
						break;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cityInfo;
	}
}
