package com.ibm.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.ibm.cdl.manage.pojo.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		//判断用户访问的是否是前台
		if("//login.do".equals(getActionPath(arg0)) || getActionPath(arg0).contains("portal")){
			return arg0.invoke();		
		}else{
			User user=(User) session.getAttribute("CURRENT_USER");
			if (user == null) {
					return "relogin";
			}
		}
		return arg0.invoke();
	}

	public String getActionPath(ActionInvocation invocation) {
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String actionPath = namespace + "/" + actionName + ".do"; 
		return actionPath;
	}
}
