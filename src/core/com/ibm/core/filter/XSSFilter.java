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

public class XSSFilter implements Filter {

	private String[] characterParams = null;
	private List<String> checkURLList = new ArrayList<String>();

	public void init(FilterConfig filterConfig) throws ServletException {

		this.characterParams = filterConfig.getInitParameter("characterParams").split(",");
		// 取出进行过滤的页面参数，并放入集合中
		String checkURLListStr = filterConfig.getInitParameter("checkURLList");
		if (checkURLListStr != null) {
			StringTokenizer st = new StringTokenizer(checkURLListStr, ";");
			checkURLList.clear();
			while (st.hasMoreTokens()) {
				checkURLList.add(st.nextToken());
			}
		}
	}

	public void destroy() {
		characterParams = null;
		checkURLList.clear();
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest  httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (checkRequestURLInFilterList(httpRequest)) {
			XSSRequestWrapper  xssRequestWrapper = new XSSRequestWrapper((HttpServletRequest) httpRequest);
			chain.doFilter(xssRequestWrapper, httpResponse);
		}else{
			chain.doFilter(httpRequest, httpResponse);
		}
	}
	
	private boolean checkRequestURLInFilterList(HttpServletRequest request) {
		String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		if(checkURLList.contains("/*")){
			return true;
		}
		return checkURLList.contains(uri);
	}

}
