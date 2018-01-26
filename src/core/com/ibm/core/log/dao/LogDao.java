/**
 * 
 */
package com.ibm.core.log.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ibm.core.log.pojo.Log;
import com.ibm.core.orm.Page;
import com.ibm.core.orm.hibernate.SessionFactoryDao;
@Repository("logDao")
public class LogDao extends SessionFactoryDao<Log, String> {
	private static final org.apache.commons.logging.Log logger = LogFactory.getLog(LogDao.class);
	/**
	 * 添加日志
	 * @param application
	 */
	public void saveMoaLog(Log log){
		this.save(log);
	}
	/**
	 * 批量添加日志
	 * 使用Jdbc
	 * @param application
	 */
	public int moaLogInsert(List<Log> logs){
		this.save(logs);
		return logs.size();
   }
	
	public Page<Log> findPage(Page<Log> page,Log log) {
		
		String hql = "from Log where 1 = 1";
		List paramList = new ArrayList();
		if (StringUtils.isNotEmpty(log.getUserCode())) {
			hql += " and userCode like '%" +log.getUserCode()+ "%'";
		}
		
		if (StringUtils.isNotEmpty(log.getOperation())) {
			hql += " and operation = '"+log.getOperation()+"'";
		}
		
		hql += " order by oprationTime desc";
		
		return this.findPage(page, hql, paramList.toArray(new Object[paramList.size()]));
	}
}
