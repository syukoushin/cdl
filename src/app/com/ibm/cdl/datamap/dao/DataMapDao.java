package com.ibm.cdl.datamap.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibm.cdl.datamap.pojo.DataMap;
import com.ibm.core.orm.Page;
import com.ibm.core.orm.hibernate.SessionFactoryDao;
import com.ibm.core.util.ListUtils;

@Repository("dataMapDao")
public class DataMapDao extends SessionFactoryDao<DataMap, String> {

	public Page<DataMap> findAllDataMap(Page<DataMap> res){
		res.setOrderBy("createDate");
		res.setOrder("desc");
		return this.getAll(res);
	}
	
	public Map<String,DataMap> queryMap() {
		
		Map<String,DataMap> resultMap = new HashMap<String,DataMap>();
		String hql = "select new Map(d.code as code,d as value) from DataMap d";
		List<Map> resultList = this.getSession().createQuery(hql).list();
		if (ListUtils.isEmpty(resultList)) return resultMap;
		for (Map temp : resultList) {
			resultMap.put((String)temp.get("code"), (DataMap)temp.get("value"));
		}
		return resultMap;
		
	}
	
}
