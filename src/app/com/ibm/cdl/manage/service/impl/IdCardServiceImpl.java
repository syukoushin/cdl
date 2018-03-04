package com.ibm.cdl.manage.service.impl;

import com.ibm.cdl.manage.dao.IdCardDao;
import com.ibm.cdl.manage.pojo.IdCard;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.cdl.manage.service.IdCardService;
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

@Service("idCardService")
public class IdCardServiceImpl implements IdCardService {
	

	@Autowired
	private IdCardDao idCardDao;

	@Autowired
	private  UserService userService;

	/**
	 * 分页获取历史记录（客户端用）
	 * @param entity
	 * @param page
	 * @return
	 */
	public Page<IdCard> findPageForClient(IdCard entity, Page<IdCard> page) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();

		hql.append("select l.NAME as name,");
		hql.append (" l.ID as id, ");
		hql.append (" l.CARD_NO as cardNo, ");
		hql.append (" l.ADDRESS as address,");
		hql.append (" l.SEX as sex,");
		hql.append (" l.ETHNIC as ethnic,");
		hql.append (" l.CREATE_USER as createUser, ");
		hql.append (" l.CREATE_TIME as createTime, ");
		hql.append ("  a.STORE_PATH AS storePath");
		hql.append (" from ");
		
		StringBuilder where = new StringBuilder();
		where.append(" id_card l ").append(" left join attachment a on a.BUSINESS_ID = l.ID where 1 = 1 ");;

		// cardNo不为空的场合
		if( entity.getCardNo() != null && !"".equals(entity.getCardNo())){
			where.append(" and l.CARD_NO= :cardNo");
			pMap.put("cardNo", entity.getCardNo());
		}
		// 姓名
		if(entity.getName() != null && !"".equals(entity.getName())){
			where.append(" and l.NAME like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		// 性别
		if(entity.getSex() != null && !"".equals(entity.getSex())){
			where.append(" and l.SEX =:sex");
			pMap.put("sex", entity.getSex());
		}
		// 民族
		if(entity.getEthnic() != null && !"".equals(entity.getEthnic())){
			where.append( " and l.ETHNIC  = :ethnic");
			pMap.put("ethnic",entity.getEthnic());
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
		Query queryList = idCardDao.getSession().createSQLQuery(querySqlString);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			queryList.setParameter(p.getKey(), p.getValue());
		}
		queryList.setResultTransformer(Transformers.aliasToBean(IdCard.class));
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
	public Page<IdCard> findPage(IdCard entity, Page<IdCard> page,User currentUser) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();

		hql.append("select l.NAME as name,");
		hql.append ("l.ID as id, ");
		hql.append ("l.CARD_NO as cardNo, ");
		hql.append ("l.ADDRESS as address,");
		hql.append ("l.SEX as sex,");
		hql.append ("l.ETHNIC as ethnic,");
		hql.append ("l.CREATE_USER as createUser,");
		hql.append ("l.CREATE_TIME as createTime");
		hql.append (" from ");
		
		StringBuilder where = new StringBuilder();
		where.append(" id_card l left join user u on l.CREATE_USER = u.USER_CODE  where 1 = 1 ");
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

		// 性别
		if(entity.getSex() != null && !"".equals(entity.getSex())){
			where.append(" and l.SEX =:sex");
			pMap.put("sex", entity.getSex());
		}
		// 民族
		if(entity.getEthnic() != null && !"".equals(entity.getEthnic())){
			where.append( " and l.ETHNIC  = :ethnic");
			pMap.put("ethnic",entity.getEthnic());
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
	
		StringBuilder countSql = new StringBuilder("SELECT COUNT(1) from");
		countSql.append(where.toString());
		
		// 计算总条数
		Query query = idCardDao.getSession().createSQLQuery(countSql.toString());
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
		Query queryList = idCardDao.getSession().createSQLQuery(querySqlString);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			queryList.setParameter(p.getKey(), p.getValue());
		}
		queryList.setResultTransformer(Transformers.aliasToBean(IdCard.class));
		List<User> result = queryList.list();
		int start = Page.getStartOfPage(page.getPageNo(), page.getPageSize());
		Page.setPageValue(page, start, totalCount, result);
		return page;
	}
	
	public void addEntity(IdCard entity) {
		idCardDao.save(entity);
	}

	public void delEntity(String ids) {
		idCardDao.delete(ids);
	}

	public IdCard findEntityById(String id) throws Exception {
		IdCard temp = idCardDao.findUniqueBy("id", id);
		User u = userService.queryUserByLoginName(temp.getCreateUser());
		IdCard re = new IdCard();
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
	public List<IdCard> findListBy(IdCard entity,User currentUser) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();

		hql.append("select l.NAME as name,");
		hql.append ("l.ID as id, ");
		hql.append ("l.CARD_NO as cardNo, ");
		hql.append ("l.ADDRESS as address,");
		hql.append ("l.SEX as sex,");
		hql.append ("l.ETHNIC as ethnic,");
		hql.append ("l.CREATE_USER as createUser,");
		hql.append ("l.CREATE_TIME as createTime");
		hql.append (" from ");

		StringBuilder where = new StringBuilder();
		where.append(" id_card l left join user u on l.CREATE_USER = u.USER_CODE  where 1 = 1 ");

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
		// 性别
		if(entity.getSex() != null && !"".equals(entity.getSex())){
			where.append(" and l.SEX =:sex");
			pMap.put("sex", entity.getSex());
		}
		// 民族
		if(entity.getEthnic() != null && !"".equals(entity.getEthnic())){
			where.append( " and l.ETHNIC  = :ethnic");
			pMap.put("ethnic",entity.getEthnic());
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
		
		StringBuilder order = new StringBuilder();
		order.append(" order by l.CREATE_TIME desc");
		
		Query query = idCardDao.createSqlQuery(hql.append(where).append(order).toString(), pMap);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			query.setParameter(p.getKey(), p.getValue());
		}
		query.setResultTransformer(Transformers.aliasToBean(IdCard.class));
		return query.list();
	}

}
