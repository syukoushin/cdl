package com.ibm.cdl.manage.service.impl;

import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.manage.dao.LicenseDao;
import com.ibm.cdl.manage.pojo.License;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.cdl.manage.service.LicenseService;
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

@Service("licenseService")
public class LicenseServiceImpl implements LicenseService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private LicenseDao licenseDao;

	public Page<License> findPageForClient(License entity, Page<License> page) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();

		hql.append("select l.NAME as name, ");
		hql.append ("l.ID as id, ");
		hql.append ("l.CARD_NO as cardNo, ");
		hql.append ("l.ADDRESS as address, ");
		hql.append ("l.CAR_TYPE as carType, ");
		hql.append ("l.BAND_NO as bandNo, ");
		hql.append ("l.CAR_NO as carNo, ");
		hql.append ("l.ENGIN_NO as enginNo, ");
		hql.append ("l.REGIST_DATE as registDate, ");
		hql.append ("l.PASS_DATE as passDate, ");
		hql.append ("l.CREATE_TIME as createTime, ");
		hql.append (" a.ID AS attId");
		hql.append (" from ");
		
		StringBuilder where = new StringBuilder();
		where.append(" license l ").append(" left join attachment a on a.BUSINESS_ID = l.ID where 1 = 1 ");
		// cardno不为空的场合
		if( entity.getCardNo() != null && !"".equals(entity.getCardNo())){
			where.append(" and l.CARD_NO= :cardNo");
			pMap.put("cardNo", entity.getCardNo());
		}
		// 姓名
		if(entity.getName() != null && !"".equals(entity.getName())){
			where.append(" and l.NAME like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		// 发动机号
		if(entity.getEnginNo() != null && !"".equals(entity.getEnginNo())){
			where.append(" and l.ENGIN_NO =:enginNo");
			pMap.put("enginNo", entity.getEnginNo());
		}
		// 车辆识别代码
		if(entity.getCarNo() != null && !"".equals(entity.getCarNo())){
			where.append(" and l.CAR_NO =:carNo");
			pMap.put("carNo", entity.getCarNo());
		}
		// 创建人
		if(entity.getCreateUser() != null && !"".equals(entity.getCreateUser())){
			where.append(" and l.CREATE_USER =:createUser");
			pMap.put("createUser", entity.getCreateUser());
		}

		StringBuilder order = new StringBuilder();
		order.append(" order by l.CREATE_TIME desc");

		// 进行分页查询
		String querySqlString = hql.append(where).append(order).append(" LIMIT ")
				.append((page.getPageNo()-1)*page.getPageSize())
				.append(",")
				.append(page.getPageSize())
				.toString();
		Query queryList = licenseDao.getSession().createSQLQuery(querySqlString);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			queryList.setParameter(p.getKey(), p.getValue());
		}
		queryList.setResultTransformer(Transformers.aliasToBean(License.class));
		List<User> result = queryList.list();
		int start = Page.getStartOfPage(page.getPageNo(), page.getPageSize());
		Page.setPageValue(page, start, 0, result);
		return page;
	}

	/**
	 * 分页查询 列表 （后台）
	 * @param entity
	 * @param page
	 * @param currentUser
	 * @return
	 */
	public Page<License> findPage(License entity, Page<License> page,User currentUser) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();

		hql.append("select l.NAME as name,");
		hql.append ("l.ID as id, ");
		hql.append ("l.CARD_NO as cardNo, ");
		hql.append ("l.ADDRESS as address,");
		hql.append ("l.CAR_TYPE as carType,");
		hql.append ("l.BAND_NO as bandNo,");
		hql.append ("l.CAR_NO as carNo,");
		hql.append ("l.ENGIN_NO as enginNo,");
		hql.append ("l.REGIST_DATE as registDate,");
		hql.append ("l.PASS_DATE as passDate,");
		hql.append ("l.CREATE_TIME as createTime");
		hql.append (" from ");
		
		StringBuilder where = new StringBuilder();
		where.append(" license l left join user u on l.CREATE_USER = u.USER_CODE  where 1 = 1 ");
		// cardno不为空的场合
		if( entity.getCardNo() != null && !"".equals(entity.getCardNo())){
			where.append(" and l.CARD_NO= :cardNo");
			pMap.put("cardNo", entity.getCardNo());
		}
		// 姓名
		if(entity.getName() != null && !"".equals(entity.getName())){
			where.append(" and l.NAME like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		// 发动机号
		if(entity.getEnginNo() != null && !"".equals(entity.getEnginNo())){
			where.append(" and l.ENGIN_NO =:enginNo");
			pMap.put("enginNo", entity.getEnginNo());
		}
		// 车辆识别代码
		if(entity.getCarNo() != null && !"".equals(entity.getCarNo())){
			where.append(" and l.CAR_NO =:carNo");
			pMap.put("carNo", entity.getCarNo());
		}
		
		// 判断创建人
		if(Constants.USER_ADMIN.equals(currentUser.getJobLevel())){
			
		} else if(Constants.USER_SECOND.equals(currentUser.getJobLevel())){
			where.append(" and u.GROUP_ID =:groupId ");
			pMap.put("groupId", currentUser.getGroupId());
		} else if(Constants.USER_THIRD.equals(currentUser.getJobLevel())){
			where.append(" and u.GROUP_ID =:groupId ");
			pMap.put("groupId", currentUser.getGroupId());
			where.append(" and (u.CREATE_BY =:createBy or u.USER_CODE = :createBy) ");
			pMap.put("createBy", currentUser.getUserCode());
		}
		
		StringBuilder order = new StringBuilder();
		order.append(" order by l.CREATE_TIME desc");
	
		StringBuilder countSql = new StringBuilder("SELECT COUNT(1) from");
		countSql.append(where.toString());
		
		// 计算总条数
		Query query = licenseDao.getSession().createSQLQuery(countSql.toString());
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
		Query queryList = licenseDao.getSession().createSQLQuery(querySqlString);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			queryList.setParameter(p.getKey(), p.getValue());
		}
		queryList.setResultTransformer(Transformers.aliasToBean(License.class));
		List<User> result = queryList.list();
		int start = Page.getStartOfPage(page.getPageNo(), page.getPageSize());
		Page.setPageValue(page, start, totalCount, result);
		return page;
	}
	
	public void addEntity(License entity) {
		licenseDao.save(entity);
	}

	public void delEntity(String ids) {
		licenseDao.delete(ids);
	}

	public License findEntityById(String id) throws Exception {
		License temp = licenseDao.findUniqueBy("id", id);
		User u = userService.queryUserByLoginName(temp.getCreateUser());
		License re = new License();
		BeanUtils.copyProperties(re, temp);
		re.setCreateUser(u.getUserName());
		return re;
	}

	/**
	 * 根据条件 查询列表 （导出用）
	 * @param entity
	 * @param currentUser
	 * @return
	 */
	public List<License> findListBy(License entity,User currentUser) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();

		hql.append("select l.NAME as name,");
		hql.append ("l.CARD_NO as cardNo, ");
		hql.append ("l.ADDRESS as address,");
		hql.append ("l.CAR_TYPE as carType,");
		hql.append ("l.BAND_NO as bandNo,");
		hql.append ("l.CAR_NO as carNo,");
		hql.append ("l.ENGIN_NO as enginNo,");
		hql.append ("l.REGIST_DATE as registDate,");
		hql.append ("l.PASS_DATE as passDate,");
		hql.append ("l.CREATE_TIME as createTime");
		hql.append (" from ");
		
		StringBuilder where = new StringBuilder();
		where.append(" license l left join user u on l.CREATE_USER = u.USER_CODE  where 1 = 1 ");
		// cardno不为空的场合
		if( entity.getCardNo() != null && !"".equals(entity.getCardNo())){
			where.append(" and l.CARD_NO= :cardNo");
			pMap.put("cardNo", entity.getCardNo());
		}
		// 姓名
		if(entity.getName() != null && !"".equals(entity.getName())){
			where.append(" and l.NAME like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		// 发动机号
		if(entity.getEnginNo() != null && !"".equals(entity.getEnginNo())){
			where.append(" and l.ENGIN_NO =:enginNo");
			pMap.put("enginNo", entity.getEnginNo());
		}
		// 车辆识别代码
		if(entity.getCarNo() != null && !"".equals(entity.getCarNo())){
			where.append(" and l.CAR_NO =:carNo");
			pMap.put("carNo", entity.getCarNo());
		}
		
		// 判断创建人
		if(Constants.USER_ADMIN.equals(currentUser.getJobLevel())){
			
		} else if(Constants.USER_SECOND.equals(currentUser.getJobLevel())){
			where.append(" and u.GROUP_ID =:groupId ");
			pMap.put("groupId", currentUser.getGroupId());
		} else if(Constants.USER_THIRD.equals(currentUser.getJobLevel())){
			where.append(" and u.GROUP_ID =:groupId ");
			pMap.put("groupId", currentUser.getGroupId());
			where.append(" and (u.CREATE_BY =:createBy or u.USER_CODE = :createBy) ");
			pMap.put("createBy", currentUser.getUserCode());
		}
		
		StringBuilder order = new StringBuilder();
		order.append(" order by l.CREATE_TIME desc");
		
		Query query = licenseDao.createSqlQuery(hql.append(where).append(order).toString(), pMap);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			query.setParameter(p.getKey(), p.getValue());
		}
		query.setResultTransformer(Transformers.aliasToBean(License.class));
		return query.list();
	}

}
