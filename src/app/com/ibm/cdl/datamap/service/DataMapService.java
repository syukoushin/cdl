package com.ibm.cdl.datamap.service;

import java.util.Map;

import com.ibm.cdl.datamap.pojo.DataMap;
import com.ibm.core.orm.Page;

/**
 * @author kelvin
 */
public interface DataMapService {
	/**
	 * 添加数据字典
	 */
	public void addDataMap(DataMap dataMap);

	public void updateDataMap(DataMap dataMap);

	public void deleteDataMapById(String id);
	

	public DataMap findDataMapById(String id);

	/**
	 * 跳转数据字典管理列表
	 * @return
	 */
	public Page<DataMap> findAllDataMap(Page<DataMap> res);

	/**
	 * 从内存中获取数组字典对象
	 * @return
	 */
	public Map getDictionary();
	
}
