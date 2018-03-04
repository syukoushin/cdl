package com.ibm.cdl.manage.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.manage.dao.PartGroupDao;
import com.ibm.cdl.manage.pojo.PartGroup;
import com.ibm.cdl.manage.service.PartGroupService;
import com.ibm.cdl.manage.service.UserService;
import com.ibm.core.orm.Page;

@Service("partGroupService")
public class PartGroupServiceImpl implements PartGroupService {
	
	
	@Autowired
	private PartGroupDao partGroupDao;
	@Autowired
	private UserService userService;
	
	/**
	 * 查询分页
	 */
	public Page<PartGroup> findPage(PartGroup entity, Page<PartGroup> page) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();
		hql.append("from PartGroup where 1=1 ");
		
		// 姓名
		if(entity.getName() != null && !"".equals(entity.getName())){
			hql.append(" and name like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		
		hql.append(" order by createTime desc");
		return partGroupDao.findPage(page, hql.toString(),pMap);
	}

	
	/**
	 * 根据id查询
	 */
	public PartGroup queryGroupById(String id) {
		Map<String,Object> p = new HashMap<String, Object>();
		p.put("id", id);
		return partGroupDao.findUnique("from PartGroup where ID = :id",p);
	}

	/**
	 * 判断用户是否存在
	 */
	public boolean checkExistGroupCode(String name) {
		List<PartGroup> uList = partGroupDao.findBy("name", name);
		if(uList.size() > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public List<PartGroup> findAll() {
		return partGroupDao.getAll();
	}
	
	public void updateEntity(PartGroup entity) {
		partGroupDao.update(entity);
	}
	
	public void saveEntity(File attachment,String name,PartGroup entity){
//		Attachment attach = attachmentService.saveAttachment(iconFile, iconFileFileName, iconFileContentType, "topic");
		partGroupDao.save(entity);
	}

	public void delEntity(String ids) {
		// 删除公司的同时，级联删除公司下的人员
		userService.delByPartGroupId(ids);
		partGroupDao.delete(ids);
	}

	public Object get(String id) {
		return partGroupDao.get(id);
	}


}
