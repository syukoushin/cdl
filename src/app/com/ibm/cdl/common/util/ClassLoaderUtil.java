package com.ibm.cdl.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClassLoaderUtil {
	public static Class loadClass(String className) throws Exception {
		try {
			return getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new Exception("class not found '" + className + "'", e);
		}
		
	}

	public static ClassLoader getClassLoader() {
		return ClassLoaderUtil.class.getClassLoader();
	}

	public static InputStream getStream(String resource) {
		return getClassLoader().getResourceAsStream(resource);
	}

	public static Properties getProperties(String resource) throws Exception {
		Properties properties = new Properties();
		try {
			properties.load(getStream(resource));
		} catch (IOException e) {
			throw new Exception("couldn't load properties file '" + resource
					+ "'", e);
		}
		return properties;
	}
}