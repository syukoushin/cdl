package com.ibm.cdl.manage.dao;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.ibm.cdl.manage.pojo.PartGroup;
import com.ibm.core.orm.Page;
import com.ibm.core.orm.hibernate.SessionFactoryDao;

@Repository("groupDao")
public class PartGroupDao extends SessionFactoryDao<PartGroup, String> {
	
	/**
	 * 查询列表
	 */
	public Page<PartGroup> queryMoaGroupList(Page<PartGroup> page , PartGroup entity) {
		String hql = "from PartGroup g where 1=1 ";
		if(StringUtils.isNotEmpty(entity.getName())){
			hql += " and g.name like '%"+entity.getName()+"%'";
		}
		hql += "order by g.createTime desc";
		return findPage(page,hql);
	}
	

	/**
	 * 根据全部获取用户信息
	 */
	public List<PartGroup> getAll(){
		return find(" from PartGroup order by createTime desc");
	}
	
	
	/**
	 * 删除用户表所有数据
	 */
	public void deleteGroup() {
		String hql = "delete from Group";
		batchExecute(hql);
	}
	
	
	/**
	 * 根据 属性 查询 仅限 【等于】 的操作
	 * @param param
	 * @return
	 */
	public PartGroup queryGroupBy(Map<String,Object> param) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PartGroup.class);
		
		for(Map.Entry<String, Object> obj: param.entrySet()){
			String key = obj.getKey();
			Object o = obj.getValue();
			dCriteria.add(Restrictions.eq(key, o));
		}
		List list = dCriteria.getExecutableCriteria(getSession()).list();
		if (list == null || list.size() == 0) return null;
		return (PartGroup) list.get(0);
	}
	
}
