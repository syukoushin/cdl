package com.ibm.cdl.datamap.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kelvin
 */
public class DataMap {

	private String id;
	private String name;// 名称
	private String code;
	private String remark;// 备注
	private String createPeople;
	private Date createDate;
	private String modifyPeople;
	private Date modifyDate;

	private List<SubDataMap> subs = new ArrayList<SubDataMap>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatePeople() {
		return createPeople;
	}

	public void setCreatePeople(String createPeople) {
		this.createPeople = createPeople;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(String modifyPeople) {
		this.modifyPeople = modifyPeople;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public List<SubDataMap> getSubs() {
		return subs;
	}

	public void setSubs(List<SubDataMap> subs) {
		this.subs = subs;
	}
	
}
