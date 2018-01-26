package com.ibm.core.util;

import org.apache.commons.lang.StringUtils;

public class JsonCheck {
	  /*** 
     *  
     * 获取JSON类型 
     *         判断规则 
     *             判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文本 
     *          
     * @param str 
     * @return 
     */ 
    public static JSON_TYPE getJSONType(String str){ 
        if(StringUtils.isEmpty(str)){ 
            return JSON_TYPE.JSON_TYPE_ERROR; 
        } 
        final char[] strChar = str.substring(0, 1).toCharArray(); 
        final char firstChar = strChar[0]; 
        if(firstChar == '{'){ 
            return JSON_TYPE.JSON_TYPE_OBJECT; 
        }else if(firstChar == '['){ 
            return JSON_TYPE.JSON_TYPE_ARRAY; 
        }else{ 
            return JSON_TYPE.JSON_TYPE_ERROR; 
        } 
    }
}


