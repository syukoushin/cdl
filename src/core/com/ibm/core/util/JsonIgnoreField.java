package com.ibm.core.util;

import com.ibm.cdl.manage.pojo.User;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class JsonIgnoreField implements PropertyFilter{
		/**
		 * 忽略的属性名称
		 */
		private String[] fields;
		
		/**
		 * 显示还是忽略标志 默认忽略
		 */
		private boolean showOrhide = false;
		
		/**
		 * 是否忽略集合
		 */
		private boolean ignoreColl = false;
	 
		/**
		 * 空参构造方法<br/>
		 * 默认不忽略集合
		 */
		public JsonIgnoreField() {
			// empty
		}
	 
		/**
		 * 构造方法
		 * @param fields 忽略属性名称数组
		 */
		public JsonIgnoreField(boolean showOrhide,String[] fields) {
			this.showOrhide = showOrhide;
			this.fields = fields; 
		}
		
		/**
		 * 构造方法
		 * @param ignoreColl	是否忽略集合
		 * @param fields	忽略属性名称数组
		 */
		public JsonIgnoreField(boolean showOrhide,boolean ignoreColl, String[] fields) {
			this.showOrhide = showOrhide;
			this.fields = fields;
			this.ignoreColl = ignoreColl; 
		}
		
		/**
		 * 构造方法
		 * @param ignoreColl 是否忽略集合
		 */
		public JsonIgnoreField(boolean ignoreColl) {
			this.ignoreColl = ignoreColl; 
		}
	 
		public boolean apply(Object source, String name, Object value) {
			//忽略值为null的属性
			if(value == null)
				return true;
			//剔除自定义属性，获取属性声明类型
//			try {
//				declaredField = source.getClass().getDeclaredField(name);
//			} catch (NoSuchFieldException e) {
//				e.printStackTrace();
//			}
//			// 忽略集合
//			if (declaredField != null) {
//				if(ignoreColl) {
//					if(declaredField.getType() == Collection.class
//							|| declaredField.getType() == Set.class) {
//						return true;
//					}
//				}
//			}
			// 忽略设定的属性
			if(fields != null && fields.length > 0) {
				if(juge(fields,name)) {
					if(showOrhide){
						return false;
					}else{
						return true;
					}
		        } else {  
		        	if(showOrhide){
						return true;
					}else{
						return false;
					}
		        } 
			}
			 
			return false;
		}
		/**
		 * 过滤忽略的属性
		 * @param s
		 * @param s2
		 * @return
		 */
		 public boolean juge(String[] s,String s2){  
	         boolean b = false;  
	         for(String sl : s){  
	             if(s2.equals(sl)){  
	                 b=true;  
	             }  
	         }  
	         return b;  
	     }  
		public String[] getFields() {
			return fields;
		}
	 
		/**
		 * 设置忽略的属性
		 * @param fields
		 */
		public void setFields(String[] fields) {
			this.fields = fields;
		}
	 
		public boolean isIgnoreColl() {
			return ignoreColl;
		}

		public boolean isShowOrhide() {
			return showOrhide;
		}

		public void setShowOrhide(boolean showOrhide) {
			this.showOrhide = showOrhide;
		}

		/**
		 * 设置是否忽略集合类
		 * @param ignoreColl
		 */
		public void setIgnoreColl(boolean ignoreColl) {
			this.ignoreColl = ignoreColl;
		}
		
		public static void main(String[]args){
			JsonConfig config = new JsonConfig();
			config.setJsonPropertyFilter(new JsonIgnoreField(true, new String[]{"name"})); // 忽略掉name属性及集合对象
			User entity = new User();
			JSONObject fromObject = JSONObject.fromObject(entity, config );
			System.out.print(fromObject.toString());
		}
}
