package com.ibm.cdl.manage.dao;

import org.springframework.stereotype.Repository;
import com.ibm.cdl.manage.pojo.License;
import com.ibm.core.orm.hibernate.SessionFactoryDao;

@Repository("licenseDao")
public class LicenseDao extends SessionFactoryDao<License, String> {
	
}
