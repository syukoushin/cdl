package com.ibm.core.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ibm.core.util.DESUtil;

public class DesRequestWrapper extends RequestParamWrapper  {

	private static final Logger logger = Logger.getLogger(DesRequestWrapper.class);
	
	public DesRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String doValue(String value) {
		String result = "";
		try {
			result = DESUtil.getInstance().decrypt(value);
			logger.debug(value+":Des解析后的值是:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Des解析数据异常", e);
		}
		return result;
	}
	
	@Override
	public boolean isParamsDoValue(String name, String[] values) {
		logger.debug("Des解析前的name=" + name + ";value="+StringUtils.join(values, ","));
		return true;
	}
}
