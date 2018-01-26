package com.ibm.cdl.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * 属性文件读取操作
 * 
 */
public class PropertiesUtils {

	private static final Logger logger = Logger
			.getLogger(PropertiesUtils.class);

	private static String configFile = "application.properties";

	private static Properties props = null;
	private static PropertiesUtils instance = null;

	private PropertiesUtils() {
	}

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static PropertiesUtils singlton() {
		if (instance == null) {
			instance = new PropertiesUtils();
		}
		return instance;
	}

	/**
	 * 重新加载配置文件
	 * 
	 */
	public void reloadProperties() {
		loadProperties();
	}

	/**
	 * 根据属性名称获得属性值
	 * 
	 * @param propertyName
	 * @return
	 */
	public String getProperty(String propertyName) {

		if (props == null) {
			loadProperties();
		}
		try {
			return props.getProperty(propertyName);
		} catch (Exception e) {
			logger.error("get property value by " + propertyName
					+ " property name error:" + e.getMessage());
			return null;
		}

	}

	/**
	 * 从XML文件加载Properties
	 * 
	 */
	private void loadProperties() {
		try {
			logger.info("load config property file.");
			InputStream in = PropertiesUtils.class.getClassLoader()
					.getResourceAsStream(configFile);
			props = new Properties();
			props.load(in);
		} catch (Exception e) {
			logger.error("load config property file error:" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtils.singlton().getProperty("jdbc.url"));
	}
}