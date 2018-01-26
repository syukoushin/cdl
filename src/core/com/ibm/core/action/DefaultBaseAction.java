package com.ibm.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.cdl.manage.pojo.User;
import com.ibm.core.log.service.LogService;
import com.ibm.core.util.ServletUtils;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DefaultBaseAction extends ActionSupport {
	
	private static final String HEADER_ENCODING = "encoding";
	private static final String HEADER_NOCACHE = "no-cache";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final boolean DEFAULT_NOCACHE = true;
	
	//result type
	public static final String LIST="list";
	public static final String EDIT="edit";
	public static final String DETAIL="detail";
	
	public String redirectUrl;
	
	public String redirectAction;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8697986716185641524L;
	public DefaultBaseAction() {
		pageNo = 1;
		pageSize = 10;
	}

	/**
	 *
	 * @param data
	 * @param msg
	 * @return
	 */
	public Map resultMap(String data, String msg) {
		Map map = new HashMap();
		if (data == null || "failed".equals(data)) {
			if (msg == null || "".equals(msg)) {
				msg = "请求失败";
			}
			map.put("optMsg", msg);
			map.put("optSts", "0");
		}else if ("success".equals(data)) {
			Date cDate = new Date();
			String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDate);
			map.put("currentTime", currentTime);
			map.put("optMsg", "请求成功");
			map.put("optSts", "1");
		} else {
			map.put("optMsg", "请求成功");
			map.put("optSts", "1");
			Date cDate = new Date();
			String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDate);
			map.put("currentTime", currentTime);
			if(data.trim().startsWith("[")){
				JSONArray  dataJson = JSONArray.fromObject(data);
				map.put("data", dataJson);
			}else {
				JSONObject dataJson = JSONObject.fromObject(data);
				map.put("data", dataJson);
			}
		}
		return map;
	}
	/**
	 * 客户端获取请求参数
	 * @param key
	 * @return
	 */
	protected String getParameter(String key) {
		
		String result = "";
		
		result = getRequest().getParameter(key);
		
		if (StringUtils.isNotEmpty(result)) return result;
		
		return (String) getRequest().getAttribute(key);
	}
	
	protected String getResourcesPath(HttpServletRequest request){
		String resources="resources";
		String requestURL=request.getRequestURL().toString();
		requestURL=requestURL.substring(0,requestURL.lastIndexOf("/")+1);
		return requestURL+resources;
		
	}
	
	protected String getContextPath(HttpServletRequest request){
		String resources="resources";
		String requestURL=request.getRequestURL().toString();
		String context=request.getContextPath();
		requestURL=requestURL.substring(0,requestURL.indexOf(context)+context.length());
		return requestURL+"/"+resources;
	}
	
	protected String getResourcesPathForPortal(HttpServletRequest request){
		String resources="resources/application";
		String requestURL=request.getRequestURL().toString();
		requestURL=requestURL.substring(0, requestURL.lastIndexOf("portal"));
//		requestURL=requestURL.substring(0,requestURL.lastIndexOf("/")+1);
		return requestURL+resources;
	}
	
	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString, final String... headers) {
		render("application/json", jsonString, headers);
	}
	
	/**
	 * 直接输出内容的简便函数.

	 * eg.
	 * render("text/plain", "hello", "encoding:GBK");
	 * render("text/plain", "hello", "no-cache:false");
	 * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
	 * 
	 * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final String contentType, final String content, final String... headers) {
		HttpServletResponse response = initResponseHeader(contentType, headers);
		try {
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 分析并设置contentType与headers.
	 */
	private static HttpServletResponse initResponseHeader(final String contentType, final String... headers) {
		//分析headers参数
		String encoding = DEFAULT_ENCODING;
		boolean noCache = DEFAULT_NOCACHE;
		for (String header : headers) {
			String headerName = StringUtils.substringBefore(header, ":");
			String headerValue = StringUtils.substringAfter(header, ":");

			if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
				encoding = headerValue;
			} else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
				noCache = Boolean.parseBoolean(headerValue);
			} else {
				throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
			}
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		//设置headers参数
		String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			ServletUtils.setDisableCacheHeader(response);
		}

		return response;
	}

	/**
	 * 返回给客户端Javascript提示窗口
	 * @param alertText 提示文本
	 * @return 
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public String returnScriptAlertWindow(String alertText){
		try {
			StringBuffer str = new StringBuffer();
			str.append("<script>");
			str.append("alert('" + alertText +"');");
			str.append("history.back();");
			str.append("</script>");
			this.getResponse().setContentType("text/html; charset=UTF-8");
			this.getResponse().getWriter().println(str);
			return null;
		} catch (IOException e) {
			throw new RuntimeException("调用提示窗口出现异常！");
		}
	}

	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	public static User getSessionUser() {
		return (User) getSession().getAttribute("CURRENT_USER");
	}
	
	public static String getTaskUserCode(){
		return getTaskUser().getUserCode();
	}
	
	
	public static String getTaskUserIdCardNum(){
		return getTaskUser().getIdCardNum();
	}
	
	public static String getTaskUserName(){
		return getTaskUser().getUserName();
	}
	
	public static User getTaskUser(){
		return (User) getSession().getAttribute("TASK_USER");
	}
	
	protected String getWebApplicationAbsolutePath() {
		String realPath = getServletContext().getRealPath("/");
		if (realPath != null && !realPath.endsWith("/"))
			return (new StringBuilder(String.valueOf(realPath))).toString();
		else
			return realPath;
	}

	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpSession getHttpSession() {
		return ServletActionContext.getRequest().getSession();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	public String getServerPaths(){
		String serverPath=getServletContext().getRealPath("");
		return serverPath+"/resources/";
	}

	public Object getBean(String name) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return wac.getBean(name);
	}

	public String goUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
		return "redirectUrl";
	}
	
	public String goAction(String redirectAction) {
		this.redirectAction = redirectAction;
		return "redirectAction";
	}
	
	public void sendResponseMessage(String message) {
		
		PrintWriter pw = null;
		try {
			
			this.getResponse().setCharacterEncoding("utf-8");
			
			pw = this.getResponse().getWriter();
			
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
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public void setPageNo(String pageNo) {
		this.pageNo = Integer.parseInt(pageNo);
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	protected int pageNo;
	protected int pageSize;
	protected String sortColumn;
	protected String sortType;
	protected String userCode;
	protected LogService moaLogService;
	public LogService getMoaLogService() {
		return moaLogService;
	}

	public void setMoaLogService(LogService moaLogService) {
		this.moaLogService = moaLogService;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	
}
