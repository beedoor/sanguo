import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.sanguo.domain.CityInfo;
import com.game.sanguo.domain.GameAreaInfo;
import com.game.sanguo.domain.GoldSearchInfo;



public class Test {

	public Test() {
	}
	final static ExecutorService exec = Executors.newFixedThreadPool(100);
	private static String sessionId = "CEAFA393AD302F3750D5B26B420C695D";

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(Test.class);

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		//游戏服务器了
		String s1 = "s5.id=3;s5.name=\"官渡\";s5.recommend=0;s5.statusAsInt=1;s5.url=\"gh2.ol.ko.cn:8080\";";
		int prex = s1.indexOf(".");
		String areaIdStr = s1.substring(0,prex);
		logger.info(areaIdStr);
		//进行替换咯
		s1 = s1.replaceAll(areaIdStr+"[.]", "");
		System.out.println(s1);
		s1 = s1.replaceAll("[:]", "#");
		s1 = s1.replaceAll("[=]", ":");
		System.out.println(s1);
		s1 = s1.replaceAll("[;]", ",");
		System.out.println(s1);
		GameAreaInfo gameArea = initBeanInfo(GameAreaInfo.class, s1);
		String url = gameArea.getUrl().replaceAll("#", ":");
		gameArea.setUrl(url);
		System.out.println(gameArea);
//		Properties prop = new Properties();
//		prop.load(Test.class.getClassLoader().getResourceAsStream("resource.properties"));
//		logger.info(prop.getProperty("yuanbao"));
//		doJx();
//		String responseStr = "dwr.engine._remoteHandleCallback('1','0',{checkId:\"2014-02-26 17:23:19\",desktopType:\"game\",desktopUrl:null,desktopVersion:0,disuseEndTime:null,logOnServers:s0,name:\"1439814\",resultCode:0,serverId:\"8\",sex:1,type:0,userId:1439814});";
//		if(responseStr.startsWith("dwr.engine._remoteHandleCallback"))
//		{
//			int startIdx = responseStr.indexOf("{");
//			int endIdx = responseStr.indexOf("}");
//			String subStr = responseStr.substring(startIdx,endIdx);
//			String checkIdStr = subStr.split("[,]")[0];
//			System.out.println(checkIdStr.substring(checkIdStr.indexOf("\"")+1,checkIdStr.lastIndexOf("\"")));
//		}
//		logger.info("sadfsafd");
//		logger.info("s3.badge=0;s3.cityName=\"\u5E7B\u5F71\u795E\u541B\";s3.citySrc=8;s3.color=0;s3.heroCount=1;s3.id=20376;s3.leagueId=0;s3.lv=1;s3.occupierHeroCount=0;s3.occupierId=0;s3.occupierName=null;s3.occupyTime=null;s3.playerId=11864;s3.protectMsLeft=0;s3.typeAsInt=1;s3.unionName=\"\";");
////		
//		Date d = new Date(1393319460488L);
//		System.out.println(d);
//		int numberId=0;
//		int batchId = 18;
//		getTimeZone(batchId,numberId, 32,32);
//		for(int i=0;i < 34;i++)
//		{
//			for(int j=0;j<34;j++)
//			{
//				numberId++;
//				batchId++;
//				getTimeZone(batchId,numberId, i, j);
//				try {
//					TimeUnit.SECONDS.sleep(2);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//		}
//		Header h = new Header("Set-Cookie","JSESSIONID=7D11F3545ABF62AAB32F53BCA85509F5; Path=/hero/; HttpOnly");
//		System.out.println(Arrays.toString(h.getElements()));
	}
	protected  static <T> T initBeanInfo(Class<T> classType,String content) {
		T classInstance = null;
		try {
			if(null == content || content.equals(""))
			{
				return null;
			}
			classInstance = classType.newInstance();
			String[] filedInfoArray = content.split(",");
			for(String filedInfo:filedInfoArray)
			{
				String []fildInfoArray = filedInfo.split(":");
				if(fildInfoArray.length != 2)
				{
					continue;
				}
				String filedName = fildInfoArray[0];
				String filedValue = fildInfoArray[1];
				Field classFiled = classType.getDeclaredField(filedName);
				if(classFiled != null)
				{
					classFiled.setAccessible(true);
					if(classFiled.getType() == Date.class)
					{
						if(filedValue.indexOf("(") == -1)
						{
							continue;
						}
						filedValue = filedValue.substring(filedValue.indexOf("(")+1,filedValue.indexOf(")"));
						classFiled.set(classInstance, new Date(Long.parseLong(filedValue)));
					}else if(classFiled.getType() == Long.class)
					{
						classFiled.set(classInstance, Long.parseLong(filedValue));
					}else 
					{
						if(filedValue.startsWith("\"") && filedValue.endsWith("\""))
						{
							filedValue =filedValue.substring(1,filedValue.length()-1);
						}
						classFiled.set(classInstance, filedValue);
					}
				}
			}
		} catch (Exception e) {
			logger.error("创建bean对象异常",e);
		}
		return classInstance;
	}
	public static void doJx()
	{
		String s1 = "var s0={};s0.areaId=1;s0.badge=0;s0.cityName=null;s0.citySrc=1;s0.color=0;s0.heroCount=3;s0.id=18541;s0.leagueId=0;s0.lv=0;s0.maxHeroCount=5;s0.occupierHeroCount=3;s0.occupierId=57610;s0.occupierName=\"丑哥78\";s0.occupierVipLv=8;s0.occupyTime=new Date(1393402580000);s0.playerId=0;s0.protectMsLeft=0;s0.statusAsInt=5;s0.typeAsInt=4;s0.unionName=\"\";s0.zoneId=1484;";
		CityInfo cityInfo = new CityInfo();
		String[] s1Array = s1.split(";");
		for(String s:s1Array)
		{
			String[] stypeArray = s.split("=");
			if(stypeArray.length != 2)
			{
				continue;
			}
			String value = stypeArray[1];
			if(value.startsWith("new Date"))
			{
				//日期格式
				value = value.substring(value.indexOf("(")+1,value.indexOf(")"));
			}
			String name = stypeArray[0];
			String [] nameSplit = name.split("[.]");
			if(nameSplit.length != 2)
			{
				continue;
			}
			String filedName = nameSplit[1];
			try {
				Field f = CityInfo.class.getDeclaredField(filedName);
				if(f != null)
				{
					f.set(cityInfo, value);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		processData(cityInfo);
	}
	private static void processData(CityInfo cityInfo) {
		Date occupyTime = new Date(Long.parseLong(cityInfo.getOccupyTime()));
		int level = Integer.parseInt(cityInfo.getOccupierVipLv());
		Date currTime = new Date(System.currentTimeMillis());
		long rage = 0;
		if(level == 9)
		{
			rage = 24 * 60*60*1000 *3;
		}else if(level == 8)
		{
			rage = 24 * 60*60*1000 *2;
		}else
		{
			rage = 24 * 60*60*1000;
		}
		long relayTime = rage - (currTime.getTime() - occupyTime.getTime());
		long realayHour = relayTime/(60*60*1000);
		long realayMins = (relayTime%(60*60*1000))/(60*1000);
		long realaySec = (relayTime%(60*1000))/(1000);
		
		System.out.println(realayHour+":"+realayMins+":"+realaySec);
	}
	public static void getTimeZone(int batchId,int numberId,int area,int areaH) {
		// TODO Auto-generated method stub
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		PostMethod postMethod = new PostMethod(
				"http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid="
						+ sessionId + ";mid=" + sessionId);
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent",
				"Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		// 使用系统提供的默认的恢复策略
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId","51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName","DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:"+numberId));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:0"));
		postMethod.addParameter(new NameValuePair("c0-e2","string:msgTypeWorld"));
		postMethod.addParameter(new NameValuePair("c0-e3","string:msgIdGetZoneInfo"));
		postMethod.addParameter(new NameValuePair("c0-e4","string:"+(area+","+areaH)));
		postMethod.addParameter(new NameValuePair("c0-param1","Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", ""+batchId));

		try {
			logger.info(String.format("wait for [%s,%s]",area,areaH) );
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ postMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = postMethod.getResponseBody();
			// 处理内容
			logger.info(new String(responseBody));
		} catch (Exception e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}

	public static void fight() {
		// int j=11;
		// for(int i=0;i<10;i++)
		// {
		// // getBattle(i++);
		// msgIdFightBegin(j++);
		// msgIdRoundBegin(j++);
		// msgIdRoundEnd(j++);
		// msgIdBattleRetreat(j++);
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// // getChat(i);
		// }
	}

	public static void doConsume(final CountDownLatch cdl,final int i,final int numberId,final int batchId,int sleepTime) {
		// TODO Auto-generated method stub
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		PostMethod postMethod = new PostMethod(
				"http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid="
						+ sessionId + ";mid=" + sessionId);
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent",
				"Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		// 使用系统提供的默认的恢复策略
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId","51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName","DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:"+numberId));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:0"));
		postMethod.addParameter(new NameValuePair("c0-e2","string:msgTypeItem"));
		postMethod.addParameter(new NameValuePair("c0-e3","string:msgIdGetVipItem"));
		postMethod.addParameter(new NameValuePair("c0-e4","string:1"));
		postMethod.addParameter(new NameValuePair("c0-param1","Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", ""+batchId));

		try {
			cdl.await();
//			TimeUnit.MILLISECONDS.sleep(sleepTime);
			System.out.println("wait for do");
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ postMethod.getStatusLine());
			}
			System.out.println("do success");
			// 读取内容
			byte[] responseBody = postMethod.getResponseBody();
			// 处理内容
			System.out.println(new String(responseBody));
		} catch (Exception e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}
