package com.ibm.core.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.ibm.core.page.PageResults;
import com.ibm.iframeworks.core.orm.DAOSupportException;
import com.ibm.iframeworks.core.orm.hibernate.HibernateDAOSupport;

public abstract class BaseDao<T, PK extends Serializable> extends HibernateDAOSupport<T, PK> {

	public void update(T transientObject) throws DAOSupportException {
		this.getHibernateTemplate().update(transientObject);
	}

	@SuppressWarnings("unchecked")
	protected PageResults findPageByCriteria(DetachedCriteria detachedCriteria, int pageSize, int pageNo) {
		return findPageByCriteria(detachedCriteria, -1, pageSize, pageNo, null);
	}

	@SuppressWarnings("unchecked")
	protected PageResults findPageByCriteria(DetachedCriteria detachedCriteria, int pageSize, int pageNo, Order orderBy) {
		return findPageByCriteria(detachedCriteria, -1, pageSize, pageNo, orderBy);
	}

	@SuppressWarnings("unchecked")
	protected PageResults findPageByCriteriaByOrders(DetachedCriteria detachedCriteria, int pageSize, int pageNo, Order[] orderBy) {
		return findPageByCriteriaByOrders(detachedCriteria, -1, pageSize, pageNo, orderBy);
	}

	@SuppressWarnings("unchecked")
	protected PageResults findPageByCriteria(DetachedCriteria detachedCriteria, int total, int pageSize, int pageNo) {
		return findPageByCriteria(detachedCriteria, total, pageSize, pageNo, null);
	}

	@SuppressWarnings("unchecked")
	protected PageResults findPageByCriteriaByOrders(final DetachedCriteria detachedCriteria, final int total, final int pageSize, final int pageNo, final Order[] orderBy) {
		if (pageNo <= 0 || pageSize <= 0)
			throw new IllegalArgumentException("pageNo or pageSize should >0");
		else
			return (PageResults) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					int totalCount = 0;
					CriteriaImpl criteria = (CriteriaImpl) detachedCriteria.getExecutableCriteria(session);
					org.hibernate.criterion.Projection projection = criteria.getProjection();
					if (total < 0) {
//						Long totalCountObject = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
						Integer totalCountObject = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
						totalCount = totalCountObject != null ? totalCountObject.intValue() : 0;
					} else {
						totalCount = total;
					}
					PageResults retValue = new PageResults();
					retValue.setPageSize(pageSize);
					retValue.setTotalCount(totalCount <= 0 ? 1 : totalCount);
					if(pageNo>retValue.getPageCount())
						retValue.setPageNo(retValue.getPageCount());
					else
						retValue.setPageNo(pageNo);
					
					List items = new ArrayList();
					if (totalCount == 0) {
						retValue.setResults(items);
					} else {
						if (orderBy != null) {
							List ids = new ArrayList();
							ProjectionList pl = Projections.projectionList().add(Projections.distinct(Projections.id()));

							for (int i = 0; i < orderBy.length; i++) {
								String orderStr = orderBy[i].toString().split(" ")[0];
								pl.add(Projections.property(orderStr));
							}
							Criteria cr = criteria.setProjection(pl);
							for (int i = 0; i < orderBy.length; i++) {
								cr.addOrder(orderBy[i]);
							}
							ids = cr.setFirstResult(retValue.getResultsFrom() - 1).setMaxResults(pageSize).list();
							List idss = new ArrayList();
							for (int i = 0; i < ids.size(); i++) {
								Object o[] = (Object[]) ids.get(i);
								idss.add(o[0]);
							}
							ids = idss;
							criteria.setProjection(projection);
							criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).add(Restrictions.in("id", ids));
							items = criteria.setFirstResult(0).setMaxResults(2147483647).list();
						} else {
							criteria.setProjection(projection);
							criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
							items = criteria.setFirstResult(retValue.getResultsFrom() - 1).setMaxResults(pageSize).list();
						}
						retValue.setResults(items);
					}
					return retValue;
				}
			});
	}

	@SuppressWarnings("unchecked")
	protected PageResults findPageByCriteria(final DetachedCriteria detachedCriteria, final int total, final int pageSize, final int pageNo, final Order orderBy) {
		if (pageNo <= 0 || pageSize <= 0)
			throw new IllegalArgumentException("pageNo or pageSize should >0");
		else
			return (PageResults) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					int totalCount = 0;
					CriteriaImpl criteria = (CriteriaImpl) detachedCriteria.getExecutableCriteria(session);
					org.hibernate.criterion.Projection projection = criteria.getProjection();
					if (total < 0) {
						Long totalCountObject = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
						totalCount = totalCountObject != null ? totalCountObject.intValue() : 0;
					} else {
						totalCount = total;
					}
					PageResults retValue = new PageResults();
					retValue.setPageNo(pageNo);
					retValue.setPageSize(pageSize);
					retValue.setTotalCount(totalCount <= 0 ? 1 : totalCount);
					List items = new ArrayList();
					if (totalCount == 0) {
						retValue.setResults(items);
					} else {
						if (orderBy != null) {
							List ids = new ArrayList();
							String orderStr = orderBy.toString().split(" ")[0];
							ids = criteria.setProjection(Projections.projectionList().add(Projections.distinct(Projections.id())).add(Projections.property(orderStr))).addOrder(orderBy)
									.setFirstResult(retValue.getResultsFrom() - 1).setMaxResults(pageSize).list();
							List idss = new ArrayList();
							for (int i = 0; i < ids.size(); i++) {
								Object o[] = (Object[]) ids.get(i);
								idss.add(o[0]);
							}
							ids = idss;
							criteria.setProjection(projection);
							criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).add(Restrictions.in("id", ids));
							items = criteria.setFirstResult(0).setMaxResults(2147483647).list();
						} else {
							criteria.setProjection(projection);
							criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
							items = criteria.setFirstResult(retValue.getResultsFrom() - 1).setMaxResults(pageSize).list();
						}
						retValue.setResults(items);
					}
					return retValue;
				}
			});
	}

	@SuppressWarnings("unchecked")
	protected List findAllByCriteria(final DetachedCriteria detachedCriteria) {
		return (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				return criteria.list();
			}
		});
	}

	/**
	 * 查询记录数目，含重复
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected long findCountByCriteria(final DetachedCriteria detachedCriteria) {

		Long count = (Long) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.setProjection(Projections.rowCount()).uniqueResult();
			}
		});
		return count.longValue();
	}

	/**
	 * 查询不重复的记录数目
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected long findCountByCriteriaUsingCountDistinct(final DetachedCriteria detachedCriteria) {
		Long count = (Long) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.uniqueResult();
			}
		});
		return count.longValue();
	}
	
    public String generatePagingSql(String sql, int pageSize,int pageNo)
    {
       return new StringBuffer("SELECT * FROM(SELECT T.*, ROWNUMBER() OVER() AS RN FROM (").
       append(sql).append(") AS T)AS A WHERE A.RN BETWEEN ").
       append((pageSize*(pageNo-1)+1)).append(" AND ").
       append(pageSize*pageNo).toString();
    }
    
	public int  queryTotalCount(String sql){
		List list=this.getSession().createSQLQuery(generateCntSql(sql)).list();
		if(list!=null&&list.size()>0){
			return (Integer)list.get(0);
		}
		return 0;
	}
	
	private String generateCntSql(String sql)
    {
        return (new StringBuilder("SELECT COUNT(*) FROM ( ")).append(sql).append(" ) AS T").toString();
    }	
}
