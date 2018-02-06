package com.ibm.cdl.manage.service.impl;

import com.ibm.cdl.manage.dao.BusinessLicenseDao;
import com.ibm.cdl.manage.pojo.BusinessLicense;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.cdl.manage.service.BusinessLicenseService;
import com.ibm.cdl.manage.service.UserService;
import com.ibm.core.orm.Page;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("businessLicenseService")
public class BusinessLicenseServiceImpl implements BusinessLicenseService {
	

	@Autowired
	private BusinessLicenseDao businessLicenseDao;

	@Autowired
	private  UserService userService;

	/**
	 * 分页获取历史记录（客户端用）
	 * @param entity
	 * @param page
	 * @return
	 */
	public Page<BusinessLicense> findPageForClient(BusinessLicense entity, Page<BusinessLicense> page) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();

		hql.append("select l.NAME as name,");
		hql.append (" l.ID as id, ");
		hql.append (" l.REG_NUMBER as regNumber, ");
		hql.append (" l.ADDRESS as address,");
		hql.append (" l.CREDIT_CODE as creditCode,");
		hql.append (" l.INCORPORATOR as incorporator,");
		hql.append (" l.END_DATE as endDate,");
		hql.append (" l.CREATE_USER as createUser,");
		hql.append (" l.CREATE_TIME as createTime,");
		hql.append ("  a.ID AS attId ");
		hql.append (" from ");
		
		StringBuilder where = new StringBuilder();
		where.append(" business_license l ").append(" left join attachment a on a.BUSINESS_ID = l.ID where 1 = 1 ");;

		// 信用代码不为空的场合
		if( entity.getCreditCode() != null && !"".equals(entity.getCreditCode())){
			where.append(" and l.CREDIT_CODE= :creditCode");
			pMap.put("creditCode", entity.getCreditCode());
		}
		// 单位名称不为空的场合
		if(entity.getName() != null && !"".equals(entity.getName())){
			where.append(" and l.NAME like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		// 注册码不为空的场合
		if(entity.getRegNumber() != null && !"".equals(entity.getRegNumber())){
			where.append(" and l.REG_NUMBER =:regNumber");
			pMap.put("regNumber", entity.getRegNumber());
		}
		// 法人不为空的场合
		if(entity.getIncorporator() != null && !"".equals(entity.getIncorporator())){
			where.append( " and l.INCORPORATOR  like :incorporator");
			pMap.put("incorporator","%"+entity.getIncorporator()+"%");
		}
		// 创建人
		if(entity.getCreateUser() != null && !"".equals(entity.getCreateUser())){
			where.append(" and l.CREATE_USER = :createUser");
			pMap.put("createUser",entity.getCreateUser());
		}

		StringBuilder order = new StringBuilder();
		order.append(" order by l.CREATE_TIME desc");
	

		// 进行分页查询
		String querySqlString = hql.append(where).append(order).append(" LIMIT ")
				.append((page.getPageNo()-1)*page.getPageSize())
				.append(",")
				.append(page.getPageSize())
				.toString();
		Query queryList = businessLicenseDao.getSession().createSQLQuery(querySqlString);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			queryList.setParameter(p.getKey(), p.getValue());
		}
		queryList.setResultTransformer(Transformers.aliasToBean(BusinessLicense.class));
		List<User> result = queryList.list();
		int start = Page.getStartOfPage(page.getPageNo(), page.getPageSize());
		Page.setPageValue(page, start, 0, result);
		return page;
	}

	/**
	 * 分页获取记录 （后台用）
	 * @param entity
	 * @param page
	 * @param currentUser
	 * @return
	 */
	public Page<BusinessLicense> findPage(BusinessLicense entity, Page<BusinessLicense> page,User currentUser) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();

		hql.append("select l.NAME as name,");
		hql.append ("l.ID as id, ");
		hql.append ("l.REG_NUMBER as regNumber, ");
		hql.append ("l.ADDRESS as address,");
		hql.append ("l.CREDIT_CODE as creditCode,");
		hql.append ("l.INCORPORATOR as incorporator,");
		hql.append ("l.END_DATE as endDate,");
		hql.append ("l.CREATE_USER as createUser,");
		hql.append ("l.CREATE_TIME as createTime");
		hql.append (" from ");
		
		StringBuilder where = new StringBuilder();
		where.append(" business_license l left join user u on l.CREATE_USER = u.USER_CODE  where 1 = 1 ");

