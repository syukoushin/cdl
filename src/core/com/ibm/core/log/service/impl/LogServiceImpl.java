package com.ibm.core.log.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.core.log.dao.LogDao;
import com.ibm.core.log.pojo.Log;
import com.ibm.core.log.service.LogService;
import com.ibm.core.orm.Page;
@Service("logService")
public class LogServiceImpl implements LogService {
	@Autowired
	private LogDao logDao;
	
	public void saveLog(Log log) {
		new SaveLogDBThread(log,logDao).run();
	}
	
	public Page<Log> findPage(Page<Log> page,Log log) {
		return logDao.findPage(page, log);
	}

	public Log get(String id) {
		return logDao.get(id);
	}

}



class SaveLogDBThread extends Thread{

	private Log log;
	@Autowired
	private LogDao logDao;
	
	private SaveLogDBThread(){
		
	}
	public SaveLogDBThread(Log log,LogDao logDao) {
		this.log = log;
		this.logDao = logDao;
	}
	public void run() {
		logDao.save(log);
//		SaveLogImpl.getInstance().getLogDao().save(log);
	}
}