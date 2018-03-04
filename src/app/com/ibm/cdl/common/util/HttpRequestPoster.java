/**
 * 文件名：HttpRequestPoster.java
 *
 * 创建人：[马佳] - [jia_ma@asdc.com.cn] // 根据个人情况在模板中进行修改
 *
 * 创建时间：2012-2-14 下午01:36:44
 *
 * 版权所有：IBM
 */
package com.ibm.cdl.common.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.ibm.cdl.util.CommunicatorException;
import com.ibm.cdl.util.ParseUtil;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

/**
 * <p> Title: [名称]</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2012-2-14</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: IBM</p>
 * @author [马佳] - [jia_ma@asdc.com.cn] 
 * @version 1.0
 */
public class HttpRequestPoster {


	public static void main(String[] args) throws Exception {
//		String fesf=HttpRequestPoster.accessNetPage("http://211.136.93.58/moafcm/spm/index.do?invok=getSumOrderNum&userCode=yxf");
//		fesf=fesf.replaceAll("encoding=\"gbk\"", "encoding=\"utf-8\"");
//		String result=null;
//		try {
//			HashMap transactMap=new HashMap();
//			transactMap=ParseUtil.transMap(fesf);
//			List paramList=(ArrayList)transactMap.get("newsContent");
//			HashMap map=(HashMap)paramList.get(0);
//			System.out.println(transactMap.toString());
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}  
//		http://211.138.125.209:88/ZJProject/news/index.do?invoke=getInfoList&type=newsindex
		String url = "http://211.138.125.209:88/ZJProject/news/index.do";
//		String url = "http://192.168.1.119:8080/ZJProject/news/index.do";
		HashMap hm = new HashMap();
		hm.put("invoke", "getInfoList");
		hm.put("type", "newsindex");

