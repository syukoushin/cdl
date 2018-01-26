package com.ibm.core.log.service;

import java.util.List;
import java.util.Map;

import com.ibm.core.log.pojo.Log;
import com.ibm.core.orm.Page;

public interface LogService {
	
	public void saveLog(Log log);
	
	public Page<Log> findPage(Page<Log> page,Log log);
	
	public Log get(String id);
	
}
