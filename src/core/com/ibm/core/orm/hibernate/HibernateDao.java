package com.ibm.core.orm.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;

import com.ibm.core.orm.Page;
import com.ibm.core.orm.PropertyFilter;
import com.ibm.core.orm.PropertyFilter.MatchType;
import com.ibm.core.orm.PropertyFilter.PropertyType;
import com.ibm.core.util.AssertUtils;
import com.ibm.core.util.ConvertUtils;
import com.ibm.core.util.ReflectionUtils;

/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <ID>
 *            主键类型
 * 
 */
public class HibernateDao<T, ID extends Serializable> extends
		SimpleHibernateDao<T, ID> {
	/**
	 * 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends HibernateDao<User,
	 * Long>{ }
	 */
	public HibernateDao() {
		super();
	}

	public HibernateDao(Class<T> entityClass) {
		super(entityClass);
	}

	// -- 分页查询函数 --//

	/**
	 * 分页获取全部对象.
	 */
	public Page<T> getAll(final Page<T> page) {
		return findPage(page);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数. 注意不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	public Page<T> findPage(final Page<T> page, final String hql,
			final Object... values) {
		AssertUtils.notNull(page, "page不能为空");
		Query q = createQuery(hql, values);
		long totalCount = countHqlResult(hql, values);
		if (totalCount < 1) {
			return page;
		}
		int startIndex = Page.getStartOfPage(page.getPageNo(), page
				.getPageSize());
		setPageParameterToQuery(q, page);
		List result = q.list();
		Page.setPageValue(page, startIndex, totalCount, result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数. 注意不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	public Page<T> findPage(final Page<T> page, final String hql,
			final Map<String, ?> values) {
		AssertUtils.notNull(page, "page不能为空");
		Query q = createQuery(hql, values);
		long totalCount = countHqlResult(hql, values);
		if (totalCount < 1) {
			return page;
		}
		int startIndex = Page.getStartOfPage(page.getPageNo(), page
				.getPageSize());
		setPageParameterToQuery(q, page);
		List result = q.list();
		Page.setPageValue(page, startIndex, totalCount, result);
		return page;
	}

	/**
	 * 多表查询的分页方法
	 * 
	 * @param <K>
	 * @param page
	 * @param sql
	 * @return
	 */
	public Page findPage(final Page page, final String sql, Class clazz) {

		int totalCount = countSqlResult(sql);

		int start = (page.getPageNo() > 1 ? (page.getPageNo() - 1)
				* page.getPageSize() : 0);
		;

		String pageSql = sql + " limit " + start + "," + page.getPageSize();

		List result = getSession().createSQLQuery(pageSql)
				.setResultTransformer(Transformers.aliasToBean(clazz)).list();

		page.setTotalCount(totalCount);
		page.setResult(result);

		Page returnPage = new Page(page.getPageNo(), start, page
				.getTotalCount(), page.getPageSize(), page.getResult());

		returnPage.setSortColumn(page.getSortColumn());
		returnPage.setSortType(page.getSortType());

		return returnPage;
	}

	/**
	 * 多表查询的分页方法
	 * 
	 * @param <K>
	 * @param page
	 * @param sql
	 * @return
	 */
	public Page findPage(final Page page, final String sqlHeader,
			final String sql, final String sqlFooter, Class clazz) {

		int totalCount = countSqlResult(sql);

		int start = (page.getPageNo() > 1 ? (page.getPageNo() - 1)
				* page.getPageSize() : 0);
		;

		String pageSql = sql + " limit " + start + "," + page.getPageSize();

		List result = getSession().createSQLQuery(
				sqlHeader + pageSql + sqlFooter).setResultTransformer(
				Transformers.aliasToBean(clazz)).list();

		page.setTotalCount(totalCount);
		page.setResult(result);

		Page returnPage = new Page(page.getPageNo(), start, page
				.getTotalCount(), page.getPageSize(), page.getResult());

		returnPage.setSortColumn(page.getSortColumn());
		returnPage.setSortType(page.getSortType());

		return returnPage;
	}

	/**
	 * 多表查询的分页方法2 添加获得分页数量sql
	 * 
	 * @param <K>
	 * @param page
	 * @param sql
	 * @return
	 */
	public Page findPage(final Page page, final String sqlHeader,
			final String sql, final String sqlFooter, Class clazz,
			String totalsql) {

		int totalCount = Integer.parseInt(""
				+ this.getSession().createSQLQuery(totalsql).list().get(0));

		int start = (page.getPageNo() > 1 ? (page.getPageNo() - 1)
				* page.getPageSize() : 0);

		String pageSql = sql + " limit " + start + "," + page.getPageSize();

		List result = getSession().createSQLQuery(
				sqlHeader + pageSql + sqlFooter).setResultTransformer(
				Transformers.aliasToBean(clazz)).list();

		page.setTotalCount(totalCount);
		page.setResult(result);

		Page returnPage = new Page(page.getPageNo(), start, page
				.getTotalCount(), page.getPageSize(), page.getResult());

		returnPage.setSortColumn(page.getSortColumn());
		returnPage.setSortType(page.getSortType());

		return returnPage;
	}

	public int countSqlResult(String sql) {

		int countResult = 0;

		String countSql = "select count(*) from (" + sql + ") countSql ";

		countResult = Integer.parseInt(String.valueOf(getSession()
				.createSQLQuery(countSql).list().get(0)));

		return countResult;
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * @param criterions
	 *            数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 */
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		AssertUtils.notNull(page, "page不能为空");
		Criteria c = createCriteria(criterions);
		long totalCount = countCriteriaResult(c);
		if (totalCount < 1) {
			return page;
		}
		int startIndex = Page.getStartOfPage(page.getPageNo(), page
				.getPageSize());
		setPageParameterToCriteria(c, page);
		List result = c.list();
		Page.setPageValue(page, startIndex, totalCount, result);
		return page;
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * @param criterions
	 *            数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 */
	public Page<T> findPage(final Page<T> page, final Order[] orders,
			final Criterion... criterions) {
		AssertUtils.notNull(page, "page不能为空");
		Criteria c = createCriteria(criterions);
		long totalCount = countCriteriaResult(c);
		if (totalCount < 1) {
			return page;
		}
		int startIndex = Page.getStartOfPage(page.getPageNo(), page
				.getPageSize());
		if (orders != null && orders.length > 0) {
			for (Order order : orders) {
				c.addOrder(order);
			}
		}
		setPageParameterToCriteria(c, page);
		List result = c.list();
		Page.setPageValue(page, startIndex, totalCount, result);
		return page;
	}

	/**
	 * 按照参数分页查询，默认情况下都为eq
	 * 
	 * @param page
	 * @param values
	 * @return
	 */
	public Page<T> findPage(final Page<T> page, final Map<String, Object> values) {

		return findPage(page, values, null);
	}

	/**
	 * 按照参数查询全部
	 */
	public List<T> findList(final Map<String, Object> values) {
		return findList(values, null);
	}

	/**
	 * 按照参数分页查询，默认情况下都为eq,支持排序
	 * 
	 * @param page
	 * @param values
	 * @param order
	 * @return
	 */
	public Page<T> findPage(final Page<T> page,
			final Map<String, Object> values, Order[] orders) {
		return findPage(page, values, null, orders);
	}

	public List<T> findList(final Map<String, Object> values, Order[] orders) {
		return findList(values, null, orders);
	}

	/**
	 * 按照参数分页查询，查询values中含有的数据，如果没有则不做处理
	 * 
	 * @param page
	 * @param values
	 * @param order
	 * @param queryKey
	 *            定义查询key，类型，查询方式 eq. name_S_EQ name为属性名，S为属性类型，EQ为查询类型 属性类型有 ：
	 *            S(String.class), I(Integer.class), L(Long.class),
	 *            N(Double.class), D(Date.class), B(Boolean.class) 查询类型有 ： EQ,
	 *            LIKE, LT, GT, LE, GE;
	 * @return
	 */
	public Page<T> findPage(final Page<T> page,
			final Map<String, Object> values, String[] queryKey, Order[] orders) {
		AssertUtils.notNull(page, "page不能为空");
		Criteria c = createCriteria(buildCriterionByMap(values, queryKey));
		long totalCount = countCriteriaResult(c);
		if (totalCount < 1) {
			return page;
		}
		int startIndex = Page.getStartOfPage(page.getPageNo(), page
				.getPageSize());

		if (orders != null && orders.length > 0) {
			for (Order order : orders) {
				c.addOrder(order);
			}
		}
		setPageParameterToCriteria(c, page);
		List result = c.list();
		Page.setPageValue(page, startIndex, totalCount, result);
		return page;
	}

	public List<T> findList(final Map<String, Object> values,
			String[] queryKey, Order[] orders) {
		Criteria c = createCriteria(buildCriterionByMap(values, queryKey));
		if (orders != null && orders.length > 0) {
			for (Order order : orders) {
				c.addOrder(order);
			}
		}
		return c.list();
	}

	public List<T> findPageList(final Map<String, Object> values,
			String[] queryKey, int pageNo, int pageSize) {
		return findPageList(values, queryKey, null, pageNo, pageSize);
	}

	/**
	 * 分页查询值
	 */
	public List<T> findPageList(final Map<String, Object> values,
			String[] queryKey, Order[] orders, int pageNo, int pageSize) {

		return findPageIndexList(values, queryKey, orders, (pageNo - 1)
				* pageSize, pageSize);

	}

	/**
	 * 位置查询
	 */
	public List<T> findPageIndexList(final Map<String, Object> values,
			String[] queryKey, Order[] orders, int startIndex, int pageSize) {

		Criteria c = createCriteria(buildCriterionByMap(values, queryKey));

		if (orders != null && orders.length > 0) {
			for (Order order : orders) {
				c.addOrder(order);
			}
		}

		c.setFirstResult(startIndex);
		c.setMaxResults(pageSize);

		return c.list();
	}

	/**
	 * 查询分页数量
	 * 
	 * @param values
	 * @param queryKey
	 * @return
	 */
	public long findPageCount(final Map<String, Object> values,
			String[] queryKey) {
		Criteria c = createCriteria(buildCriterionByMap(values, queryKey));
		return countCriteriaResult(c);
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameterToQuery(final Query q, final Page<T> page) {
		AssertUtils.isTrue(page.getPageSize() > 0,
				"Page Size must larger than zero");

		q.setFirstResult(Page.getStartOfPage(page.getPageNo(), page
				.getPageSize()));
		q.setMaxResults(page.getPageSize());

		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameterToCriteria(final Criteria c,
			final Page<T> page) {
		AssertUtils.isTrue(page.getPageSize() > 0,
				"Page Size must larger than zero");

		c.setFirstResult(Page.getStartOfPage(page.getPageNo(), page
				.getPageSize()));
		c.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			AssertUtils.isTrue(orderByArray.length == orderArray.length,
					"分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl,
					"orderEntries");
			ReflectionUtils
					.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		Integer totalCountObject = (Integer) c.setProjection(
				Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}

	// -- 属性过滤条件(PropertyFilter)查询函数 --//

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType
	 *            匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
	 */
	public List<T> findBy(final String propertyName, final Object value,
			final MatchType matchType) {
		Criterion criterion = buildCriterion(propertyName, value, matchType);
		return find(criterion);
	}

	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public List<T> find(List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return find(criterions);
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> findPage(final Page<T> page,
			final List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return findPage(page, criterions);
	}

	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildCriterion(final String propertyName,
			final Object propertyValue, final MatchType matchType) {
		AssertUtils.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
		// 根据MatchType构造criterion
		switch (matchType) {
		case EQ:
			criterion = Restrictions.eq(propertyName, propertyValue);
			break;
		case LIKE:
			criterion = Restrictions.like(propertyName, (String) propertyValue,
					MatchMode.ANYWHERE);
			break;

		case LE:
			criterion = Restrictions.le(propertyName, propertyValue);
			break;
		case LT:
			criterion = Restrictions.lt(propertyName, propertyValue);
			break;
		case GE:
			criterion = Restrictions.ge(propertyName, propertyValue);
			break;
		case GT:
			criterion = Restrictions.gt(propertyName, propertyValue);
		}
		return criterion;
	}

	/**
	 * 创建相等的属性条件
	 */
	protected Criterion[] buildCriterionByMap(final Map<String, Object> values) {

		return buildCriterionByMap(values, null);
	}

	/**
	 * @param values
	 * @param queryKey
	 *            eq. name_S_EQ name为属性名，S为属性类型，EQ为查询类型 属性类型有 ： S(String.class),
	 *            I(Integer.class), L(Long.class), N(Double.class),
	 *            D(Date.class), B(Boolean.class) 查询类型有 ： EQ, LIKE, LT, GT, LE,
	 *            GE;
	 * @return
	 */
	protected Criterion[] buildCriterionByMap(final Map<String, Object> values,
			final String[] queryKey) {

		List<Criterion> criterionList = new ArrayList<Criterion>();

		if (queryKey == null || queryKey.length == 0)
			return new Criterion[0];

		for (String key : queryKey) {

			String[] keys = key.split("_");

			if (!values.containsKey(keys[0]))
				continue;
			// 如果是日期类型
			if ("D".equals(keys[1])) {

				Date startDate = new Date(values.get(keys[0]).toString());

				try {
					startDate.setHours(0);
					startDate.setMinutes(0);
					startDate.setSeconds(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if ("LIKE".equals(keys[2])) {
					// 表示要查询当天范围内的
					Date endDate = new Date(startDate.getTime() + 1000 * 60
							* 60 * 24);

					criterionList.add(buildCriterion(keys[0], startDate,
							MatchType.GE));
					criterionList.add(buildCriterion(keys[0], endDate,
							MatchType.LE));
				} else {
					criterionList.add(buildCriterion(keys[0], startDate, Enum
							.valueOf(MatchType.class, keys[2])));
				}
			} else {
				criterionList.add(buildCriterion(keys[0], values.get(keys[0]),
						keys[1], keys[2]));
			}
		}

		return criterionList.toArray(new Criterion[criterionList.size()]);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param typeClass
	 * @param matchType
	 * @return
	 */
	protected Criterion buildCriterion(String key, Object value,
			String typeClass, String matchType) {

		MatchType type;

		try {
			type = Enum.valueOf(MatchType.class, matchType);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("matchType名称" + matchType
					+ "没有按规则编写,无法得到属性比较类型.", e);
		}

		Class propertyClass;

		try {
			propertyClass = Enum.valueOf(PropertyType.class, typeClass)
					.getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("propertyClass名称" + typeClass
					+ "没有按规则编写,无法得到属性值类型.", e);
		}

		Object valueObj = null;
		valueObj = ConvertUtils.convertStringToObject(value.toString(),
				propertyClass);

		return buildCriterion(key, valueObj, type);
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	protected Criterion[] buildCriterionByPropertyFilter(
			final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			if (!filter.hasMultiProperties()) { // 只有一个属性需要比较的情况.
				Criterion criterion = buildCriterion(filter.getPropertyName(),
						filter.getMatchValue(), filter.getMatchType());
				criterionList.add(criterion);
			} else {// 包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames()) {
					Criterion criterion = buildCriterion(param, filter
							.getMatchValue(), filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}
}
