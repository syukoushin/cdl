package com.ibm.cdl.manage.service;

import com.ibm.cdl.manage.pojo.BusinessLicense;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.core.orm.Page;

import java.util.List;

public interface BusinessLicenseService {

	/**
	 * 查询历史分页（客户端用）
	 * @param entity
	 * @return
	 */
	Page<BusinessLicense> findPageForClient(BusinessLicense entity, Page<BusinessLicense> page);

	/**
	 * 查找实体列表
	 */
	Page<BusinessLicense> findPage(BusinessLicense entity, Page<BusinessLicense> page, User user);

	/**
	 * 获取列表
	 * @param entity
	 * @return
	 */
	List<BusinessLicense> findListBy(BusinessLicense entity, User user);


	/**
	 * 添加实体
	 */
	void addEntity(BusinessLicense entity);

	/**
	 * 添加或更新实体
	 * @param entity
	 */
	void save(BusinessLicense entity);

	/**
	 * 删除实体
	 */
	void delEntity(String ids);


	/**
	 * 查找实体
	 * @throws Exception
	 */
	BusinessLicense findEntityById(String id) throws Exception;
}
