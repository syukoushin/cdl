package com.ibm.core.filter;

/**     
 * @create.date: 2012-8-8 下午04:52:21
 * @author: syl 
 * @see:com.chinawsoft.portal.app.portal.ui.servlet 
 */

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @create.date: 2012-8-8 下午04:52:21
 * @comment: <p>
 *           TODO
 *           </p>
 * @see: com.chinawsoft.portal.app.portal.ui.filter
 * @author: syl
 * @modify.by: syl
 * @modify.date: 2012-8-8 下午04:52:21
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class RequestParamWrapper extends HttpServletRequestWrapper {

	private static List<String> includePara = new ArrayList<String>(){{add("login");add("getAppList");add("getFramVersion");add("getGtasksNum");add("getGtasksNumNew");add("savefeedback");add("getPortalInfo");add("clearServiceCallerFactory");add("saveVersionMess");add("getPoiMess");add("getModule");add("queryFeedback");add("getToken");add("queryIsUpdate");add("refreshDataMap");add("saveAppLogin");add("getcomments");add("savecomments");
	add("queryMessage");add("queryMessageCount");add("delMessage");add("doReadMessage");}};
	
	public RequestParamWrapper(HttpServletRequest request) {
		super(request);
	}
	
	private Map<String, String[]> parameterMap;

    public void setParameterMap(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}

	@Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        Map<String, String[]> parameterMap = new HashMap<String, String[]>(map.size());
        Iterator<Map.Entry<String, String[]>> entryIter = map.entrySet().iterator();
        String[] invokeVal = (String[])map.get("invoke");
        if(invokeVal != null && invokeVal.length >0){
        	for(int i = 0 ; i < includePara.size() ; i++){
            	if(includePara.contains(invokeVal[0])){
            		this.setParameterMap(map);
            		return map;
            	}
            }
        }
        
        while (entryIter.hasNext()) {
            Map.Entry<String, String[]> entry = entryIter.next();
            String[] origVals = entry.getValue();
            int vallen = origVals.length;
            String[] encodeVals = new String[vallen];
            
            if (isParamsDoValue(entry.getKey(),origVals)) {
            	for (int i = 0; i < vallen; i++) {
            		if (origVals[i] != null ) {
            			encodeVals[i] = doValue(origVals[i]);
            		}
                }
                parameterMap.put(entry.getKey(), encodeVals);
            }
        }
        this.setParameterMap(parameterMap);//新增加
        return parameterMap;
    }

    @Override
	public Enumeration getParameterNames() {
    	Map<String, String[]> map = super.getParameterMap();
		Vector l = new Vector(map.keySet());
		return l.elements();
	}

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        if (isParamsDoValue(parameter,values)) {
        	int count = values.length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
            	if (values[i] != null) {
            		encodedValues[i] = doValue(values[i]);
            	}
            }
            return encodedValues;
        }
        
        return values;
    }

	/**
	* 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
	* 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	* getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	*/
//	@Override
//	public String getParameter(String name) {
//		String value = super.getParameter(name);
//		if (value != null && isParamsDoValue(name,value)) {
//			value = doValue(value);
//		}
//		return value;
//	}
    @Override
	public String getParameter(String name) {//新增修改
		String[] values = parameterMap.get(name);
		String value = "";
		if(values!=null){
			if(values.length>0){
				value = values[0];
			}
		}
		return value;
	}


    /**
    * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
    * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
    * getHeaderNames 也可能需要覆盖
    */
    @Override
    public String getHeader(String name) {
    	
    	String value = super.getHeader(name);
    	if (value != null && isHeaderDoValue(name,value)) {
    		value = doValue(value);
    	}
	    return value;
    }

    /**
     * 对请求参数中的值进行处理
     * @param value
     * @return
     */
    public abstract String doValue(String value);
    
    /**
     * 对请求参数是否进行过滤，默认为过滤
     * @param name
     * @return
     */
    public boolean isParamsDoValue(String name,String value) {
    	return isParamsDoValue(name,new String[]{value});
    }
    
    public boolean isParamsDoValue(String name,String[] values) {
    	return true;
    }
   
    /**
     * 对请求的header参数是否进行过滤，默认为过滤
     * @param headerName
     * @return
     */
    public boolean isHeaderDoValue(String headerName,String headerValue) {
    	return true;
    }
    
    
    
}
