package com.ibm.cdl.manage.service;

import com.ibm.cdl.manage.pojo.IdCard;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.core.orm.Page;

import java.util.List;

public interface IdCardService {

	/**
	 * 查询历史分页（客户端用）
	 * @param entity
	 * @return
	 */
	Page<IdCard> findPageForClient(IdCard entity,Page<IdCard> page);

	/**
	 * 查找实体列表
	 */
	Page<IdCard> findPage(IdCard entity, Page<IdCard> page,User user);

	/**
	 * 获取列表
	 * @param entity
	 * @return
	 */
	List<IdCard> findListBy(IdCard entity,User user);


	/**
	 * 添加实体
	 */
	void addEntity(IdCard entity);

	/**
	 * 添加或者更新实体
	 * @param entity
	 */
	void save(IdCard entity);

	/**
	 * 删除实体
	 */
	void delEntity(String ids);


	/**
	 * 查找实体
	 * @throws Exception
	 */
	IdCard findEntityById(String id) throws Exception;
}
