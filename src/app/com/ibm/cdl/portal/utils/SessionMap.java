package com.ibm.cdl.portal.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ibm.core.util.KeyUtils;

public class SessionMap implements Serializable {
	
	public static final String SESSION_MAP = "SESSION_MAP";
	public static final String SESSION_CACHE = "SessionCatch";

	private Map map;
	
	public SessionMap() {
		map = new HashMap();
	}
	
	public String addSession(String userName,String password) {
		
//		HashMap map = new HashMap();
//		map.put("username", userName);
//		map.put("password", password);
//		map.put("invoke","addsession");
//		
//		String post = "";
//		try {
//			
//			post = HttpRequestPoster.communicateNew(map, PropertiesUtils.singlton().getProperty("SESSION_URL"));
//			
//			System.out.println("这个是从服务器获取下来的数据了====" + post);
//			
//			JSONObject json = JSONObject.fromObject(post);
//			
//			return json.getString("sessionId");
//			
//		} catch (CommunicatorException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//			System.out.println("#####################获取session出错，这个问题很严重");
//		}
//		
//		return post;
//		
		
		String value = encrytValue(userName,password);
		
		map.put(userName+password, value);
		
		return value;
	}
	
	/**
	 * 判断是否含有权限，并修正时间
	 * @param sessionid
	 * @return
	 */
	public boolean access(String sessionId,String userCode) {

//		HashMap map = new HashMap();
//		map.put("sessionid", sessionId);
//		map.put("invoke", "access");
//		
//		String post = "";
//		try {
//			
//			post = HttpRequestPoster.communicateNew(map, PropertiesUtils.singlton().getProperty("SESSION_URL"));
//			
//			System.out.println("这个是从服务器获取下来的数据了====" + post);
//			
//			JSONObject json = JSONObject.fromObject(post);
//			
//			String responseCode = json.getString("responseCode");
//			
//			if ("1".equals(responseCode)) {
//				
//				if ("true".equals(json.getString("result"))) {
//					return true;
//				}
//			} else {
//				return false;
//			}
//			
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//			System.out.println("验证session失败，这个是不可能出现的");
//		}
//		
//		return false;
		
		String s = decryptValue(sessionId);
		
		if (StringUtils.isEmpty(s) || s.indexOf(";") == -1) return false;
		
		String[] values = s.split(";");
		
		//如果不存在
//		if (!map.containsKey(values[0])) return false;
		if (StringUtils.isEmpty(userCode)) return false;
		if (!values[0].startsWith(userCode)) return false;
		
//		String timeOut = PropertiesUtils.singlton().getProperty("SESSION_TIME_OUT");
//		String timeInter = PropertiesUtils.singlton().getProperty("SESSION_TIME_INTER");
//		
//		
//		String lastSessionTime = decryptValue(map.get(values[0]).toString()).split(";")[1];
//		
//		
//		//判断时候超时,超过2分钟
//		if (System.currentTimeMillis() - Long.valueOf(lastSessionTime) >= 1000*60*Integer.parseInt(timeOut)) {
//
//			System.out.println("判断用户超时===" + values[0] + ";时间＝＝＝" + values[1]);
//			return false;
//		} else {
//			
//			if (System.currentTimeMillis() - Long.valueOf(lastSessionTime) > 1000*60*Integer.parseInt(timeInter)) {
//				//该段时间操作了，对操作时间重新赋值
//				map.put(values[0], encrytValue(values[0]));
//				
//				System.out.println("重新赋值对用户：" + values[0] + "=======" + map.get(values[0]));
//			} 
//		}
		
		return true;
	}
	
	
	public String encrytKey(String userName,String password) {
		
		return KeyUtils.encryptHex(userName+"=="+password);
	}
	
	public String encrytValue(String userName,String password) {
		
		return KeyUtils.encryptHex(userName+password+";"+System.currentTimeMillis());
	}
	
	public String encrytValue(String userNamePassword) {
		return KeyUtils.encryptHex(userNamePassword+";"+System.currentTimeMillis());
	}
	
	public String decryptValue(String sessionId) {
		
		return KeyUtils.decryptHex(sessionId);
	}
	
}
