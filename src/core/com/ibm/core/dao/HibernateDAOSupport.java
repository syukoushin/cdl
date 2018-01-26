package com.ibm.core.dao;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.LockMode;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ibm.iframeworks.core.orm.DAOSupportException;

public abstract class HibernateDAOSupport<T, PK extends Serializable> extends HibernateDaoSupport {
	private Class<T> type;

	public HibernateDAOSupport() {
		this.type = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.type = (Class<T>) p[0];
		}
	}

	public void attachClean(T object) {
		try {
			super.getHibernateTemplate().lock(object, LockMode.NONE);
		} catch (DataAccessException e) {
			throw new DAOSupportException(e);
		}
	}

	public void attachDirty(T object) {
		try {
			super.getHibernateTemplate().saveOrUpdate(object);
		} catch (DataAccessException e) {
			throw new DAOSupportException(e);
		}
	}

	public void delete(T object) {
		try {
			super.getHibernateTemplate().delete(object);
		} catch (DataAccessException e) {
			throw new DAOSupportException(e);
		}
	}

	public List<T> findByExample(T object) {
		try {
			List<T> returnList = super.getHibernateTemplate().findByExample(object);
			return returnList;
		} catch (DataAccessException e) {
			throw new DAOSupportException(e);
		}
	}

	public T findById(PK id) {
		try {
			T returnT = (T) super.getHibernateTemplate().get(type, id);

			return returnT;
		} catch (DataAccessException e) {
			throw new DAOSupportException(e);
		}
	}

	public void merge(T object) {
		try {
			super.getHibernateTemplate().merge(object);
		} catch (DataAccessException e) {
			throw new DAOSupportException(e);
		}
	}

	public void persist(T object) {
		try {
			super.getHibernateTemplate().persist(object);
		} catch (DataAccessException e) {
			throw new DAOSupportException(e);
		}
	}

	public PK save(T object) {
		try {
			PK returnPK = (PK) super.getHibernateTemplate().save(object);
			return returnPK;
		} catch (DataAccessException e) {
			throw new DAOSupportException(e);
		}
	}

}
