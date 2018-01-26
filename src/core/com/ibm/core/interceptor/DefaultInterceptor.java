package com.ibm.core.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.ibm.core.log.service.LogService;
import com.ibm.core.util.ReflectionUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

/**
 * paramSet    表示要带有包含的参数，值的要做处理,默认做处理
 * resultKey   表示返回的结果值的key，如果为空，则不做处理
 */
public class DefaultInterceptor extends MethodFilterInterceptor {

	
	private Object action;
	private String methodName;
	private LogService moaLogService;
	private Map requestParam;
	
	private Map<String,Set<String>> paramSet;
	private String resultKey;
	
	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public DefaultInterceptor() {
		paramSet = new HashMap<String,Set<String>>();
	}
	
	public void initParam(ActionInvocation arg0) {
		action = arg0.getAction();
		methodName = arg0.getProxy().getMethod();
		requestParam = ActionContext.getContext().getParameters();
		appendFilter();
	}
	
	@Override
	public String doIntercept(ActionInvocation arg0) throws Exception {
		initParam(arg0);
		try {
			if (checkParam(arg0)) {
				return doParamIntercept(arg0);
			} else {
				return arg0.invoke();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return doException(e);
		}
		
	}
	
	// 通过方法和参数的处理
	public String doParamIntercept(ActionInvocation arg0) throws Exception {
		return arg0.invoke();
	}
	
	public String doException(Exception e) throws Exception {
		throw e;
	}
	
	public String getParamValue(String key) {
		return ServletActionContext.getRequest().getParameter(key);
	}
	
	public String getResultString() {
		try {
			if (StringUtils.isEmpty(resultKey)) {
				return "";
			} else {
				return (String)ReflectionUtils.invokeGetterMethod(action, resultKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String getActionAttr(String key) {
		try {
			return (String) ReflectionUtils.invokeGetterMethod(action, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean checkParam(ActionInvocation arg0) {
		// 默认情况是全部拦截
		if ((paramSet == null || paramSet.size() == 0)) return true;
		Map map = ActionContext.getContext().getParameters();
		Set<String> keySet = paramSet.keySet();
		for (String key : keySet) {
			if (map.containsKey(key)) {
				Object factValue = map.get(key);
				if (factValue.getClass().isArray()) {
					factValue = ((String[])factValue)[0];
				}
				Set valueSet = paramSet.get(key);
				if (valueSet.contains(factValue)) {
					return true;
				}
			} 
		}
		
		return false;
	}
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	/**
	 * 获取拦截action的简称
	 * @return
	 */
	public String getActionName() {
		
		return action.getClass().getSimpleName();
	}
	
	/**
	 * 获取拦截action的全称
	 * @return
	 */
	public String getActionFullName() {
		return action.getClass().getName();
	}

	public LogService getMoaLogService() {
		return moaLogService;
	}

	public void setParamSet(String param) {
		String[] partParam = param.split(";");
		for (int i = 0; i < partParam.length; i++) {
			String[] keyValue = partParam[i].split("=");
			Set<String> tempSet = TextParseUtil.commaDelimitedStringToSet(keyValue[1]);
			paramSet.put(keyValue[0], tempSet);
		}
	}
	
	public void appendFilter() {
		String myFilter = getFilter();
		if (StringUtils.isNotEmpty(myFilter)) {
			String[] filter = myFilter.split(";");
			for (int i = 0; i < filter.length; i++) {
				String[] keyValue = filter[i].split("=");
				Set<String> tempSet = TextParseUtil.commaDelimitedStringToSet(keyValue[1]);
				paramSet.put(keyValue[0], tempSet);
			}
		}
	}
	
	public String getFilter() {
		return "";
	}

}
