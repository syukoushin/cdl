package com.ibm.core.interceptor;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ibm.cdl.datamap.action.DataMapUtils;
import com.ibm.cdl.datamap.constants.Constants;
import com.opensymphony.xwork2.ActionInvocation;

public class CommonLogInterceptor extends LogInterceptor  {

	@Override
	public String getOptionName() {
		return getParamValue("methodId");
	}
	
	@Override
	public String getModuleName() {
		return getParamValue("appId");
	}
	
	@Override
	public String getUserCode() {
		return getParamValue("userCode");
	}
	
	@Override
	public String getFilter() {
		return DataMapUtils.getDataMapSub(Constants.SYS_PARAMS, Constants.SYS_PARAM_LOG_METHOD);
	}
	
	@Override
	public String getErrorMessage() {
		Map map = new HashMap();
		map.put("reqCode", "0");
		map.put("reqMessage", "业务系统处理失败，请稍后再试！ ");
		return JSONObject.fromObject(map).toString();
	}
}
