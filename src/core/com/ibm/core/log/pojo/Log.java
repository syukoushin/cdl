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
public class Log implements Serializable {
	public static String SUCCESS = "0";
	public static String FAIL = "1";
	private String id;
	private String userCode;// 访问用户
	private String operation;//操作
	private String method;//方法
	private String result;//结果
	private String status;
	private String note;//描述
	private String beginDate;
	private String endDate;
	private Timestamp oprationTime;// 操作时间
	public Log(String userCode,String method,String operation,String result,String status, String note) {
		this.userCode = userCode;
		this.method = method;
		this.operation = operation;
		this.result = result;
		this.status = status;
		this.note = note;
		this.oprationTime = new Timestamp(System.currentTimeMillis());
	}
	
	public Log() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public Timestamp getOprationTime() {
		return oprationTime;
	}

	public void setOprationTime(Timestamp oprationTime) {
		this.oprationTime = oprationTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
