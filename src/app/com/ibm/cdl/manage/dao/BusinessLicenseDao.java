package com.ibm.cdl.manage.dao;

import com.ibm.cdl.manage.pojo.BusinessLicense;
import com.ibm.core.orm.Page;
import com.ibm.core.orm.hibernate.SessionFactoryDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("businessLicenseDao")
public class BusinessLicenseDao extends SessionFactoryDao<BusinessLicense, String> {
	
	/**
	 * 查询列表
	 */
	public Page<BusinessLicense> queryIcCardPage(Page<BusinessLicense> page , BusinessLicense entity) {

		String hql = "from BusinessLicense g where 1=1 ";

		// 根据单位名称模糊查询
		if(StringUtils.isNotEmpty(entity.getName())){
			hql += " and g.name like '%"+entity.getName()+"%'";
		}

		// 根据注册号查询
		if(StringUtils.isNotEmpty(entity.getRegNumber())){
			hql += " and g.REG_NUMBER =‘" +entity.getRegNumber()+ "'";
		}

		// 根据社会信用代码查询
		if(StringUtils.isNotEmpty(entity.getCreditCode())){
			hql += " and g.CREDIT_CODE ='" +entity.getCreditCode()+ "'";
		}

		// 	根据	法人模糊查询
		if(StringUtils.isNotEmpty(entity.getIncorporator())){
			hql += " and g.INCORPORATOR LIKE '%" +entity.getIncorporator()+"%'";
 		}
		hql += "order by g.createTime desc";
		return findPage(page,hql);
	}
	

	/**
	 * 根据全部列表信息
	 */
	public List<BusinessLicense> getAll(){
		return find(" from BusinessLicense order by createTime desc");
	}
	
}
