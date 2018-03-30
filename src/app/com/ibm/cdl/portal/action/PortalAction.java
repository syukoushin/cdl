package com.ibm.cdl.portal.action;

import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.attachment.service.AttachmentService;
import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.manage.pojo.*;
import com.ibm.cdl.manage.service.*;
import com.ibm.core.action.DefaultBaseAction;
import com.ibm.core.orm.Page;
import com.ibm.core.util.DateJsonValueProcessor;
import com.ibm.core.util.EncodeUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户端接口
 */
public class PortalAction extends DefaultBaseAction {
	Logger logger = Logger.getLogger(PortalAction.class);
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private LicenseService licenseService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private IdCardService idCardService;
	@Autowired
	private BusinessLicenseService businessLicenseService;
	@Autowired
	private UserService userService;
	private File[] attachFile;                              //附件上传对象
	private String[] attachFileFileName;                    //附件名称
	private String[] attachFileContentType;                 //附件类型
	private List<Attachment> attachments = new ArrayList<Attachment>();               //附件列表
	private String resultJSON=null;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyy-MM-dd");


    /**
     * app登录
     * @return
     */
    public String appLogin(){
    	JSONObject json = new JSONObject();
		try{
			String userCode = getParameter("userCode");
			String password = getParameter("passWord");
			if(userCode == null || password == null 
					|| "".equals(userCode) || "".equals(password)){
				json.put("optSts", "2");
				json.put("optMsg", "参数错误");
			}
			password = DigestUtils.md5Hex(password);
			User user = userService.getUserByUserCodeAndType(userCode,Constants.APP_USER);
			if(user != null){
				if(user.getPassword()!= null && password.equals(user.getPassword())){
					json.put("optSts", "0");
					json.put("optMsg", "登录成功");
				} else {
					json.put("optSts", "1");
					json.put("optMsg", "用户名或密码错误");
				}
			} else {
				json.put("optSts", "1");
				json.put("optMsg", "用户名或密码错误");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			json.put("optSts", "1");
			json.put("optMsg", "登录失败");
		}finally{
			this.sendResponseMessage(json.toString());
		}
		return null;
    }

	/**
	 *
	 * 注册
	 * @return
	 */
	public String regist() {
		JSONObject json = new JSONObject();
		try{
			String password= getParameter("password");
			String userCode= getParameter("phoneNumber");
			String userName = getParameter("userName");
			String deptName = getParameter("deptName");

			// 检查userCode是否存在
			if(StringUtils.isNotEmpty(userCode) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(deptName)){
				if(userService.checkExistUserCode(userCode)){
					json.put("optSts", "2");
					json.put("optMsg", "该用户已存在");
				} else {
					User engineer = new User();
					engineer.setUserName(userName);
					engineer.setUserCode(userCode);
					engineer.setPassword(DigestUtils.md5Hex(password));
					engineer.setDeptName(deptName);
					engineer.setJobLevel(Constants.USER_COMMON);
					engineer.setType(Constants.APP_USER);
					userService.saveEntity(engineer);
					json.put("optSts", "0");
					json.put("optMsg", "成功");
				}
			} else {
				json.put("optSts", "1");
				json.put("optMsg", "用户名为空");
			}

		}
		catch(Exception e){
			json.put("optSts", "1");
			json.put("optMsg", "失败");
		}finally{
			this.sendResponseMessage(json.toString());
		}
		return null;
	}
    
    
    /**
     * 添加行驶证信息
     * @return
     */
    public String addLicense(){
    	JSONObject json = new JSONObject();
		try{
			String name = getParameter("name");
			String cardNo = getParameter("cardNo");
			String carType = getParameter("carType");
			String address = getParameter("address");
			String bandNo = getParameter("bandNo");
			String carNo = getParameter("carNo");
			String useType = getParameter("useType");
			String enginNo = getParameter("enginNo");
			String registDate = getParameter("registDate");
			String passDate = getParameter("passDate");
			String createUser = getParameter("createUser");
			License license = new License();
			license.setAddress(address);
			license.setBandNo(bandNo);
			license.setCarNo(carNo);
			license.setCardNo(cardNo);
			license.setCarType(carType);
			license.setEnginNo(enginNo);
			logger.info("name:"+name+",cardNo:"+cardNo+","+"carType:"+carType+",address:"+address
			+",bandNo:"+bandNo+",carNo:"+cardNo+",useType:"+useType+",enginNo:"+enginNo+",registDate:"+registDate
			+",passDate:"+passDate+",createUser:"+createUser);
			if(registDate.contains("-")){
				license.setRegistDate(sdf2.parse(registDate));
			} else {
				license.setRegistDate(sdf.parse(registDate));
			}
			if(passDate.contains("-")){
				license.setPassDate(sdf2.parse(passDate));
			} else {
				license.setPassDate(sdf.parse(passDate));
			}
			license.setUseType(useType);
			license.setName(name);
			license.setDmsFlag("0");
			license.setCreateUser(createUser);
			
			String result = licenseService.save(license);
			if("0".equals(result)){
				json.put("optSts", "0");
				json.put("objId", license.getId());
				json.put("optMsg", "成功");
			} else if("1".equals(result)){
				json.put("optSts","1");
				json.put("optMsg", "dms库中不存在此车");
			} else if("2".equals(result)){
				json.put("optSts","2");
				json.put("optMsg", "推送dms系统失败");
			} else if("3".equals(result)){
				json.put("optSts","2");
				json.put("optMsg", "该行驶证信息已存在");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			json.put("optSts", "1");
			json.put("optMsg", "添加失败");
		}finally{
			this.sendResponseMessage(json.toString());
		}
		return null;
    }
    
    /**
     * 添加发票信息
     * @return
     */
    public String addInvoice(){
    	JSONObject json = new JSONObject();
    	try{
    		String name = getParameter("name");
    		String printDate = getParameter("printDate");
    		String number = getParameter("number");
    		String invoiceNo = getParameter("invoiceNo");
    		String frameNo = getParameter("frameNo");
    		String tax = getParameter("tax");
    		String createUser = getParameter("createUser");
			/** add  by zhuxiangxin 增加购买方名称、身份证/组织机构代码、厂牌型号、合格证号、发动机号码 字段 2018-03-25**/
			// 购买方名称
			String buyerName = getParameter("buyerName");
			// 身份证/组织机构代码
			String idCard = getParameter("idCard");
			// 厂牌型号
			String bandNo = getParameter("bandNo");
			// 合格证号
			String okNo = getParameter("okNo");
			// 发动机号码
			String enginNo = getParameter("enginNo");
			/** add end **/
    		Invoice invoice = new Invoice();
    		invoice.setName(name);
			if(printDate.contains("-")){
				invoice.setPrintDate(sdf2.parse(printDate));
			} else {
				invoice.setPrintDate(sdf.parse(printDate));
			}
    		invoice.setInvoiceNo(invoiceNo);
    		invoice.setNumber(number);
    		invoice.setFrameNo(frameNo);
    		invoice.setTax(new BigDecimal(tax));
    		invoice.setCreateUser(createUser);
			invoice.setDmsFlag("0");
			/** add  by zhuxiangxin 增加购买方名称、身份证/组织机构代码、厂牌型号、合格证号、发动机号码 字段 2018-03-25**/
			invoice.setBuyerName(buyerName);
			invoice.setBandNo(bandNo);
			invoice.setIdCard(idCard);
			invoice.setOkNo(okNo);
			invoice.setEnginNo(enginNo);
			/** add end **/
    		String result = invoiceService.save(invoice);
			if("0".equals(result)){
				json.put("optSts", "0");
				json.put("objId", invoice.getId());
				json.put("optMsg", "成功");
			} else if("1".equals(result)){
				json.put("optSts","1");
				json.put("optMsg", "dms库中不存在此车");
			} else if("2".equals(result)){
				json.put("optSts","2");
				json.put("optMsg", "推送dms系统失败");
			} else if("3".equals(result)){
				json.put("optSts","3");
				json.put("optMsg", "该发票信息已存在");
			}
    	}catch(Exception e){
    		e.printStackTrace();
    		json.put("optSts", "-1");
    		json.put("optMsg", "添加失败");
    	}finally{
    		this.sendResponseMessage(json.toString());
    	}
    	return null;
    }


	/**
	 * 添加身份证信息
	 * @return
	 */
	public String addIdCard(){
		JSONObject json = new JSONObject();
		try{
			String name = getParameter("name");
			String cardNo = getParameter("cardNo");
			String address = getParameter("address");
			String sex = getParameter("sex");
			String ethnic = getParameter("ethnic");
			String createUser = getParameter("createUser");
			String birthday = getParameter("birthDay");
			IdCard idCard = new IdCard();
			idCard.setName(name);
			idCard.setSex(sex);
			idCard.setAddress(address);
			idCard.setEthnic(ethnic);
			idCard.setCreateUser(createUser);
			idCard.setCardNo(cardNo);
			if(birthday.contains("-")){
				idCard.setBirthDay(sdf2.parse(birthday));
			} else {
				idCard.setBirthDay(sdf.parse(birthday));
			}
			idCardService.save(idCard);
			json.put("optSts", "0");
			json.put("objId", idCard.getId());
			json.put("optMsg", "成功");

		}catch(Exception e){
			e.printStackTrace();
			json.put("optSts", "1");
			json.put("optMsg", "添加失败");
		}finally{
			this.sendResponseMessage(json.toString());
		}
		return null;
	}

	/**
	 * 添加营业执照信息
	 * @return
	 */
	public String addBusinessLicense(){
		JSONObject json = new JSONObject();
		try{
			String name = getParameter("name");
			String creditCode = getParameter("creditCode");
			String regNumber = getParameter("regNumber");
			String address = getParameter("address");
			String endDate = getParameter("endDate");

			String incorporator = getParameter("incorporator");
			String createUser = getParameter("createUser");
			BusinessLicense businessLicense = new BusinessLicense();
			businessLicense.setName(name);
			businessLicense.setCreditCode(creditCode);
			businessLicense.setRegNumber(regNumber);
			businessLicense.setAddress(address);
			businessLicense.setEndDate(endDate);
			businessLicense.setIncorporator(incorporator);
			businessLicense.setCreateUser(createUser);
			businessLicenseService.save(businessLicense);
			json.put("optSts", "0");
			json.put("objId", businessLicense.getId());
			json.put("optMsg", "成功");

		}catch(Exception e){
			e.printStackTrace();
			json.put("optSts", "1");
			json.put("optMsg", "添加失败");
		}finally{
			this.sendResponseMessage(json.toString());
		}
		return null;
	}

    /**
     * 获取分页历史记录
     * @return
     */
    public String getPage(){
    	JSONObject json = new JSONObject();
    	try{
    		
    		String type = getParameter("type");
    		String userCode = getParameter("userCode");
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
			config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    		// 判断分类 查询的是 什么的分页, 行驶证的场合
    		if(Constants.SHOW_TYPE_LICENSE.equals(type)){
    			License entity = new License();
    			entity.setCreateUser(userCode);
    			Page<License> page = new Page<License>();
    			page.setPageNo(pageNo);
    			page.setPageSize(pageSize);
    			Page<License> result = licenseService.findPageForClient(entity, page);
    			json.put("optSts", "0");

    			JSONObject resJson =  JSONObject.fromObject(result, config);
    			json.put("data", resJson);
        		json.put("optMsg", "请求成功");

				// 发票的场合
    		} else if(Constants.SHOW_TYPE_INVOICE.equals(type)){
				Invoice entity = new Invoice();
				entity.setCreateUser(userCode);
				Page<Invoice> page = new Page<Invoice>();
				page.setPageNo(pageNo);
				page.setPageSize(pageSize);
				Page<Invoice> result = invoiceService.findPageForClient(entity, page);
				json.put("optSts", "0");
				JSONObject resJson =  JSONObject.fromObject(result, config);
				json.put("data", resJson);
				json.put("optMsg", "请求成功");

				// 身份证的场合
    		} else if(Constants.SHOW_TYPE_IDCARD.equals(type)){
				IdCard entity = new IdCard();
				entity.setCreateUser(userCode);
				Page<IdCard> page = new Page<IdCard>();
				page.setPageNo(pageNo);
				page.setPageSize(pageSize);
				Page<IdCard> result = idCardService.findPageForClient(entity, page);
				json.put("optSts", "0");
				JSONObject resJson =  JSONObject.fromObject(result, config);
				json.put("data", resJson);
				json.put("optMsg", "请求成功");
    			
    		} else if(Constants.SHOW_TYPE_BUSINESS.equals(type)){
				BusinessLicense entity = new BusinessLicense();
				entity.setCreateUser(userCode);
				Page<BusinessLicense> page = new Page<BusinessLicense>();
				page.setPageNo(pageNo);
				page.setPageSize(pageSize);
				Page<BusinessLicense> result = businessLicenseService.findPageForClient(entity, page);
				json.put("optSts", "0");
				JSONObject resJson =  JSONObject.fromObject(result, config);
				json.put("data", resJson);
				json.put("optMsg", "请求成功");
    		} else {
    			json.put("optSts", "2");
        		json.put("optMsg", "参数错误");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		json.put("optSts", "1");
    		json.put("optMsg", "添加失败");
    	}finally{
    		this.sendResponseMessage(json.toString());
    	}
    	return null;
    }
    
    
    /**
	 * ajax上传附件
	 * @return
	 */
	public String ajaxUploadFile(){
		JSONObject json = new JSONObject();
		try{
			String id = getParameter("id");
			String createUser = getParameter("createUser");
			if(attachFile != null && attachFile.length > 0){
				String attachId = attachmentService.saveAttachment(id, createUser, attachFile[0],
						attachFileFileName[0], attachFileContentType[0]);
				if(attachId == null){
					json.put("optSts", "2");
					json.put("optMsg", "文件服务器保存附件失败");
				} else {
					json.put("fileName", attachFileFileName[0]);
					json.put("attachId", attachId);
				}
			}
			json.put("optSts", "0");
			json.put("optMsg", "保存附件成功");
		}catch(Exception e){
			e.printStackTrace();
			json.put("optSts", "1");
			json.put("optMsg", "保存附件失败");
		}finally{
			render("text/html",json.toString());
		}
		return null;
	}

	public File[] getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(File[] attachFile) {
		this.attachFile = attachFile;
	}

	public String[] getAttachFileFileName() {
		return attachFileFileName;
	}

	public void setAttachFileFileName(String[] attachFileFileName) {
		this.attachFileFileName = attachFileFileName;
	}

	public String[] getAttachFileContentType() {
		return attachFileContentType;
	}

	public void setAttachFileContentType(String[] attachFileContentType) {
		this.attachFileContentType = attachFileContentType;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getResultJSON() {
		return resultJSON;
	}

	public void setResultJSON(String resultJSON) {
		this.resultJSON = resultJSON;
	}

	public static void main(String[] arg){
		String name = "木村崔仔";
		String after = EncodeUtils.urlEncode(name);
		System.out.println(after);
	}
}
