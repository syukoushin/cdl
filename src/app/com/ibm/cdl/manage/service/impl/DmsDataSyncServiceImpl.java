package com.ibm.cdl.manage.service.impl;

import com.bjev.dms.*;
import com.ibm.cdl.datamap.action.DataMapUtils;
import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.manage.pojo.Invoice;
import com.ibm.cdl.manage.pojo.License;
import com.ibm.cdl.manage.service.DmsDataSyncService;
import com.ibm.core.util.MD5Utils;
import org.apache.axis.AxisFault;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 与DMS接口服务实现类
 */
@Service("dmsDataSyncService")
public class DmsDataSyncServiceImpl implements DmsDataSyncService {

	private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-mm-dd");
	/**
	 * 检查车辆是否实销
	 * @param vin
	 * @return
	 */
	public String checkVin(String vin) {
		String flag = "";
		String username = DataMapUtils.getDataMapSub(Constants.DMS_INFO,Constants.DMS_USER_NAME);
		String pwd = DataMapUtils.getDataMapSub(Constants.DMS_INFO,Constants.DMS_PASSWORD);
		try {
			OclToDmsServiceImplService locator = new OclToDmsServiceImplServiceLocator();
			OclCarTransportInformation oclInfo = new OclCarTransportInformation();
			oclInfo.setUSERNAME("Dms&Ocl");
			oclInfo.setPASSWORD(MD5Utils.GetMD5Code("Dms&Ocl@DO"));
			OclCarSalesMessage oclCarSalesMessage = new OclCarSalesMessage();
			oclCarSalesMessage.setXSVINM(vin);
			OclCarSalesMessage[] msgArr = new OclCarSalesMessage[1];
			msgArr[0] = oclCarSalesMessage;
			oclInfo.setOclCarSalesMessage(msgArr);
			OclToDmsService service = locator.getHTTP_Port();
			OclCarSalesBack back[] = service.savecarsales(oclInfo);
			for(OclCarSalesBack te : back){

				// 实销的场合
				 if("Y".equals(te.getKCSXZT())){
					flag = "0";

				// 未实销的场合
				 } else if("N".equals(te.getKCSXZT())){
					flag = "0";

				// dms中不存在此车
				} else {
					flag = "1";
				}
			}

		} catch (AxisFault e) {
			flag="-1";
			e.printStackTrace();
		} catch (RemoteException e) {
			flag="-2";
			e.printStackTrace();
		} catch (ServiceException e) {
			flag="-3";
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 发送发票信息到DMS系统
	 * @param invoiceList
	 * @return
	 */
	public String pushInvoiceDataToDms(List<Invoice> invoiceList) {
		String flag = "0";
		String username = DataMapUtils.getDataMapSub(Constants.DMS_INFO,Constants.DMS_USER_NAME);
		String pwd = DataMapUtils.getDataMapSub(Constants.DMS_INFO,Constants.DMS_PASSWORD);
		Map<String,String> reMap = new HashMap<String, String>();
		try {
			OclToDmsServiceImplService locator = new OclToDmsServiceImplServiceLocator();
			OclInvoiceTransportInformation oclInfo = new OclInvoiceTransportInformation();
			oclInfo.setUSERNAME("Dms&Ocl");
			oclInfo.setPASSWORD(MD5Utils.GetMD5Code("Dms&Ocl@DO"));
			OclInvoiceMessage[] msgArr = new OclInvoiceMessage[invoiceList.size()];
			for(int i=0; i<invoiceList.size(); i++){
				Invoice temp = invoiceList.get(i);
				OclInvoiceMessage m = new OclInvoiceMessage();
				// 车架号
				m.setXSVINM(temp.getFrameNo());
				Long l = new Long(temp.getPrintDate().getTime());
				// 开票日期
				m.setKCKPRQ(Integer.parseInt(ymd.format(temp.getPrintDate()).replace("-","")));
				// 发票号
				m.setKCFPHM(temp.getNumber());
				// 开票金额
				m.setKCKPJE(Double.valueOf(temp.getTax().toString()));
				// 发票代码
				m.setKCFPDM(temp.getInvoiceNo());
				// 车牌型号
				// 客户名称
				// 身份证号/纳税人识别号
				m.setKCSFZH(temp.getName());
				// 合格证号
				// 发动机号码
				msgArr[i] = m;

			}
			oclInfo.setOclInvoiceMessage(msgArr);
			OclToDmsService service = locator.getHTTP_Port();
			OclInvoiceAndDrivingLicenseBack back[] = service.saveoclinvoice(oclInfo);
			for(OclInvoiceAndDrivingLicenseBack te : back){
				reMap.put(te.getXSVINM(),te.getKCCSZT());
				if("E".equals(te.getKCCSZT())){
					flag="1";
					break;
				}
			}
		} catch (AxisFault e) {
			flag="-1";
			e.printStackTrace();
		} catch (RemoteException e) {
			flag="-2";
			e.printStackTrace();
		} catch (ServiceException e) {
			flag="-3";
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 发送行驶证信息到DMS系统
	 * @param licenseList
	 * @return
	 */
	public String pushLicenseDataToDms(List<License> licenseList) {
		String flag = "0";
		String username = DataMapUtils.getDataMapSub(Constants.DMS_INFO,Constants.DMS_USER_NAME);
		String pwd = DataMapUtils.getDataMapSub(Constants.DMS_INFO,Constants.DMS_PASSWORD);
		Map<String,String> reMap = new HashMap<String, String>();
		try {
			OclToDmsServiceImplService locator = new OclToDmsServiceImplServiceLocator();
			OclDrivingLicenseTransportInformation oclInfo = new OclDrivingLicenseTransportInformation();
			oclInfo.setUSERNAME("Dms&Ocl");
			oclInfo.setPASSWORD(MD5Utils.GetMD5Code("Dms&Ocl@DO"));
			OclDrivingLicenseMessage[] msgArr = new OclDrivingLicenseMessage[licenseList.size()];
			for(int i=0; i<licenseList.size(); i++){
				License temp = licenseList.get(i);
				OclDrivingLicenseMessage m = new OclDrivingLicenseMessage();
				// 车架号
				m.setXSVINM(temp.getCarNo());
				// 注册日期
				m.setKCZCRQ(Integer.parseInt(ymd.format(temp.getRegistDate()).replace("-", "")));
				// 行驶证号
				// 所有人（客户姓名）
				m.setKCKHXM(temp.getName());
				// 地址
				m.setKCKHDZ(temp.getAddress());
				// 使用性质
				m.setKCSYXZ(temp.getUseType());
				// 品牌型号
				m.setKCPPXH(temp.getBandNo());
				// 发动机号码
				m.setKCFDJH(temp.getEnginNo());

				msgArr[i] = m;

			}
			oclInfo.setOclDrivingLicenseMessage(msgArr);
			OclToDmsService service = locator.getHTTP_Port();
			OclInvoiceAndDrivingLicenseBack back[] = service.saveocldriving(oclInfo);
			for(OclInvoiceAndDrivingLicenseBack te : back){
				reMap.put(te.getXSVINM(),te.getKCCSZT());
				if("E".equals(te.getKCCSZT())){
					flag="1";
					break;
				}
			}
		} catch (AxisFault e) {
			flag="-1";
			e.printStackTrace();
		} catch (RemoteException e) {
			flag="-2";
			e.printStackTrace();
		} catch (ServiceException e) {
			flag="-3";
			e.printStackTrace();
		}
		return flag;
	}
	
	public static void main(String[] args) throws ParseException {

		String flag = "";
		try {
			OclToDmsServiceImplService locator = new OclToDmsServiceImplServiceLocator();
			OclInvoiceTransportInformation oclInfo = new OclInvoiceTransportInformation();
			oclInfo.setUSERNAME("Dms&Ocl");
			oclInfo.setPASSWORD(MD5Utils.GetMD5Code("Dms&Ocl@DO"));

			List<Invoice> invoiceList = new ArrayList<Invoice>();
			Invoice invoice = new Invoice();
			invoice.setFrameNo("LNBSCC3W8HF320812");
			invoice.setName("111111");
			invoice.setNumber("00278382");
			invoice.setInvoiceNo("112001420160");
			invoice.setTax(new BigDecimal(89000));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyymmdd");
			invoice.setPrintDate(sdf.parse("2017-01-01"));
			invoiceList.add(invoice);
			invoiceList.add(invoice);
			OclInvoiceMessage[] msgArr = new OclInvoiceMessage[invoiceList.size()];
			for(int i=0; i<invoiceList.size(); i++){
				Invoice temp = invoiceList.get(i);
				OclInvoiceMessage m = new OclInvoiceMessage();
				// 车架号
				m.setXSVINM(temp.getFrameNo());
				Long l = new Long(temp.getPrintDate().getTime());
				// 开票日期
				m.setKCKPRQ(Integer.valueOf(sdf2.format(temp.getPrintDate())));
				// 发票号
				m.setKCFPHM(temp.getNumber());
				// 开票金额
				m.setKCKPJE(Double.valueOf(temp.getTax().toString()));
				// 发票代码
				m.setKCFPDM(temp.getInvoiceNo());
				// 车牌型号
				// 客户名称
				// 身份证号/纳税人识别号
				m.setKCSFZH(temp.getName());
				// 合格证号
				// 发动机号码
				msgArr[i] = m;

			}
			oclInfo.setOclInvoiceMessage(msgArr);
			OclToDmsService service = locator.getHTTP_Port();
			OclInvoiceAndDrivingLicenseBack back[] = service.saveoclinvoice(oclInfo);
			for(OclInvoiceAndDrivingLicenseBack te : back){
				System.out.println(te.getXSVINM());
				System.out.println(te.getKCCSZT());
				System.out.println(te.getKCCSBZ());
			}
			flag = "1";

		} catch (AxisFault e) {
			flag="-1";
			e.printStackTrace();
		} catch (RemoteException e) {
			flag="-2";
			e.printStackTrace();
		} catch (ServiceException e) {
			flag="-3";
			e.printStackTrace();
		}

	}

}