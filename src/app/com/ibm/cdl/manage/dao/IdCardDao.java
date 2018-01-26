package com.ibm.cdl.manage.dao;

import com.ibm.cdl.manage.pojo.IdCard;
import com.ibm.core.orm.Page;
import com.ibm.core.orm.hibernate.SessionFactoryDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("idCardDao")
public class IdCardDao extends SessionFactoryDao<IdCard, String> {
	
	/**
	 * 查询列表
	 */
	public Page<IdCard> queryIcCardPage(Page<IdCard> page , IdCard entity) {

		String hql = "from IdCard g where 1=1 ";
		if(StringUtils.isNotEmpty(entity.getName())){
			hql += " and g.name like '%"+entity.getName()+"%'";
		}
		hql += "order by g.createTime desc";
		return findPage(page,hql);
	}
	

	/**
	 * 根据全部获取用户信息
	 */
	public List<IdCard> getAll(){
		return find(" from IdCard order by createTime desc");
	}
	
	
	/**
	 * 删除用户表所有数据
	 */
	public void deleteGroup() {
		String hql = "delete from IdCard";
		batchExecute(hql);
	}

}