//	public static void doConsume(final CountDownLatch cdl,final int i,final int numberId,final int batchId,int sleepTime) {
//		// TODO Auto-generated method stub
//		// 构造HttpClient的实例
//		HttpClient httpClient = new HttpClient();
//		// 创建GET方法的实例
//		PostMethod postMethod = new PostMethod(
//				"http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid="
//						+ sessionId + ";mid=" + sessionId);
//		postMethod.addRequestHeader("Content-type", "application/octet-stream");
//		postMethod.addRequestHeader("Cache-Control", "no-cache");
//		postMethod.addRequestHeader("Pragma", "no-cache");
//		postMethod.addRequestHeader("Accept-Encoding", "gzip");
//		postMethod.addRequestHeader("User-Agent",
//				"Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
//		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
//		postMethod.addRequestHeader("Connection", "Keep-Alive");
//		// 使用系统提供的默认的恢复策略
//		postMethod.addParameter(new NameValuePair("callCount", "1"));
//		postMethod.addParameter(new NameValuePair("page", ""));
//		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
//		postMethod.addParameter(new NameValuePair("scriptSessionId",
//				"51A0434AF2250025CA28BCB7B4E55E900"));
//		postMethod.addParameter(new NameValuePair("c0-scriptName",
//				"DwrGameWorld"));
//		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
//		postMethod.addParameter(new NameValuePair("c0-id", "0"));
//		postMethod.addParameter(new NameValuePair("c0-param0", "number:"+numberId));
//		postMethod.addParameter(new NameValuePair("c0-e1", "number:3880264"));
//		postMethod.addParameter(new NameValuePair("c0-e2",
//				"string:msgTypeItem"));
//		postMethod.addParameter(new NameValuePair("c0-e3",
//				"string:msgIdItemConsume"));
//		postMethod.addParameter(new NameValuePair("c0-e4",
//				"string:-1"));
//		postMethod
//				.addParameter(new NameValuePair(
//						"c0-param1",
//						"Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
//		postMethod.addParameter(new NameValuePair("batchId", ""+batchId));
//
//		try {
//			cdl.await();
////			TimeUnit.MILLISECONDS.sleep(sleepTime);
//			System.out.println("wait for do");
//			int statusCode = httpClient.executeMethod(postMethod);
//			if (statusCode != HttpStatus.SC_OK) {
//				System.err.println("Method failed: "
//						+ postMethod.getStatusLine());
//			}
//			System.out.println("do success");
//			// 读取内容
//			byte[] responseBody = postMethod.getResponseBody();
//			// 处理内容
//			System.out.println(new String(responseBody));
//		} catch (Exception e) {
//			// 发生网络异常
//			e.printStackTrace();
//		} finally {
//			// 释放连接
//			postMethod.releaseConnection();
//		}
//	}

	public static void msgIdFightBegin(int i) {
		// TODO Auto-generated method stub
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		PostMethod postMethod = new PostMethod(
				"http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid="
						+ sessionId + ";mid=" + sessionId);
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent",
				"Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		// 使用系统提供的默认的恢复策略
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId",
				"51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName",
				"DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + i));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:17"));
		postMethod.addParameter(new NameValuePair("c0-e2",
				"string:msgTypeBattle"));
		postMethod.addParameter(new NameValuePair("c0-e3",
				"string:msgIdFightBegin"));
		postMethod.addParameter(new NameValuePair("c0-e4",
				"string:3%2c1663589%261507410%2c1507410"));
		postMethod
				.addParameter(new NameValuePair(
						"c0-param1",
						"Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", "62"));

		// postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
		// new DefaultHttpMethodRetryHandler());
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ postMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = postMethod.getResponseBody();
			// 处理内容
			System.out.println(new String(responseBody));
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}

	public static void msgIdRoundBegin(int i) {
		// TODO Auto-generated method stub
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		PostMethod postMethod = new PostMethod(
				"http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid="
						+ sessionId + ";mid=" + sessionId);
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent",
				"Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		// 使用系统提供的默认的恢复策略
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId",
				"51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName",
				"DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + i));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:17"));
		postMethod.addParameter(new NameValuePair("c0-e2",
				"string:msgTypeBattle"));
		postMethod.addParameter(new NameValuePair("c0-e3",
				"string:msgIdRoundBegin"));
		postMethod.addParameter(new NameValuePair("c0-e4", "1663589%2c65"));
		postMethod
				.addParameter(new NameValuePair(
						"c0-param1",
						"Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", "67"));

		// postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
		// new DefaultHttpMethodRetryHandler());
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ postMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = postMethod.getResponseBody();
			// 处理内容
			System.out.println(new String(responseBody));
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}

	public static void msgIdRoundEnd(int i) {
		// TODO Auto-generated method stub
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		PostMethod postMethod = new PostMethod(
				"http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid="
						+ sessionId + ";mid=" + sessionId);
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent",
				"Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		// 使用系统提供的默认的恢复策略
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId",
				"51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName",
				"DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + i));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:17"));
		postMethod.addParameter(new NameValuePair("c0-e2",
				"string:msgTypeBattle"));
		postMethod.addParameter(new NameValuePair("c0-e3",
				"string:msgIdRoundEnd"));
		postMethod.addParameter(new NameValuePair("c0-e4",
				"string:180%3b7-2%2c2d-6%3a58%2c63-6%3a58%2c91-6%3a58%3b0"));
		postMethod
				.addParameter(new NameValuePair(
						"c0-param1",
						"Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", "77"));

		// postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
		// new DefaultHttpMethodRetryHandler());
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ postMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = postMethod.getResponseBody();
			// 处理内容
			System.out.println(new String(responseBody));
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}

	public static void msgIdBattleRetreat(int i) {
		// TODO Auto-generated method stub
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		PostMethod postMethod = new PostMethod(
				"http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid="
						+ sessionId + ";mid=" + sessionId);
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent",
				"Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		// 使用系统提供的默认的恢复策略
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId",
				"51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName",
				"DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + i));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:18"));
		postMethod.addParameter(new NameValuePair("c0-e2",
				"string:msgTypeBattle"));
		postMethod.addParameter(new NameValuePair("c0-e3",
				"string:msgIdBattleRetreat"));
		postMethod.addParameter(new NameValuePair("c0-e4", "string:"));
		postMethod
				.addParameter(new NameValuePair(
						"c0-param1",
						"Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", "78"));

		// postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
		// new DefaultHttpMethodRetryHandler());
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ postMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = postMethod.getResponseBody();
			// 处理内容
			System.out.println(new String(responseBody));
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}
}
