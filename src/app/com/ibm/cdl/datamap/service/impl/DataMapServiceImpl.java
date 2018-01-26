package com.ibm.cdl.datamap.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.cdl.datamap.dao.DataMapDao;
import com.ibm.cdl.datamap.pojo.DataMap;
import com.ibm.cdl.datamap.service.DataMapService;
import com.ibm.core.orm.Page;
@Service("dataMapService")
public class DataMapServiceImpl implements DataMapService {
	@Autowired
	private DataMapDao dataMapDao;

	/**
	 * 添加数据字典
	 */
	public void addDataMap(DataMap dataMap){
		dataMap.setCreateDate(new Date());
		dataMapDao.save(dataMap);
	}
	/**
	 * 跳转数据字典管理列表
	 * @return
	 */
	public Page<DataMap> findAllDataMap(Page<DataMap> res){
		return dataMapDao.findAllDataMap(res);
	}
	/**
	 * 通过id获取对象
	 */
	public DataMap findDataMapById(String id){
		return dataMapDao.get(id);
	}
	/**
	 * 修改数据字典
	 */
	public void updateDataMap(DataMap dataMap){
		DataMap dp = dataMapDao.get(dataMap.getId());
		dp.setCode(dataMap.getCode());
		dp.setName(dataMap.getName());
		dp.setRemark(dataMap.getRemark());
		dataMapDao.update(dp);
	}
	/**
	 * 删除字典
	 */
	public void deleteDataMapById(String ids){
		String[] values = ids.split(",");
		dataMapDao.delete(values);
	}
	/**
	 * 从内存中获取数组字典对象
	 * @return
	 */
	public Map getDictionary() {
		Map result = new HashMap();
		Map<String,DataMap> map = dataMapDao.queryMap();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			DataMap temp = new DataMap();
			BeanUtils.copyProperties(map.get(key), temp);
			result.put(key, temp);
		}
		return result;
	}
	public void setDataMapDao(DataMapDao dataMapDao) {
		this.dataMapDao = dataMapDao;
	}

}
