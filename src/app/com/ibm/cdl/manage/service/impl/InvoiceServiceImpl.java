package com.ibm.cdl.manage.service.impl;

import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.manage.dao.InvoiceDao;
import com.ibm.cdl.manage.pojo.Invoice;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.cdl.manage.service.InvoiceService;
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

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InvoiceDao invoiceDao;

	/**
	 * 分页查询历史记录（客户端）
	 * @param entity
	 * @param page
	 * @return
	 */
	public Page<Invoice> findPageForClient(Invoice entity, Page<Invoice> page) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();
		hql.append(" SELECT i.ID AS id,");
		hql.append("  i.NUMBER AS number,");
		hql.append("  i.PRINT_DATE AS printDate,");
		hql.append("  i.NAME AS name,");
		hql.append("  i.INVOICE_NO AS invoiceNo,");
		hql.append("  i.FRAME_NO AS frameNo,");
		hql.append("  i.TAX AS tax,");
		hql.append("  i.CREATE_TIME AS createTime,");
		hql.append("  i.CREATE_USER AS createUser,");
		hql.append("  a.ID AS attId");
		hql.append(" from  ");

		StringBuilder where = new StringBuilder();
		where.append(" invoice i ").append(" left join attachment a on a.BUSINESS_ID = i.ID where 1 = 1 ");

		// 发票代码
		if( entity.getInvoiceNo() != null && !"".equals(entity.getInvoiceNo())){
			where.append(" and i.INVOICE_NO= :invoiceNo");
			pMap.put("invoiceNo", entity.getInvoiceNo());
		}
		// 发票代码
		if( entity.getNumber() != null && !"".equals(entity.getNumber())){
			where.append(" and i.NUMBER= :number");
			pMap.put("number", entity.getNumber());
		}
		// 购买方税号
		if(entity.getName() != null && !"".equals(entity.getName())){
			where.append(" and i.NAME like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		// 车架号码
		if(entity.getFrameNo() != null && !"".equals(entity.getFrameNo())){
			where.append(" and i.FRAME_NO = :frameNo");
			pMap.put("frameNo", entity.getFrameNo());
		}

		// 判断创建人
		if(entity.getCreateUser() != null && !"".equals(entity.getCreateUser())){
			where.append(" and i.CREATE_USER = :createUser ");
			pMap.put("createUser", entity.getCreateUser());
		}
		StringBuilder order = new StringBuilder();
		order.append(" order by i.CREATE_TIME desc");


		// 进行分页查询
		String querySqlString = hql.append(where).append(order).append(" LIMIT ")
				.append((page.getPageNo()-1)*page.getPageSize())
				.append(",")
				.append(page.getPageSize())
				.toString();
		Query queryList = invoiceDao.getSession().createSQLQuery(querySqlString);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			queryList.setParameter(p.getKey(), p.getValue());
		}
		queryList.setResultTransformer(Transformers.aliasToBean(Invoice.class));
		List<User> result = queryList.list();
		int start = Page.getStartOfPage(page.getPageNo(), page.getPageSize());
		Page.setPageValue(page, start, 0, result);
		return page;
	}

	/**
	 * 后台分页查询 列表
	 * @param entity
	 * @param page
	 * @param currentUser
	 * @return
	 */
	public Page<Invoice> findPage(Invoice entity, Page<Invoice> page,User currentUser) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();
		hql.append(" SELECT i.ID AS id,");
		hql.append("  i.NUMBER AS number,");
		hql.append("  i.PRINT_DATE AS printDate,");
		hql.append("  i.NAME AS name,");
		hql.append("  i.INVOICE_NO AS invoiceNo,");
		hql.append("  i.FRAME_NO AS frameNo,");
		hql.append("  i.TAX AS tax,");
		hql.append("  i.CREATE_TIME AS createTime,");
		hql.append("  i.CREATE_USER AS createUser");
		hql.append(" from  ");
		
		StringBuilder where = new StringBuilder();
		where.append(" invoice i left join user u on i.CREATE_USER = u.USER_CODE where 1 = 1 ");
		// 发票代码
		if( entity.getInvoiceNo() != null && !"".equals(entity.getInvoiceNo())){
			where.append(" and i.INVOICE_NO= :invoiceNo");
			pMap.put("invoiceNo", entity.getInvoiceNo());
		}
		// 发票代码
		if( entity.getNumber() != null && !"".equals(entity.getNumber())){
			where.append(" and i.NUMBER= :number");
			pMap.put("number", entity.getNumber());
		}
		// 购买方税号
		if(entity.getName() != null && !"".equals(entity.getName())){
			where.append(" and i.NAME like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		// 车架号码
		if(entity.getFrameNo() != null && !"".equals(entity.getFrameNo())){
			where.append(" and i.FRAME_NO =:frameNo");
			pMap.put("frameNo", entity.getFrameNo());
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
		order.append(" order by i.CREATE_TIME desc");
	
		StringBuilder countSql = new StringBuilder("SELECT COUNT(1) from");
		countSql.append(where.toString());
		
		// 计算总条数
		Query query = invoiceDao.getSession().createSQLQuery(countSql.toString());
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
		Query queryList = invoiceDao.getSession().createSQLQuery(querySqlString);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			queryList.setParameter(p.getKey(), p.getValue());
		}
		queryList.setResultTransformer(Transformers.aliasToBean(Invoice.class));
		List<User> result = queryList.list();
		int start = Page.getStartOfPage(page.getPageNo(), page.getPageSize());
		Page.setPageValue(page, start, totalCount, result);
		return page;
	}
	
	public void addEntity(Invoice entity) {
		invoiceDao.save(entity);
	}

	public void delEntity(String ids) {
		invoiceDao.delete(ids);
	}

	public Invoice findEntityById(String id) throws Exception {
		Invoice temp= invoiceDao.findUniqueBy("id", id);
		User u = userService.queryUserByLoginName(temp.getCreateUser());
		Invoice re = new Invoice();
		BeanUtils.copyProperties(re, temp);
		re.setCreateUser(u.getUserName());
		return re;
	}

	public List<Invoice> findListBy(Invoice entity,User currentUser) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();
		hql.append(" SELECT i.ID AS id,");
		hql.append("  i.NUMBER AS number,");
		hql.append("  i.PRINT_DATE AS printDate,");
		hql.append("  i.NAME AS name,");
		hql.append("  i.INVOICE_NO AS invoiceNo,");
		hql.append("  i.FRAME_NO AS frameNo,");
		hql.append("  i.TAX AS tax,");
		hql.append("  i.CREATE_TIME AS createTime,");
		hql.append("  i.CREATE_USER AS createUser");
		hql.append(" from  ");
		
		StringBuilder where = new StringBuilder();
		where.append(" invoice i left join user u on i.CREATE_USER = u.USER_CODE where 1 = 1 ");
		// 发票代码
		if( entity.getInvoiceNo() != null && !"".equals(entity.getInvoiceNo())){
			where.append(" and i.INVOICE_NO= :invoiceNo");
			pMap.put("invoiceNo", entity.getInvoiceNo());
		}
		// 发票代码
		if( entity.getNumber() != null && !"".equals(entity.getNumber())){
			where.append(" and i.NUMBER = :number");
			pMap.put("number", entity.getNumber());
		}
		// 购买方税号
		if(entity.getName() != null && !"".equals(entity.getName())){
			where.append(" and i.NAME like :name");
			pMap.put("name", "%"+entity.getName()+"%");
		}
		// 车架号码
		if(entity.getFrameNo() != null && !"".equals(entity.getFrameNo())){
			where.append(" and i.FRAME_NO = :frameNo");
			pMap.put("frameNo", entity.getFrameNo());
		}
		
		// 判断创建人
		if(Constants.USER_ADMIN.equals(currentUser.getJobLevel())){
			
		} else if(Constants.USER_SECOND.equals(currentUser.getJobLevel())){
			where.append(" and u.GROUP_ID = :groupId ");
			pMap.put("groupId", currentUser.getGroupId());
		} else if(Constants.USER_THIRD.equals(currentUser.getJobLevel())){
			where.append(" and u.GROUP_ID = :groupId ");
			pMap.put("groupId", currentUser.getGroupId());
			where.append(" and (u.CREATE_BY =:createBy or u.USER_CODE = :createBy) ");
			pMap.put("createBy", currentUser.getUserCode());
		}
		StringBuilder order = new StringBuilder();
		order.append(" order by i.CREATE_TIME desc");
		
		Query query = invoiceDao.createSqlQuery(hql.append(where).append(order).toString(), pMap);
		for(Map.Entry<String, Object> p : pMap.entrySet()){
			query.setParameter(p.getKey(), p.getValue());
		}
		return query.list();
		
	}

}
