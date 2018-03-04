package com.ibm.cdl.common.util;

import java.util.HashMap;
import java.util.Map;

public class LoginCallerFactory {

	private static LoginCallerFactory factory;
	
	private Map<String,LoginHttpClient> map = new HashMap<String,LoginHttpClient>();
	
	private LoginCallerFactory(){
	}
	
	public static LoginCallerFactory singlon(){
		
		if (factory == null) {
			
			synchronized (LoginCallerFactory.class) {
				
				if (factory == null) {
					
					factory = new LoginCallerFactory();
				}
				
			}
		}
		
		return factory;
		
	}
	
	
	public LoginHttpClient createLoginCaller(String userCode) throws Exception {
		
		if (!map.containsKey(userCode)) {
			map.put(userCode, new LoginHttpClient());
		}else if(map.get(userCode).isTimeOut()){
			map.remove(userCode);
			map.put(userCode, new LoginHttpClient());
		}
		return map.get(userCode);
		
	}
	
	public void removeAll() {
		
		System.out.println("##############################");
		System.out.println(map);
		map.clear();
	}
}
