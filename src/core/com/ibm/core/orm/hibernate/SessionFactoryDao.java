package com.ibm.core.orm.hibernate;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <ID>
 *            主键类型
 * 
 */
public class SessionFactoryDao<T, ID extends Serializable> extends
		HibernateDao<T, ID> {
	/**
	  * 注入mySessionFactory
	  */
	 @Autowired@Qualifier("sessionFactory") /*****注入*****/
	 protected SessionFactory sessionFactory;
	 @PostConstruct /*****bean实例化时执行该方法*******/
	 protected void injectSessionFactory(){
		 super.setSessionFactory(sessionFactory);
	 }
}
