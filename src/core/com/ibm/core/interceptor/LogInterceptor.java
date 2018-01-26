package com.ibm.core.interceptor;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.core.log.pojo.Log;
import com.ibm.core.log.service.LogService;
import com.ibm.core.spring.SpringContextHolder;
import com.opensymphony.xwork2.ActionInvocation;

import net.sf.json.JSONObject;

public class LogInterceptor extends DefaultInterceptor {

	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public String doParamIntercept(ActionInvocation arg0) throws Exception {
		String result =  arg0.invoke();
		return result;
	}
	
	
	public String doException(Exception e) {
		//e.printStackTrace();
		String errorLog = ExceptionUtils.getFullStackTrace(e);
		getLogService().saveLog(new Log(getUserCode(),getMethod(),getRequestParam(),errorLog,Log.FAIL,""));
		ServletActionContext.getRequest().setAttribute("clientErrorMsg", getErrorMessage());
		return "clientError";
	}
	
	public String getOptionName() {
		return "";
	}
	
	public String getModuleName() {
		return "";
	}
	
	public String getUserCode() {
		return getParamValue("userCode");
	}
	
	public String getMethod(){
		return getParamValue("invoke");
	}
	
	public String getErrorMessage() {
		Map map = new HashMap();
		map.put("reqCode", "0");
		map.put("reqMessage", "请求失败，请稍后再试！");
		return JSONObject.fromObject(map).toString();
	}
	
	public String getRequestParam() {
		String result = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = enu.nextElement();
			String value = request.getParameter(name);
			if (StringUtils.isNotEmpty(value)) {
				result += ";" + name + "=" + value;
			}
		}
		if (StringUtils.isNotEmpty(result)) {
			result = result.substring(1);
		}
		return result;
	}
	
	public LogService getLogService() {
		return SpringContextHolder.getBean("logService");
	}
	
}
