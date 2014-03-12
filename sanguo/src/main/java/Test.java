import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class Test {
	public Test() {
	}

	final static ExecutorService exec = Executors.newFixedThreadPool(100);
	private static String sessionId = "ADD8EBD17CBDF79EEF93DD4EBF8FCAA9";
	public static void main(String[] args) throws Exception {
//		fight();
		String s1 ="s108.belongState=0;s108.blood=60;s108.bloodAdd=0;s108.bloodBuffAdd=0;s108.bookSkills=s251;s108.bufferBlood=60;s108.cityId=56;s108.createDate=new Date(1385637132000);s108.currentLineup=2;s108.experience=0;s108.fidelity=0;s108.fighting=false;s108.forceAdd=0;s108.forceBuffAdd=0;s108.id=622;s108.intelligenceAdd=0;s108.intelligenceBuffAdd=0;s108.killHeroNum=0;s108.kingId=1;s108.level=2;s108.locationAsInt=1;s108.magic=39;s108.magicAdd=0;s108.magicBuffAdd=0;s108.masterLineup=66;s108.masterWarriors=11;s108.playerHero=true;s108.plusExpCount=0;s108.sendItemFlag=0;s108.sendItemNum=0;s108.soldierNum=11;s108.soldierNumBufferAdd=0;s108.soldierType=8;s108.sourceId=118;s108.surrenderGrem=0;s108.surrenderNum=1;s108.type=1;s108.updateDate=new Date(1391851781000);";
		s1 = s1.replaceAll(String.format("%s[.]", "s108"), "");
		System.out.println(s1);
	
	}
	public static void fight() {
		int numberId=5;
		int batchId =33;
		getHoldInfo(numberId++,batchId++);
		for(int i=0;i < 1;i++)
		{
			msgIdFightBegin(numberId++,batchId++);
			msgIdRoundBegin(numberId++,batchId++);
			msgIdRoundEnd(numberId++,batchId++);
			msgIdBattleRetreat(numberId++,batchId++);
		}
	}
	public static void getHoldInfo(int numberId,int batchId) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid=" + sessionId + ";mid=" + sessionId);
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + numberId));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:18"));
		postMethod.addParameter(new NameValuePair("c0-e2", "string:msgTypeBattle"));
		postMethod.addParameter(new NameValuePair("c0-e3", "string:msgIdGetHoldInfo"));
		postMethod.addParameter(new NameValuePair("c0-e4", "number:1"));
		postMethod.addParameter(new NameValuePair("c0-param1", "Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", ""+batchId));

		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
			byte[] responseBody = postMethod.getResponseBody();
			System.out.println(new String(responseBody));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
	public static void msgIdFightBegin(int numberId,int batchId) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid=" + sessionId + ";mid=" + sessionId);
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + numberId));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:18"));
		postMethod.addParameter(new NameValuePair("c0-e2", "string:msgTypeBattle"));
		postMethod.addParameter(new NameValuePair("c0-e3", "string:msgIdFightBegin"));
		postMethod.addParameter(new NameValuePair("c0-e4", "string:1,413710&1137908,1137908"));
		postMethod.addParameter(new NameValuePair("c0-param1", "Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", ""+batchId));

		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
			byte[] responseBody = postMethod.getResponseBody();
			System.out.println(new String(responseBody));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}

	public static void msgIdRoundBegin(int numberId,int batchId) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid=" + sessionId + ";mid=" + sessionId);
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
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + numberId));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:18"));
		postMethod.addParameter(new NameValuePair("c0-e2", "string:msgTypeBattle"));
		postMethod.addParameter(new NameValuePair("c0-e3", "string:msgIdRoundBegin"));
		postMethod.addParameter(new NameValuePair("c0-e4", "413710,65"));
		postMethod.addParameter(new NameValuePair("c0-param1", "Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId", ""+batchId));
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
			byte[] responseBody = postMethod.getResponseBody();
			System.out.println(new String(responseBody));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}

	public static void msgIdRoundEnd(int numberId,int batchId) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid=" + sessionId + ";mid=" + sessionId);
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
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:"+ numberId));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:18"));
		postMethod.addParameter(new NameValuePair("c0-e2", "string:msgTypeBattle"));
		postMethod.addParameter(new NameValuePair("c0-e3", "string:msgIdRoundEnd"));
		postMethod.addParameter(new NameValuePair("c0-e4", "string:49;5-4,9-2|5;0"));
		postMethod.addParameter(new NameValuePair("c0-param1", "Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId",  ""+batchId));
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
			byte[] responseBody = postMethod.getResponseBody();
			System.out.println(new String(responseBody));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}

	public static void msgIdBattleRetreat(int numberId,int batchId) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("http://g8.ol.ko.cn:8080/hero/dwr/call/plaincall/DwrGameWorld.setMsg.dwr;jsessionid=" + sessionId + ";mid=" + sessionId);
		postMethod.addRequestHeader("Content-type", "application/octet-stream");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("User-Agent", "Dalvik/1.4.0 (Linux; U; Android 2.3.4; GT-I9100 Build/GRJ22)");
		postMethod.addRequestHeader("Host", "g8.ol.ko.cn:8080");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		postMethod.addParameter(new NameValuePair("callCount", "1"));
		postMethod.addParameter(new NameValuePair("page", ""));
		postMethod.addParameter(new NameValuePair("httpSessionId", sessionId));
		postMethod.addParameter(new NameValuePair("scriptSessionId", "51A0434AF2250025CA28BCB7B4E55E900"));
		postMethod.addParameter(new NameValuePair("c0-scriptName", "DwrGameWorld"));
		postMethod.addParameter(new NameValuePair("c0-methodName", "setMsg"));
		postMethod.addParameter(new NameValuePair("c0-id", "0"));
		postMethod.addParameter(new NameValuePair("c0-param0", "number:" + numberId));
		postMethod.addParameter(new NameValuePair("c0-e1", "number:18"));
		postMethod.addParameter(new NameValuePair("c0-e2", "string:msgTypeBattle"));
		postMethod.addParameter(new NameValuePair("c0-e3", "string:msgIdBattleRetreat"));
		postMethod.addParameter(new NameValuePair("c0-e4", "string:"));
		postMethod.addParameter(new NameValuePair("c0-param1", "Object_Object:{instanceId:reference:c0-e1, messageType:reference:c0-e2, messageId:reference:c0-e3, message:reference:c0-e4}"));
		postMethod.addParameter(new NameValuePair("batchId",  ""+batchId));
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
			byte[] responseBody = postMethod.getResponseBody();
			System.out.println(new String(responseBody));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
}
