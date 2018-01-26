package com.ibm.core.log.action;

import com.ibm.core.action.DefaultBaseAction;
import com.ibm.core.log.pojo.Log;
import com.ibm.core.log.service.LogService;
import com.ibm.core.orm.Page;

public class LogAction extends DefaultBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6350537226017757587L;

	private Log log = new Log();

	private Page<Log> res = new Page<Log>();
	
	private LogService logService;
	private String results;
	
	public String index() throws Exception {
		res = logService.findPage(res, log);
		return LIST;
	}
	
	public String getResultDetail() throws Exception {
		results = logService.get(log.getId()).getResult();
		this.sendResponseMessage(results);
		return null;
	}
	
	public String getContent() throws Exception {
		results = logService.get(log.getId()).getOperation();
		this.sendResponseMessage(results);
		return null;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Page<Log> getRes() {
		return res;
	}

	public void setRes(Page<Log> res) {
		this.res = res;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}
}