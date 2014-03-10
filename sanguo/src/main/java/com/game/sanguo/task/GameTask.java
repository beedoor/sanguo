package com.game.sanguo.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.sanguo.domain.Pair;
import com.game.sanguo.domain.UserBean;
import com.game.sanguo.util.GameUtil;
import com.game.sanguo.util.LoginGameInfo;

public abstract class GameTask implements Runnable {

	UserBean userBean = null;
	protected Logger logger = LoggerFactory.getLogger(GameTask.class);
	HttpClient httpClient = new HttpClient();

	protected static Lock loginLock = new ReentrantReadWriteLock().writeLock();

	public GameTask() {
		super();
	}

	public void run() {
		boolean lockFlag = false;
		try {
			lockFlag = loginLock.tryLock(10, TimeUnit.SECONDS);
			if (lockFlag) {
				doAction();
			} else {
				logger.info("暂时没有登录，无法执行该请求，等待登录");
			}
		} catch (Exception e) {
			logger.error("任务异常", e);
		} finally {
			if (lockFlag) {
				loginLock.unlock();
			}
		}
	}

	protected abstract void doAction();

	protected void doRequest(PostMethod postMethod) {
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + postMethod.getStatusLine());
			}
			printResponseInfo(postMethod);
		} catch (Throwable e) {
			logger.error(postMethod.getPath(), e);
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}

	protected void sleep(long unit, TimeUnit tu) {
		try {
			tu.sleep(unit);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void sleep(int unit) {
		sleep(unit, TimeUnit.SECONDS);
	}

	private String getParamValue(PostMethod method, String key) {
		NameValuePair nv = method.getParameter(key);
		if (nv == null) {
			return "";
		}
		return nv.getValue();
	}

	protected void printResponseInfo(PostMethod postMethod) throws Exception {
		logger.debug(String.format("response for 【%s】 【%s】【%s】 【%s】【%s】 【%s】【%s】  ", getParamValue(postMethod, "c0-scriptName"), getParamValue(postMethod, "c0-methodName"),
				getParamValue(postMethod, "c0-e2"), getParamValue(postMethod, "c0-e3"), userBean.getSessionId(), userBean.getNumberIdNoIncrement(), userBean.getBatchIdNoIncrement()));
		logger.debug(postMethod.getPath());
		logger.debug(postMethod.getQueryString());
		printHeader(postMethod);
		printBody(postMethod);
		logger.debug(String.format("response end "));
	}

	private void printBody(PostMethod postMethod) throws Exception {
		String responseStr = GameUtil.parseUnicode(postMethod.getResponseBodyAsString());
		logger.debug(responseStr);
		if (responseStr.indexOf("java.lang") != -1) {
			// 会话失效，重新登录吧
			new LoginTask(userBean, 5, TimeUnit.SECONDS).doAction();
		}
	}

	private void printHeader(PostMethod postMethod) throws Exception {
		for (Header header : postMethod.getResponseHeaders()) {
			logger.debug(GameUtil.parseUnicode(header.toString()));
		}
	}

	private static String getResponseContent(String responseContent) {
		if (responseContent == null || responseContent.equals("")) {
			return null;
		}
		int startIdx = responseContent.indexOf("{");
		int endIdx = responseContent.lastIndexOf("}");
		String subStr = responseContent.substring(startIdx + 1, endIdx);
		return subStr;
	}

	protected <T> T initBeanInfo(Class<T> classType, InputStream inputStream, String prex) {
		T classInstance = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String s1 = null;
			while ((s1 = br.readLine()) != null) {
				if (s1.startsWith(prex)) {
					break;
				}
			}
			classInstance = initBeanInfo(classType, s1);
		} catch (IOException e) {
			logger.error("解析报文异常", e);
		}

		return classInstance;
	}

	protected static <T> T initBeanInfo(Class<T> classType, String content) {
		T classInstance = null;
		try {
			content = getResponseContent(content);
			if (null == content || content.equals("")) {
				return null;
			}
			classInstance = classType.newInstance();
			List<Pair<String, String>> filedInfoArray = split(content, ',', ':');
			for (Pair<String, String> filedInfo : filedInfoArray) {
				String methodName = String.format("set%s%s", filedInfo.first.substring(0, 1).toUpperCase(), filedInfo.first.substring(1));
				Method m = getMethod(classType, methodName, new Class[] { String.class });
				if (m != null) {
					if (!m.isAccessible()) {
						m.setAccessible(true);
					}
					m.invoke(classInstance, filedInfo.second);
				}
			}
		} catch (Exception e) {
			// logger.error("创建bean对象异常", e);
		}
		return classInstance;
	}

	private static <T> Method getMethod(Class<T> classType, String methodName, Class<?>... paramTypes) {
		try {
			Method m = classType.getDeclaredMethod(methodName, new Class[] { String.class });
			return m;
		} catch (Throwable e) {
			// TODO: handle exception
		}
		return null;
	}

	private static List<Pair<String, String>> split(String s, char wordSplit, char valueSplit) {
		if(s.charAt(s.length()-1) != wordSplit)
		{
			s += String.valueOf(wordSplit);
		}
		List<Pair<String, String>> splitResult = new ArrayList<Pair<String, String>>();
		StringBuilder sBuffer = new StringBuilder();
		String key = null, value = null;
		int endC = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == valueSplit && endC == 0) {
				key = sBuffer.toString();
				sBuffer.setLength(0);
			} else if (c == wordSplit && endC == 0) {
				value = GameUtil.parseUnicode(sBuffer.toString());
				if (value.startsWith("\"") && value.endsWith("\"")) {
					value = value.substring(1, value.length() - 1);
				}
				sBuffer.setLength(0);
				splitResult.add(Pair.makePair(key, value));
				key = null;
				value = null;
			} else if (c == '{') {
				endC--;
				sBuffer.append(c);
			} else if (c == '}' && endC < 0) {
				endC++;
				sBuffer.append(c);
			} else {
				sBuffer.append(c);
			}
		}

		return splitResult;
	}

	public static void main(String args[]) {
		try {
			String s1 = "dwr.engine._remoteHandleCallback('4','0',{autoFlag:false,captureHeros:s0,challengeTimes:8,changeNameTimes:1,computerCitys:s1,computerHeros:s2,continuousLoginDays:14,continuousLoginRewardRecv:-1,errCode:0,errMsg:\"successful\",gem:2175,gold:1568360,goldLimitId:1,guideProgress:\"{\"version\": 6,\"guideVisitSet\": \"C21056009C0001724F108E308520CB2002304830A37044701172415087502172C1C04A602C608570D5708B60C6703172438041720D705172617208C08980CF804EC084D0CAD001E047E08DE0\"}\",head:4,heroBoardData:s3,id:65,lastChapterIsReward:true,lastForceId:9,leagueId:0,maxMedicalCampStorage:13600,medicalCampStorage:13600,monthCardRewardYB:0,name:\"诺\",occupyPvpMainCityId:19338,option:s4,permitLoginTime:null,playerCitys:s5,playerHeros:s6,playerItems:s7,preChapterIsReward:true,prepareSoldierNum:5867,pvpMainCityOccupyHead:7,pvpMainCityOccupyName:\"喻雪\",pvpScore:0,resultCode:0,selectedHeroPresent:385,sendGold:0,sessionId:\"1331FCFA30CD4C20B772E0D4945EB70E\",sex:1,skipFightTimes:0,taskTriggers:s8,tasks:\"\",totalRecharge:69600,unreadLetterCount:32,vipItem:s9,vipLv:5,worldInfo:s10});";
			LoginGameInfo beanInfo = initBeanInfo(LoginGameInfo.class, s1);
			System.out.println(beanInfo.toString());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}