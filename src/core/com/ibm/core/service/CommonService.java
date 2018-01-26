package com.ibm.core.service;

import java.util.List;
import java.util.Map;

import com.ibm.core.orm.Page;

/**
 * 定义服务层的标准命名规则
 * @author menghai
 *
 */
public interface CommonService<T> {

	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(T entity);
	
	/**
	 * 保存实体数组
	 * @param list
	 * @return
	 */
	public void save(List<T> list);
	
	/**
	 * 更新实体
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * 删除实体
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 分页查询
	 */
	public Page<T> findPage(Page<T> page);
	
	/**
	 * 分页查询
	 */
	public Page<T> findPage(Page<T> page,Map<String,Object> paramsMap);
	
	/**
	 * 通过条件查询全部
	 */
	public List<T> findList(Map<String,Object> paramsMap);
	
	/**
	 * 查询全部
	 */
	public List<T> findAll();
	
	/**
	 * 通过Id获取实体
	 */
	public T get(String id);
	
}
