package com.game.sanguo.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.game.sanguo.domain.UserBean;

public class ProfileConfigUtil {
//	static{
//		loadUserConfigInfo();
//		URL yurl = ProfileConfigUtil.class.getClassLoader().getResource("config");
////		InputStream ins = ProfileConfigUtil.class.getClassLoader().getResourceAsStream("config/resource.properties");
////		Properties prop = new Properties();
////		try {
////			prop.load(ins);
////			System.out.println(prop.getProperty("yuanbao"));
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		prop.load(GetWordCityInfoTask.class.getClassLoader().getResourceAsStream("resource.properties"));
//	}
	public String getStringConfig(String fileName,String key)
	{
		
		return "";
	}
	private  void loadUserConfigInfo(File file) {
		try {
			Digester dig = new Digester();
			addRule(dig);
			dig.push(this);
			dig.setValidating(false);
			dig.parse(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void addRule(Digester digester) {
		digester.addObjectCreate("configuration/users/user", UserBean.class);// 创建节点的实例
		digester.addCallMethod("configuration/users/user/userName", "setUserName");
		digester.addSetNext("configuration/users/user", "addItem");
	}
	
	public static void main(String [] args)
	{
		
	}
}
