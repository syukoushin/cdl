package com.ibm.cdl.datamap.service;

import com.ibm.cdl.datamap.pojo.SubDataMap;
import com.ibm.core.orm.Page;

import net.sf.ehcache.util.FindBugsSuppressWarnings;

/**
 * @author kelvin
 */
public interface SubDataMapService {
    /**
     * 跳转到数据字典子字典管理列表
     * @param res 
     * @param id    DataMap.id
     * @return
     */
	public Page<SubDataMap> list(Page<SubDataMap> res, SubDataMap subDataMap);

	/**
	 * 添加
	 * @param subDataMap 数据字典字字典对象
	 */
	public void save(SubDataMap subDataMap);

	public SubDataMap findSubDataMapById(String id);
	
	public SubDataMap findSubDataMapByCode(String code);

	public void update(SubDataMap subDataMap);

	public void delete(String ids);
	
}
