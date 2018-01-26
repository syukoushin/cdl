package com.ibm.cdl.portal.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class DBPathUtils implements Serializable {

	public static final String DBPATH_MAP = "DBPATH_MAP";
	public static final String DBPATH_CACHE = "DBPATH_CACHE";
	
	private Map dbpathMap;
	private Map serverMap;
	
	public DBPathUtils() {
		dbpathMap = new HashMap();
		serverMap = new HashMap();
	}
	
	public String getServer(String userCode) {
		
		if (StringUtils.isEmpty(userCode) || !serverMap.containsKey(userCode)) return "";
		
		return serverMap.get(userCode).toString();
	}
	
	
	
//	public String getDbPath(String userCode) {
//		
//		String result = "";
//		
//		if (dbpathMap == null) dbpathMap = new HashMap();
//		
//		if (StringUtils.isEmpty(userCode)) return "";
//		
//		if (dbpathMap.containsKey(userCode)) return dbpathMap.get(userCode).toString();
//		
//		try {
//			
//			AuthenticatService authService = (AuthenticatService)SpringUtils.getObject("authService");
//			
//			User user = authService.getUserById(userCode);
//			
//			if (user == null) return "";
//			
//			String dbpath = user.getDbPath();
//			
//			dbpathMap.put(userCode, dbpath);
//			
//			serverMap.put(userCode, user.getMailServer());
//			
//			result = dbpath;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//			System.out.println("获取用户信息失败");
//		}
//		
//		System.out.println("userCode====" + userCode + ";;;;dbpath======" + result + ";;;;serverPath=====");
//		
//		return result;
//		
//	}
}
