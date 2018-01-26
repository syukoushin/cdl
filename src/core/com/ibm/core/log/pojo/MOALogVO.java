package com.ibm.core.log.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 * 业务日志功能
 * Created on 2013-4-1
 * Copyright: Copyright (c) 2012
 * @version 1.0
 */
public class MOALogVO implements Serializable {
	private String sourceId;//资源ID
	private String menuId;//隶属菜单
	private String operation;//操作
	private String note;//描述
	private String courseItemName;
	private String courseId;
	private String type;
	private String grandParId;
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCourseItemName() {
		return courseItemName;
	}
	public void setCourseItemName(String courseItemName) {
		this.courseItemName = courseItemName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGrandParId() {
		return grandParId;
	}
	public void setGrandParId(String grandParId) {
		this.grandParId = grandParId;
	}
}
