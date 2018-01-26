package com.ibm.core.page;
import java.util.ArrayList;
import java.util.List;

public class PageResults<T>  implements java.io.Serializable
{

	private static final long serialVersionUID = 1997931781872368542L;
	public boolean isASC()
    {
        return isASC;
    }

    public void setASC(boolean isASC)
    {
        this.isASC = isASC;
    }

    public PageResults()
    {
        results = new ArrayList<T>();
        totalCount = 0;
        pageSize = 15;
        pageNo = 1;
    }

    public List<T> getResults()
    {
        return results;
    }

    public void setResults(List<T> results)
    {
        this.results = results;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getPageCount()
    {
        return ((totalCount + pageSize) - 1) / pageSize;
    }

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        if(pageNo <= 0)
        	pageNo = 1;
        this.pageNo = pageNo;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    public int getResultsFrom()
    {
        return (pageNo - 1) * pageSize + 1;
    }

    public int getResultsEnd()
    {
        return pageSize * pageNo;
    }

    private List<T> results;
    private int totalCount;
    private int pageSize;
    private int pageNo;
    private String orderBy;
    private boolean isASC;
}