		// 信用代码不为空的场合
		if( entity.getCreditCode() != null && !"".equals(entity.getCreditCode())){
			where.append(" and l.CREDIT_CODE= :creditCode");
			pMap.put("creditCode", entity.getCreditCode());
		}
		// 单位名称不为空的场合
		if(entity.getName() != null && !"".equals(entity.getName())){
			where.append(" and l.NAME like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		// 注册码不为空的场合
		if(entity.getRegNumber() != null && !"".equals(entity.getRegNumber())){
			where.append(" and l.REG_NUMBER =:regNumber");
			pMap.put("regNumber", entity.getRegNumber());
		}
		// 法人不为空的场合
		if(entity.getIncorporator() != null && !"".equals(entity.getIncorporator())){
			where.append( " and l.INCORPORATOR  like :incorporator");
			pMap.put("incorporator","%"+entity.getIncorporator()+"%");
		}
		// 创建人
		if(entity.getCreateUser() != null && !"".equals(entity.getCreateUser())){
			where.append(" and l.CREATE_USER = :createUser");
			pMap.put("createUser",entity.getCreateUser());
		}
		
//		// 判断创建人
//		if(Constants.USER_ADMIN.equals(currentUser.getJobLevel())){
//
//		} else if(Constants.USER_SECOND.equals(currentUser.getJobLevel())){
//			where.append(" and u.GROUP_ID =:groupId ");
//			pMap.put("groupId", currentUser.getGroupId());
//		} else if(Constants.USER_THIRD.equals(currentUser.getJobLevel())){
//			where.append(" and u.GROUP_ID =:groupId ");
//			pMap.put("groupId", currentUser.getGroupId());
//			where.append(" and (u.CREATE_BY =:createBy or u.USER_CODE = :createBy) ");
//			pMap.put("createBy", currentUser.getUserCode());
//		}
//
		StringBuilder order = new StringBuilder();
		order.append(" order by l.CREATE_TIME desc");
	
		StringBuilder countSql = new StringBuilder("SELECT COUNT(1) from");
		countSql.append(where.toString());
		
		// 计算总条数
		Query query = businessLicenseDao.getSession().createSQLQuery(countSql.toString());
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			query.setParameter(p.getKey(), p.getValue());
		}
		Integer totalCount = Integer.valueOf(query.uniqueResult().toString());
		
		// 进行分页查询
		String querySqlString = hql.append(where).append(order).append(" LIMIT ")
				.append((page.getPageNo()-1)*page.getPageSize())
				.append(",")
				.append(page.getPageSize())
				.toString();
		Query queryList = businessLicenseDao.getSession().createSQLQuery(querySqlString);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			queryList.setParameter(p.getKey(), p.getValue());
		}
		queryList.setResultTransformer(Transformers.aliasToBean(BusinessLicense.class));
		List<User> result = queryList.list();
		int start = Page.getStartOfPage(page.getPageNo(), page.getPageSize());
		Page.setPageValue(page, start, totalCount, result);
		return page;
	}

	/**
	 * 添加 实体
	 * @param entity
	 */
	public void addEntity(BusinessLicense entity) {
		businessLicenseDao.save(entity);
	}

	/**
	 * 根据id删除 实体
	 * @param ids
	 */
	public void delEntity(String ids) {
		businessLicenseDao.delete(ids);
	}

	/**
	 * 根据id查询 详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BusinessLicense findEntityById(String id) throws Exception {
		BusinessLicense temp = businessLicenseDao.findUniqueBy("id", id);
		User u = userService.queryUserByLoginName(temp.getCreateUser());
		BusinessLicense re = new BusinessLicense();
		BeanUtils.copyProperties(re, temp);
		re.setCreateUser(u.getUserName());
		return re;
	}

	/**
	 * 根据条件获取列表 （导出用）
	 * @param entity
	 * @param currentUser
	 * @return
	 */
	public List<BusinessLicense> findListBy(BusinessLicense entity,User currentUser) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();

		hql.append("select l.NAME as name,");
		hql.append ("l.ID as id, ");
		hql.append ("l.REG_NUMBER as regNumber, ");
		hql.append ("l.ADDRESS as address,");
		hql.append ("l.CREDIT_CODE as creditCode,");
		hql.append ("l.INCORPORATOR as incorporator,");
		hql.append ("l.END_DATE as endDate,");
		hql.append ("l.CREATE_USER as createUser,");
		hql.append ("l.CREATE_TIME as createTime");
		hql.append (" from ");

		StringBuilder where = new StringBuilder();
		where.append(" business_license l left join user u on l.CREATE_USER = u.USER_CODE  where 1 = 1 ");

		// 信用代码不为空的场合
		if( entity.getCreditCode() != null && !"".equals(entity.getCreditCode())){
			where.append(" and l.CREDIT_CODE= :creditCode");
			pMap.put("creditCode", entity.getCreditCode());
		}
		// 单位名称不为空的场合
		if(entity.getName() != null && !"".equals(entity.getName())){
			where.append(" and l.NAME like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		// 注册码不为空的场合
		if(entity.getRegNumber() != null && !"".equals(entity.getRegNumber())){
			where.append(" and l.REG_NUMBER =:regNumber");
			pMap.put("regNumber", entity.getRegNumber());
		}
		// 法人不为空的场合
		if(entity.getIncorporator() != null && !"".equals(entity.getIncorporator())){
			where.append( " and l.INCORPORATOR  like :incorporator");
			pMap.put("incorporator","%"+entity.getIncorporator()+"%");
		}
		// 创建人
		if(entity.getCreateUser() != null && !"".equals(entity.getCreateUser())){
			where.append(" and l.CREATE_USER = :createUser");
			pMap.put("createUser",entity.getCreateUser());
		}

		// 判断创建人
//		if(Constants.USER_ADMIN.equals(currentUser.getJobLevel())){
//
//		} else if(Constants.USER_SECOND.equals(currentUser.getJobLevel())){
//			where.append(" and u.GROUP_ID =:groupId ");
//			pMap.put("groupId", currentUser.getGroupId());
//		} else if(Constants.USER_THIRD.equals(currentUser.getJobLevel())){
//			where.append(" and u.GROUP_ID =:groupId ");
//			pMap.put("groupId", currentUser.getGroupId());
//			where.append(" and (u.CREATE_BY =:createBy or u.USER_CODE = :createBy) ");
//			pMap.put("createBy", currentUser.getUserCode());
//		}
		
		StringBuilder order = new StringBuilder();
		order.append(" order by l.CREATE_TIME desc");
		
		Query query = businessLicenseDao.createSqlQuery(hql.append(where).append(order).toString(), pMap);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			query.setParameter(p.getKey(), p.getValue());
		}
		query.setResultTransformer(Transformers.aliasToBean(BusinessLicense.class));
		return query.list();
	}

}
