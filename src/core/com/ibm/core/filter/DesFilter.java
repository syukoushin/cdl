package com.ibm.core.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.cdl.datamap.action.DataMapUtils;
import com.ibm.cdl.datamap.constants.Constants;

public class DesFilter implements Filter {

	private List<String> checkURLList = new ArrayList<String>();

	public void init(FilterConfig filterConfig) throws ServletException {
		// 取出进行过滤的页面参数，并放入集合中
//		String checkURLListStr = filterConfig.getInitParameter("checkURLList");
//		String checkURLListStr = DataMapUtils.getDataMapSub(Constants.SYS_PARAMS, Constants.SYS_PARAMS_DECRYPT);
//		if (checkURLListStr != null && !"".equals(checkURLListStr)) {
//			StringTokenizer st = new StringTokenizer(checkURLListStr, ";");
//			checkURLList.clear();
//			while (st.hasMoreTokens()) {
//				checkURLList.add(st.nextToken());
//			}
//		}
	}

	public void destroy() {
		checkURLList.clear();
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		init();
		HttpServletRequest  httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (checkRequestURLInFilterList(httpRequest)) {
			DesRequestWrapper  desRequestWrapper = new DesRequestWrapper((HttpServletRequest) httpRequest);
			chain.doFilter(desRequestWrapper, httpResponse);
		}else{
			chain.doFilter(httpRequest, httpResponse);
		}
	}
	
	private boolean checkRequestURLInFilterList(HttpServletRequest request) {
		String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		return checkURLList.contains(uri);
	}
	
	public void init() {
		String checkURLListStr = DataMapUtils.getDataMapSub(Constants.SYS_PARAMS, Constants.SYS_PARAMS_DECRYPT);
		if (checkURLListStr != null && !"".equals(checkURLListStr)) {
			StringTokenizer st = new StringTokenizer(checkURLListStr, ";");
			checkURLList.clear();
			while (st.hasMoreTokens()) {
				checkURLList.add(st.nextToken());
			}
		}
	}

}
