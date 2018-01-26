package com.ibm.cdl.common.util;

import org.apache.commons.httpclient.HttpClient;

import com.sun.jmx.snmp.Timestamp;

public class LoginHttpClient {
	private HttpClient httpClient = null;
	private Timestamp loginTime = null;
	private int loginStatus;
	private static final long LOGIN_TIME_OUT = 25*60*1000;
	
	public LoginHttpClient() {
		this.httpClient = new HttpClient();
		this.loginTime = new Timestamp();
	}
	
	public boolean isTimeOut(){
		boolean flag = false;
		Timestamp nowTime = new Timestamp();
		if(nowTime.getDateTime() - loginTime.getDateTime() >  LOGIN_TIME_OUT){
			flag = true;
		}
		return flag;
	}
	
	public HttpClient getHttpClient() {
		return httpClient;
	}
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public int getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}

}
