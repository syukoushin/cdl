package com.ibm.core.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 获得Spring容器中的实例
 * @author syl
 */
public class BeanFactoryHolder {

	public static String[] BEANFACTORY_CONFIG_FILENAMES = {"classpath*:applicationContext-*.xml","classpath*:**/*-spring.xml","classpath*:*-spring.xml"};

	private static BeanFactory beanFactory = null;

	public static BeanFactory getBeanFactory() {
		if (beanFactory == null) {

			beanFactory = new ClassPathXmlApplicationContext(
					BEANFACTORY_CONFIG_FILENAMES);

		}
		return beanFactory;
	}

	public static void setBeanFactory(BeanFactory args) {
		beanFactory = args;
	}

	public static Object getBean(String name) {
		if ((beanFactory == null) && (getBeanFactory() == null))
			return null;

		return beanFactory.getBean(name);
	}
	
	
}