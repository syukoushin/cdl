package com.ibm.core.service.impl;

import java.util.List;
import java.util.Map;

import com.ibm.core.orm.Page;
import com.ibm.core.orm.hibernate.HibernateDao;
import com.ibm.core.service.CommonService;




public class CommonServiceImpl<T,K extends HibernateDao<T,String>> implements CommonService<T> {

	private K baseDao;
	
	public void delete(String id) {
		baseDao.delete(id);
	}

	/**
	 * 分页查询
	 */
	public Page<T> findPage(Page<T> page) {
		return baseDao.findPage(page);
	}
	
	/**
	 * 分页查询
	 */
	public Page<T> findPage(Page<T> page,Map<String,Object> paramsMap) {
		return baseDao.findPage(page, paramsMap);
	}

	/**
	 * 具体的通过继承实现
	 */
	public void save(T entity) {
		
		baseDao.save(entity);
	}
	
	/**
	 * 保存实体数组
	 * @param list
	 * @return
	 */
	public void save(List<T> list) {
		
		baseDao.save(list);
	}

	/**
	 * 本方法不做处理
	 */
	public void update(T entity) {
		baseDao.update(entity);
	}
	
	/**
	 * 查询全部，如果按照顺序查询，可以在dao层中修改查询顺序
	 */
	public List<T> findAll() {
		
		return baseDao.getAll();
	}
	
	/**
	 * 通过条件查询全部
	 */
	public List<T> findList(Map<String,Object> paramsMap) {
		
		return baseDao.findList(paramsMap);
	}
	
	/**
	 * 通过Id获取实体
	 */
	public T get(String id) {
		
		return baseDao.get(id);
	}

	public K getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(K baseDao) {
		this.baseDao = baseDao;
	}
	
	

}
