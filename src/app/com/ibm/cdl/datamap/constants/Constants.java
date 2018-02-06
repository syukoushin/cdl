package com.ibm.cdl.datamap.constants;

public class Constants {
	
	// 默认密码
	public final static String  INIT_PWD ="111111";
	//系统参数
	public static String SYS_PARAMS = "SYS_PARAMS";
	// 版本号
	public static String VERSION = "VERSION";
	//加密过滤地址
	public static String SYS_PARAMS_DECRYPT = "SYS_PARAMS_DECRYPT";
	//过滤通用接口的参数
	public static String SYS_PARAM_LOG_METHOD = "SYS_PARAM_LOG_METHOD";

	// 用户类型
	// app用户
	public static String APP_USER = "1";
	// 后台用户
	public static String ADMIN_USER = "0";


	// 用户级别
	public static String USER_TYPE = "USER_LEVEL";
	// 一级 顶级管理员
	public static String USER_ADMIN = "1";
	// 三级  分公司管理员
	public static String USER_DEPT = "2";
	// 四级 没有登陆后台的权限
	public static String USER_COMMON = "3";
	
	
	
	// 发票
	public static String SHOW_TYPE_INVOICE ="1";
	
	// 行驶证
	public static String SHOW_TYPE_LICENSE = "2";
	
	// 身份证
	public static String SHOW_TYPE_IDCARD = "3";
	
	// 营业执照
	public static String SHOW_TYPE_BUSINESS = "4";

	public static String MENU_INVOICE ="invoice";

	public static String MENU_LICENSE ="lecense";

	public static String MENU_IDCARD ="idcard";

	public static String MENU_BUSINESS ="business";

	public static String MENU_USER = "user";

	public static String MENU_GROUP ="group";
	
}
