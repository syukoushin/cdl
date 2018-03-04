package com.ibm.cdl.manage.service;

import java.io.File;
import java.util.List;

import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.manage.pojo.PartGroup;
import com.ibm.core.orm.Page;

public interface PartGroupService {
	
	
	/**
     * 查找实体列表
     */
    Page<PartGroup> findPage(PartGroup entity, Page<PartGroup> page);
	
    /**
	 * 更新实体
	 * @param moaGroup
	 */
	public void updateEntity(PartGroup moaGroup) ;
    
	
	/**
	 * 判断用户是否存在
	 * @param userCode
	 * @return
	 */
	public boolean checkExistGroupCode(String userCode);
	

	/**
	 * 根据主键查找MOAUSER
	 * @param id
	 * @return
	 */
	public PartGroup queryGroupById(String id);
	
	
	/**
	 * 根据主键删除
	 * @param ids
	 */
	public void delEntity(String ids);
	
	
	public Object get(String id);
	
	
	/**
	 * 添加实体
	 * @param moaGroup 添加的用户信息
	 */
	public void saveEntity(File att,String name,PartGroup moaGroup);

	/**
	 * 查询所有实体
	 * @return
	 */
	public List<PartGroup> findAll();
	
}
