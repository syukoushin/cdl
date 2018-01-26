package com.ibm.core.orm.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.ibm.core.exception.DBException;

public class JdbcBaseDao extends NamedParameterJdbcDaoSupport{
	protected static final Log log = LogFactory.getLog(JdbcBaseDao.class);
	
protected void batchUpdate(String sql, List<Object[]> params) throws DBException
  {
    try
    {
      getJdbcTemplate().batchUpdate(sql, new CWBatchPSSetter(params));
    } catch (DataAccessException e) {
      throw new DBException(e);
    }
  }

  protected void batchUpdate(List<String> sqls, List<Object[]> params) throws DBException
  {
    try
    {
      if (sqls.size() != params.size())
        throw new DBException("SQL语句与参数不匹配！");
      Iterator sqlIterator = sqls.iterator();
      Iterator paramsIterator = params.iterator();
      do {
        update((String)sqlIterator.next(), (Object[])paramsIterator.next());

        if (!sqlIterator.hasNext()) break; 
      }while (paramsIterator.hasNext());
    }
    catch (DataAccessException e)
    {
      throw new DBException(e);
    }
  }
  
  protected int update(String sql) throws DBException
  {
    try
    {
      return getJdbcTemplate().update(sql); }
    catch (DataAccessException e) {
    	throw new DBException(e);
    }
  }

  protected int update(String sql, Map paramMap) throws DBException
  {
    try
    {
      return getNamedParameterJdbcTemplate().update(sql, paramMap); 
    } catch (DataAccessException e) {
      throw new DBException(e);
    }
  
  }

  protected int update(String sql, Object[] args) throws DBException
  {
    try
    {
      return getJdbcTemplate().update(sql, args); 
    } catch (DataAccessException e) {
    	 throw new DBException(e);
    }
   
  }

  
  protected int update(String sql, SqlParameterSource paramSource) throws DBException
  {
    try
    {
      return getNamedParameterJdbcTemplate().update(sql, paramSource); 
    }catch (DataAccessException e) {
    	 throw new DBException(e);
    }
  }
  
  private class CWBatchPSSetter implements BatchPreparedStatementSetter
  {
    private List<Object[]> params = null;

    public CWBatchPSSetter(List<Object[]> params) {
      this.params = params;
    }

    public int getBatchSize()
    {
      return this.params.size();
    }

    public void setValues(PreparedStatement ps, int i) throws SQLException
    {
      Object[] param = (Object[])this.params.get(i);
      for (int j = 0; j < param.length; j++)
        if ((param[j] instanceof java.util.Date))
          ps.setObject(j + 1, new java.sql.Date(((java.util.Date)param[j]).getTime()));
        else
          ps.setObject(j + 1, param[j]);
    }
  }
  
}
