package com.ibm.core.filter;

import javax.servlet.http.HttpServletRequest;

/**     
 * @create.date: 2012-8-8 下午04:52:21
 * @author: syl 
 * @see:com.chinawsoft.portal.app.portal.ui.servlet 
 */

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
public class XSSRequestWrapper extends RequestParamWrapper {

	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
	}

    /**
     * 对请求参数中的值进行处理
     * @param value
     * @return
     */
    public String doValue(String value) {
    	return stripXSS(value);
    }
    
    private String stripXSS(String value) {
        if (null != value) {
            value = value.replaceAll("&", "&amp;");
            value = value.replaceAll("<", "&lt;");
            value = value.replaceAll(">", "&gt;");
            value = value.replaceAll("\"", "&quot;");
        }
        return value;
    }
    
}
