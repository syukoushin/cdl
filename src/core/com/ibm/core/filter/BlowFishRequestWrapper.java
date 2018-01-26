package com.ibm.core.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ibm.core.util.BlowFish;

public class BlowFishRequestWrapper extends RequestParamWrapper  {

	private static final Logger logger = Logger.getLogger(BlowFishRequestWrapper.class);
	
	public BlowFishRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String doValue(String value) {
		BlowFish blowFish = new BlowFish();
		String result = "";
		try {
			result = blowFish.decryptString(value);
			logger.debug(value+":BlowFish解析后的值是:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BlowFish解析数据异常", e);
		}
		return result;
	}
	
	@Override
	public boolean isParamsDoValue(String name, String[] values) {
		logger.debug("BlowFish解析前的name=" + name + ";value="+StringUtils.join(values, ","));
		return true;
	}
}
