package com.ibm.cdl.manage.service;

import com.ibm.cdl.manage.pojo.Invoice;
import com.ibm.cdl.manage.pojo.License;

import java.util.List;

/**
 * 与dms接口服务类
 * @author zhuxiangxin
 *
 */
public interface DmsDataSyncService {

	/**
	 * 检查vin是否实销
	 * @param vin
	 * @return
	 */
	String checkVin(String vin);


	
	/**
	 * 向dms推送发票信息
	 * @param invoiceList
	 * @return
	 */
	String pushInvoiceDataToDms(List<Invoice> invoiceList);
	
	/**
	 * 向dms推送行驶证信息
	 * @param licenseList
	 */
	String pushLicenseDataToDms(List<License> licenseList);
}
