package com.ibm.core.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class ListUtils {

	/**
	 * 去除list中为空的数据
	 * @param source
	 * @return
	 */
	public static List removeNullObject(List source) {
		
		List result = new ArrayList();
		
		if (source == null || source.size() == 0) return result;
		
		Object obj = null;
		
		for (int i = 0; i < source.size(); i++) {
			obj = source.get(i);
			if (obj == null) continue;
			result.add(obj);
		}
		
		return result;
	}
	
	/**
	 * 判断list是否为空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List list) {
		
		if (list == null || list.size() == 0) return true;
		
		return false;
	}
	
	public static boolean isNotEmpty(List list) {
		
		return !isEmpty(list);
	}
	
	
	/**
	 * 对数组中的数据进行统一赋值
	 * @param list
	 * @param property
	 * @param value
	 */
	public static List setValue(List list,String property,Object value) {
	
		if (isEmpty(list)) return null;
		
		for (Object obj : list) {
			
			ReflectionUtils.setFieldValue(obj, property, value);
		}
		
		return list;
	}
	
	/**
	 * 对数组中的数据进行多
	 * @param list
	 * @param propertys
	 * @param objects
	 * @return
	 */
	public static List setValues(List list,String[] propertys,Object[] objects) {
		
		if (isEmpty(list)) return null;
		
		for (Object obj : list) {
			
			for (int i = 0; i < propertys.length; i++) {
				ReflectionUtils.setFieldValue(obj, propertys[i], objects[i]);
			}
		}
		
		return list;
	}

	
	public static List copyList(List sourceList,List targetList) throws Exception {
		
		if (isEmpty(sourceList)) return null;
		
		for (int i = 0; i < sourceList.size(); i++) {
			
			Object sourceObj = sourceList.get(i);
			Object targetObj = targetList.get(i);
			
			BeanUtils.copyProperties(sourceObj, targetObj);
		}
		
		return targetList;
	}
	
	
}
