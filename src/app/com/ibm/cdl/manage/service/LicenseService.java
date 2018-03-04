package com.ibm.cdl.manage.service;

import com.ibm.cdl.manage.pojo.License;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.core.orm.Page;

import java.util.List;

public interface LicenseService {

    /**
     * 保存发票信息，保存之前先调用与dms接口
     * @param entity
     * @return
     */
    public String save(License entity);
	
	/**
     * 查找实体列表
     */
    Page<License> findPage(License entity, Page<License> page,User user);
    
    /**
     * 获取列表
     * @param entity
     * @return
     */
    List<License> findListBy(License entity,User currentUser);

    /**
     * 客户端分页
     * @param entity
     * @param page
     * @return
     */
    Page<License> findPageForClient(License entity, Page<License> page);
    
    /**
     * 添加实体
     */
    void addEntity(License image);

    /**
     * 删除实体
     */
    void delEntity(String ids);


    /**
     * 查找实体
     */
    License findEntityById(String id) throws Exception;
    
}
