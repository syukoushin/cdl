package com.ibm.cdl.manage.dao;

import com.ibm.cdl.manage.pojo.IdCard;
import com.ibm.core.orm.hibernate.SessionFactoryDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("idCardDao")
public class IdCardDao extends SessionFactoryDao<IdCard, String> {
	

	/**
	 * 根据全部获取用户信息
	 */
	public List<IdCard> getAll(){
		return find(" from IdCard order by createTime desc");
	}
	

}
