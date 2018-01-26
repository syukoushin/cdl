package com.ibm.cdl.manage.dao;

import org.springframework.stereotype.Repository;
import com.ibm.cdl.manage.pojo.Invoice;
import com.ibm.core.orm.hibernate.SessionFactoryDao;

@Repository("invoiceDao")
public class InvoiceDao extends SessionFactoryDao<Invoice, String> {
	
}
