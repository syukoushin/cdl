package com.ibm.cdl.datamap.dao;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ibm.cdl.datamap.pojo.SubDataMap;
import com.ibm.core.orm.Page;
import com.ibm.core.orm.hibernate.SessionFactoryDao;

@Repository("subDataMapDao")
public class SubDataMapDao extends SessionFactoryDao<SubDataMap, String> {

	
	 /**
     * 跳转到数据字典子字典管理列表
     * @param res 
     * @param id    DataMap.id
     * @return
     */
	public Page<SubDataMap> list(Page<SubDataMap> res, SubDataMap subDataMap) {

		String hql = "from SubDataMap where 1 = 1";
		if(StringUtils.isNotEmpty(subDataMap.getParent())){//根据父id查询
			hql += " and parent = '" + subDataMap.getParent() + "' ";
		}
		if(StringUtils.isNotEmpty(subDataMap.getCode())){//根据父id查询
			hql += " and code = '" + subDataMap.getCode() + "' ";
		}
		return findPage(res,hql);
	}

}
