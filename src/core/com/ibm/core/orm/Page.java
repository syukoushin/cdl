package com.ibm.core.orm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * 
 * @param <T> Page中记录的类型.
 * 
 */
public class Page<T> implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	//-- 公共变量 --//
	public static final int 	DEFAULT_PAGE_SIZE = 10;
	public static final int 	DEFAULT_FIRST_NO = 1;
	public static final int 	DEFAULT_NUM_COUNT = 4;
	public static final int 	DEFAULT_PAGE_NO = 1;
	public static final int 	DEFAULT_START = 0;
	public static final long 	DEFAULT_TOTAL_COUNT = 0;
	public static final String 	DEFAULT_SORT_COLUMN = "id";
	public static final String 	ASC = "asc";
	public static final String 	DESC = "desc";
	
	private int 	firstNo = DEFAULT_FIRST_NO;			//首页 页号
	private int 	lastNo;								//末页 页号
	private int 	startNo;							//页号式导航中起始页号
	private int 	prevNo;								//上一页 页号
	private int 	nextNo;								//下一页 页号
	private int 	endNo;								//页号导航中结束页号
	private int 	numCount = DEFAULT_NUM_COUNT;		//页号式导航, 最多显示numCount+1页
	private int 	pageCount;							//总页数
	private int 	pageSize = DEFAULT_PAGE_SIZE; 		//每页的记录数
	private int 	pageNo = DEFAULT_PAGE_NO;			//当前页号
	private int 	start; 								//当前页第一条记录排序号
	private long 	totalCount; 						//总记录数
	private List<T> result = new ArrayList<T>();		//当前页中存放的记录
	private String	sortColumn = DEFAULT_SORT_COLUMN;	//排序列
	private String	sortType = DESC;					//排序类型
	
	//-- 分页查询参数 --//
	protected String orderBy = null;
	protected String order = null;
	//-- 返回结果 --//

	/**
	 * 构造方法，只构造空页.
	 */
	public Page() {
		this(DEFAULT_PAGE_NO, DEFAULT_START, DEFAULT_TOTAL_COUNT, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}

	/**
	 * 默认构造方法.
	 * @param targetNo	 当前页
	 * @param start	 本页数据在数据库中的起始位置
	 * @param totalCount 数据库中总记录条数
	 * @param pageSize  本页容量
	 * @param data	  本页包含的数据
	 */
	public Page(int pageNo, int start, long totalCount, int pageSize, List<T> result) {
		this.pageNo = pageNo;
		this.start = start;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.result = result;
		this.pageCount = (int) Math.ceil((double)this.totalCount/this.pageSize);
		this.pageNo = Math.min(this.pageNo, pageCount);
		this.pageNo = Math.max(1, this.pageNo);
		this.lastNo = this.pageCount;
		this.nextNo = Math.min(this.pageCount, this.pageNo+1);
		this.prevNo = Math.max(1, this.pageNo-1);
		this.startNo = Math.max(this.pageNo-this.numCount/2, this.firstNo);
		this.endNo = Math.min(this.startNo+this.numCount, this.lastNo);
		if((this.endNo-this.startNo) < this.numCount){
			this.startNo = Math.max(this.endNo-this.numCount, 1);
		}
	}


	/**
	 * 获得排序字段,无默认值. 多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 获得排序方向, 无默认值.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		String lowcaseOrder = StringUtils.lowerCase(order);

		//检查order字符串的合法值
		String[] orders = StringUtils.split(lowcaseOrder, ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this.order = lowcaseOrder;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}
	
	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageNo   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
	
	/**
	 * 设置page对象内的内容
	 * @param page
	 * @param start
	 * @param totalCount
	 * @param data
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setPageValue(Page page, int start, long totalCount, List result) {
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int pageCount = (int) Math.ceil((double)totalCount/pageSize);
		pageNo = Math.min(pageNo, pageCount);
		pageNo = Math.max(1, pageNo);
		int lastNo = pageCount;
		int nextNo = Math.min(pageCount, pageNo+1);
		int prevNo = Math.max(1, pageNo-1);
		int startNo = Math.max(pageNo-DEFAULT_NUM_COUNT/2, DEFAULT_FIRST_NO);
		int endNo = Math.min(startNo+DEFAULT_NUM_COUNT, lastNo);
		if((endNo-startNo) < DEFAULT_NUM_COUNT){
			startNo = Math.max(endNo-DEFAULT_NUM_COUNT, 1);
		}
		page.setPageNo(pageNo);
		page.setPageCount(pageCount);
		page.setLastNo(lastNo);
		page.setNextNo(nextNo);
		page.setPrevNo(prevNo);
		page.setStartNo(startNo);
		page.setEndNo(endNo);
		page.setStart(start);
		page.setTotalCount(totalCount);
		page.setResult(result);
		return;
	}
	
	public void setPageSize(String pageSize) {
		if(org.springframework.util.StringUtils.hasText(pageSize)){
			this.pageSize = Integer.parseInt(pageSize);
		} else {
			this.pageSize = DEFAULT_PAGE_SIZE;
		}
	}
	
	public void setPageNo(String pageNo) {
		if (org.springframework.util.StringUtils.hasText(pageNo)) {
			this.pageNo = Integer.parseInt(pageNo);
		} else {
			this.pageNo = DEFAULT_PAGE_NO;
		}
	}
	
	public List<T> getResult() {
		return result;
	}

	public void setResult(final List<T> result) {
		this.result = result;
	}

	public int getFirstNo() {
		return firstNo;
	}

	public void setFirstNo(int firstNo) {
		this.firstNo = firstNo;
	}

	public int getLastNo() {
		return lastNo;
	}

	public void setLastNo(int lastNo) {
		this.lastNo = lastNo;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getPrevNo() {
		return prevNo;
	}

	public void setPrevNo(int prevNo) {
		this.prevNo = prevNo;
	}

	public int getNextNo() {
		return nextNo;
	}

	public void setNextNo(int nextNo) {
		this.nextNo = nextNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	public int getNumCount() {
		return numCount;
	}

	public void setNumCount(int numCount) {
		this.numCount = numCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	
}
