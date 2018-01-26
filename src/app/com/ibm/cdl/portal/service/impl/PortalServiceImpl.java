package com.ibm.cdl.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibm.cdl.manage.service.UserService;
import com.ibm.cdl.portal.service.PortalService;

@Service("portalService")
public class PortalServiceImpl implements PortalService {
	
	@Autowired
	private UserService userService;
	
	public boolean login(String userCode, String password) {
		Boolean result= false;
		return result;
	}

	
}