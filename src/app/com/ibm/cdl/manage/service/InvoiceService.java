package com.ibm.cdl.manage.service;

import com.ibm.cdl.manage.pojo.Invoice;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.core.orm.Page;

import java.util.List;

public interface InvoiceService {


    /**
     * 保存发票信息
     * @param entity
     * @return
     */
    public String save(Invoice entity);

	/**
     * 查找实体列表 （后台）
     */
    Page<Invoice> findPage(Invoice entity, Page<Invoice> page,User user);

    /**
     * 分页查询历史记录 （客户端）
     * @param entity
     * @param page
     * @return
     */
    Page<Invoice> findPageForClient(Invoice entity,Page<Invoice> page);
    
    /**
     * 获取列表
     * @param entity
     * @return
     */
    List<Invoice> findListBy(Invoice entity,User user);

    
    /**
     * 添加实体
     */
    void addEntity(Invoice image);

    /**
     * 删除实体
     */
    void delEntity(String ids);


    /**
     * 查找实体
     * @throws Exception 
     */
    Invoice findEntityById(String id) throws Exception;
    
}
