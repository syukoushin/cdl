package com.ibm.core.orm.hibernate;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
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
public class BiSessionFactoryDao<T, ID extends Serializable> extends
		HibernateDao<T, ID> {
	/**
	  * 注入mySessionFactory
	  */
	 @Autowired@Qualifier("biSessionFactory") /*****注入*****/
	 protected SessionFactory biSessionFactory;
	 @PostConstruct /*****bean实例化时执行该方法*******/
	 protected void injectSessionFactory(){
		 super.setSessionFactory(biSessionFactory);
	 }
	 
	 /**
	  * 返回结果map
	  * @param sql
	  * @return
	  */
	 public List<Map<String,Object>> findBySql(String sql){
		  Session session = super.getSession();
		  //获得SQLQuery对象
		  SQLQuery query = session.createSQLQuery(sql);
		  //设定结果结果集中的每个对象为Map类型   
		  query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		  //执行查询
		  List list = query.list();
		  return list;
	 }
	 

	/**
	 * 执行sql更新等
	 * @param sql
	 * @param paramter
	 * @return
	 */
	public int delBySql(String sql, Map paramter) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		Iterator<Map.Entry<String, Object>> iterator = paramter.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			if(entry.getValue() instanceof String[]){
				query.setParameterList(entry.getKey(), (String[])entry.getValue());
			} else {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.executeUpdate();
	}
}
