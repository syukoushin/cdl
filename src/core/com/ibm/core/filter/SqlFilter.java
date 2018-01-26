package com.ibm.core.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class SqlFilter implements Filter{
	

	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
//		    init();
		    HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse res = (HttpServletResponse) response;
	        // 获得所有请求参数名
	        Enumeration<?> params = req.getParameterNames();
	        String[] appIds =req.getParameterValues("appId");
	        String  appId = (appIds!=null&&appIds.length>0?appIds[0]:"");
	        String sql = "";
	        while (params.hasMoreElements()) {
	            // 得到参数名
	            String name = params.nextElement().toString();
	            // System.out.println("name===========================" + name +
	            // "--");
	            // 得到参数对应值
	            String[] value = req.getParameterValues(name);
	            for (int i = 0; i < value.length; i++) {
	                sql = sql + value[i];
	            }
	        }
	        if (sqlValidate(sql)) {
	        	    Map map = new HashMap();
	        	    map.put("responseCode", "0");
	        	    map.put("responseMessage","含有非法参数");
	        	    sendResponseMessage(JSONObject.fromObject(map).toString(), res);
	        } else {
	            chain.doFilter(req, res);
	        }
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	// 校验
    protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();// 统一转为小写
        String badStr = "or|count|and|exec|execute|insert|select|delete|update|drop|chr|master|truncate|char|declare|sitename|net user|xp_cmdshell|like";
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
        	String bad1=" "+badStrs[i];
            if (str.indexOf(bad1) != -1) {
                System.out.println("匹配到：" + badStrs[i]);
                return true;
            }
        	String bad2 = badStrs[i]+" ";
            if (str.indexOf(bad2) != -1) {
                System.out.println("匹配到：" + badStrs[i]);
                return true;
            }
            String bad3 = " "+badStrs[i]+" ";
            if (str.indexOf(bad3) != -1) {
                System.out.println("匹配到：" + badStrs[i]);
                return true;
            }
        }
        return false;
    }
    
//    public void init() {
//		String checkAppIdListStr = DataMapUtils.getDataMapSub(Constants.SYS_PARAMS, Constants.SQL_FILTER_APPID);
//		if (checkAppIdListStr != null && !"".equals(checkAppIdListStr)) {
//			StringTokenizer st = new StringTokenizer(checkAppIdListStr, ",");
//			checkAppIdList.clear();
//			while (st.hasMoreTokens()) {
//				checkAppIdList.add(st.nextToken());
//			}
//		}
//	}
    
	 private void sendResponseMessage(String message,HttpServletResponse response) {
			PrintWriter pw = null;
			try {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				pw = response.getWriter();
				pw.print(message);
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pw != null) {
					try {
						 pw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
	}
}