		System.out.println(HttpRequestPoster.communicateNew(hm, url));
	}

	/**
	 * <p>根据URL获得网络对象</p>
	 * Created on 2012-2-15
	 *
	 * @author: [马佳] - [jia_ma@asdc.com.cn]
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static HashMap getNetObjectByURL(String URL) {
		String fesf = HttpRequestPoster.accessNetPage(URL);
		HashMap transactMap = null;
		try {
			transactMap = ParseUtil.transMap(fesf);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return transactMap;
	}

	/**
	 * 访问网络资源
	 *
	 * @param netUrl URL
	 * @return
	 */
	public static InputStream accessNetResources(String netUrl) {
		InputStream rsIo = null;
		try {

			URL url = new URL(netUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			int httpResult = conn.getResponseCode();
			if (httpResult == HttpURLConnection.HTTP_OK) {
				int filesize = conn.getContentLength();
				rsIo = conn.getInputStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsIo;
	}

	/**
	 * 访问网络页面
	 *
	 * @param netUrl
	 * @return
	 */
	public static String accessNetPage(String netUrl) {
		InputStream stream = null;
		byte[] fdata = null;
		String pageHtml = "";
		try {

			URL url = new URL(netUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			stream = conn.getInputStream();
			if (stream != null) {
				byte[] buf = new byte[40960];
				fdata = new byte[0];
				int read = 0;
				while ((read = stream.read(buf)) != -1) {
					byte[] newBytes = new byte[fdata.length + read];
					System.arraycopy(fdata, 0, newBytes, 0, fdata.length);
					System.arraycopy(buf, 0, newBytes, fdata.length, read);
					fdata = newBytes;
					newBytes = null;
				}
				stream.close();
				stream = null;

				if (fdata != null && fdata.length > 0) {
					pageHtml = new String(fdata, "GBK");
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageHtml;
	}


	/**
	 *
	 * @param h
	 * @param url
	 * @param type
	 * @return
	 * @throws CommunicatorException
	 */
	public static String communicate(Map<String, Object> h, String url, String type) throws CommunicatorException {
		String ret = null;
		WebRequest webRequest = null;
		WebResponse webResponse = null;
		try {
			if (null == type || "".equalsIgnoreCase(type) || "POST".equalsIgnoreCase(type)) {
				webRequest = new PostMethodWebRequest(url);
				if(h!=null) {
					Iterator iter = h.keySet().iterator();
					while (iter.hasNext()) {
						String key = (String) iter.next();
						Object valueObj = h.get(key);
						String putValue = "";
						if (valueObj == null) {
							valueObj = "";
						}
						putValue = valueObj.toString();
						webRequest.setParameter(key, putValue);
					}
					webResponse = new WebConversation().getResponse(webRequest);
				}
			}else {
				//oa新接口要求参数传递有序 故采用append形式传递参数
				if (h != null) {
					StringBuilder sb = new StringBuilder();
					Iterator iter = h.keySet().iterator();
					while (iter.hasNext()) {
						String key = (String) iter.next();
						Object valueObj = h.get(key);
						String putValue = "";
						if (valueObj == null) {
							valueObj = "";
						}
						putValue = valueObj.toString();
						putValue = putValue;
						sb.append("&" + key + "=" + putValue);
					}
					url += "?" + sb.substring(1);
					webRequest = new GetMethodWebRequest(url);
					webResponse = new WebConversation().getResponse(webRequest);
//					webRequest = new GetMethodWebRequest(url);
//					if(h!=null) {
//						Iterator iter = h.keySet().iterator();
//						while (iter.hasNext()) {
//							String key = (String) iter.next();
//							Object valueObj = h.get(key);
//							String putValue = "";
//							if (valueObj == null) {
//								valueObj = "";
//							}
//							putValue = valueObj.toString();
//							webRequest.setParameter(key, putValue);
//						}
//						webResponse = new WebConversation().getResponse(webRequest);
//					}
				}
			}
			ret = webResponse.getText().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * <p>Discription:[方法功能中文描述]</p>
	 * Created on 2012-2-21
	 * @author: [马佳] - [jia_ma@asdc.com.cn]
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static String communicate(HashMap h,String url) throws CommunicatorException {

		String ret = null;
		try {
			WebRequest webRequest = new PostMethodWebRequest(url);

			if(h!=null){
				Iterator iter = h.keySet().iterator();
				while (iter.hasNext()) {
					String key = (String)iter.next();
					Object valueObj=h.get(key);
					String putValue="";
					if(valueObj==null){
						valueObj="";
					}
					putValue=valueObj.toString();
					putValue=putValue;
					webRequest.setParameter(key, putValue);
				}
			}
			WebResponse webResponse = new WebConversation().getResponse(webRequest);

			ret = webResponse.getText().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	
	/**
	 * 消除134，146环境问题
	 * @param h
	 * @param url
	 * @return
	 * @throws CommunicatorException
	 */
	public static String communicateNew(HashMap h,String url) throws CommunicatorException {

		PostMethod post = new PostMethod(url);
		
	    try
	    {
			HttpClient client = new HttpClient(); 

			Set entrySet = h.entrySet();  
	        int dataLength = entrySet.size();  
	        NameValuePair[] params = new NameValuePair[dataLength];  
	        int i = 0;  
	        for(Iterator itor = entrySet.iterator();itor.hasNext();){  
	        	Map.Entry entry = (Map.Entry)itor.next();  
	        	params[i++] = new NameValuePair(entry.getKey().toString(),URLEncoder.encode(entry.getValue().toString(), "utf-8"));  
	        }  
	        post.setRequestBody(params);
			int j=client.executeMethod(post);
	    	String result = post.getResponseBodyAsString();
	    	return result;
	    }
	    catch (Throwable e)
	    {
	    	e.printStackTrace();
	    	return "";
	    }
	    finally
	    {
	    	post.releaseConnection();
	    }
	
	}
	
	/**
	 * 消除134，146环境问题
	 * @param h
	 * @param url
	 * @return
	 * @throws CommunicatorException
	 */
	public static String communicateNewNoEncode(HashMap h,String url) throws CommunicatorException {

		PostMethod post = new PostMethod(url);
		
	    try
	    {
			HttpClient client = new HttpClient(); 

			Set entrySet = h.entrySet();  
	        int dataLength = entrySet.size();  
	        NameValuePair[] params = new NameValuePair[dataLength];  
	        int i = 0;  
	        for(Iterator itor = entrySet.iterator();itor.hasNext();){  
	        	Map.Entry entry = (Map.Entry)itor.next();  
	        	params[i++] = new NameValuePair(entry.getKey().toString(),entry.getValue().toString());  
	        }  
	        post.setRequestBody(params);
			int j=client.executeMethod(post);
	    	String result = post.getResponseBodyAsString();
	    	return result;
	    }
	    catch (Throwable e)
	    {
	    	e.printStackTrace();
	    	return "";
	    }
	    finally
	    {
	    	post.releaseConnection();
	    }
	
	}
}
