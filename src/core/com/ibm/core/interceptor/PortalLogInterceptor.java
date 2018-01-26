package com.ibm.core.interceptor;


/**
 * paramSet    表示要带有包含的参数，值的要做处理,默认做处理
 * resultKey   表示返回的结果值的key，如果为空，则不做处理
 */
public class PortalLogInterceptor extends LogInterceptor {

	@Override
	public String getOptionName() {
		
		String invoke = getParamValue("invoke");
		if ("login".equals(invoke)) {
			return "登录";
		} else if ("saveAppLogin".equals(invoke)) {
			String appName = getParamValue("appName");
			return appName + "登录";
		} else if ("getPortalInfo".equals(invoke)){
			return "登录";
		}
		return "";
	}
	
	@Override
	public String getModuleName() {
		String invoke = getParamValue("invoke");
		if ("login".equals(invoke)) {
			return "MOA平台";
		} else if ("saveAppLogin".equals(invoke)) {
			String appName = getParamValue("appName");
			return appName;
		} else if ("getPortalInfo".equals(invoke)){
			return "MOA平台";
		}
		return "";
	}
	
	@Override
	public String getUserCode() {
		return getParamValue("userCode");
	}

}
