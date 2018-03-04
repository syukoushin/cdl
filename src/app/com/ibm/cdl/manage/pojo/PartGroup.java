package com.ibm.cdl.manage.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 分公司实体类
 * @author zhuxiangxin
 *
 */
public class PartGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8023334088673007751L;
	// id
	private String id;
	// 分公司code
	private String code;
	// 分公司名称
	private String name;
	// 创建时间
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());
	
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
