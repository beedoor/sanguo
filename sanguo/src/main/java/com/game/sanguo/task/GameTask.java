package com.game.sanguo.task;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.sanguo.domain.UserBean;
import com.game.sanguo.util.GameUtil;

public abstract class GameTask implements Runnable {

	UserBean userBean = null;
	protected Logger logger = LoggerFactory.getLogger(GameTask.class);
	HttpClient httpClient = new HttpClient();

	public GameTask(){
		super();
	}

	protected void doRequest(PostMethod postMethod) {
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + postMethod.getStatusLine());
			}
			printResponseInfo(postMethod);
		} catch (Exception e) {
			logger.error("loginGame", e);
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}

	protected void sleep(int second) {
		try {
			TimeUnit.SECONDS.sleep(second);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void printResponseInfo(PostMethod postMethod) {
		NameValuePair methodName = postMethod.getParameter("c0-methodName");
		logger.debug("method " + methodName + " response info :");
		printHeader(postMethod);
		printBody(postMethod);
	}

	private void printBody(PostMethod postMethod) {
		try {
			logger.debug(GameUtil.parseUnicode(postMethod.getResponseBodyAsString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printHeader(PostMethod postMethod) {
		try {
			for (Header header : postMethod.getResponseHeaders()) {
				logger.debug(GameUtil.parseUnicode(header.toString()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getResponseContent(String responseContent) {
		if(responseContent == null || responseContent.equals(""))
		{
			return null;
		}
		int startIdx = responseContent.indexOf("{");
		int endIdx = responseContent.indexOf("}");
		String subStr = responseContent.substring(startIdx+1,endIdx);
		return subStr;
	}
	
	protected <T> T initBeanInfo(Class<T> classType,InputStream inputStream,String prex)
	{
		T classInstance = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String s1=null;
			while((s1 = br.readLine()) != null)
			{
				if(s1.startsWith("dwr"))
				{
					break;
				}
			}
			classInstance = initBeanInfo(classType,s1);
		} catch (IOException e) {
			logger.error("解析报文异常",e);
		}
		
		return classInstance;
	}
	
	protected <T> T initBeanInfo(Class<T> classType,String content) {
		T classInstance = null;
		try {
			content = getResponseContent(content);
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
				String filedValue = GameUtil.parseUnicode(fildInfoArray[1]);
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
	public static void main(String args[])
	{
		
	}
}