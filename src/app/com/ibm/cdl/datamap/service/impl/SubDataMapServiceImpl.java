package com.ibm.cdl.datamap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.cdl.datamap.dao.SubDataMapDao;
import com.ibm.cdl.datamap.pojo.SubDataMap;
import com.ibm.cdl.datamap.service.SubDataMapService;
import com.ibm.core.orm.Page;

@Service("subDataMapService")
public class SubDataMapServiceImpl implements SubDataMapService {
	@Autowired
	private SubDataMapDao subDataMapDao;
	 /**
     * 跳转到数据字典子字典管理列表
     * @param res 
     * @param id    DataMap.id
     * @return
     */
	public Page<SubDataMap> list(Page<SubDataMap> res, SubDataMap subDataMap) {
		return subDataMapDao.list(res,subDataMap);
	}
	/**
	 * 添加
	 */
	public void save(SubDataMap subDataMap) {
		subDataMapDao.save(subDataMap);
	}
	/**
	 * 通过id获取对象
	 */
	public SubDataMap findSubDataMapById(String id) {
		return subDataMapDao.get(id);
	}
	/**
	 * 修改
	 */
	public void update(SubDataMap subDataMap) {
		subDataMapDao.update(subDataMap);
	}
	/**
	 * 删除
	 */
	public void delete(String ids) {
		
		String[] values = ids.split(",");
			
		subDataMapDao.delete(values);
	}
	
	public SubDataMap findSubDataMapByCode(String code){
		List<SubDataMap> subList = subDataMapDao.findBy("code", code);
		if(subList != null && subList.size() > 0){
			return subList.get(0);
		} else {
			return null;
		}
	}
}
