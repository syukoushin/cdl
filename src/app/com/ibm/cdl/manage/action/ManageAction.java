package com.ibm.cdl.manage.action;

import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.attachment.service.AttachmentService;
import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.manage.pojo.License;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.cdl.manage.service.LicenseService;
import com.ibm.cdl.manage.service.UserService;
import com.ibm.core.action.DefaultBaseAction;
import com.ibm.core.orm.Page;
import com.ibm.core.util.DateJsonValueProcessor;
import com.ibm.core.util.DesUtils;
import com.ibm.core.util.excel.ExportExcel;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ManageAction extends DefaultBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserService userService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private LicenseService	licenseService;
	
	private License entity = new License();
	private Page<License> res = new Page<License>();   //分页对象
	private String resultJSON=null;
	private List<Attachment> attachmentList = new ArrayList<Attachment>();
	private String result = null;

	private String menuFlag = null;
    
    /**
     * 登录
     * @return
     */
    public String login(){
		String userCode = getParameter("userCode");
		String password = getParameter("passWord");
		password = DesUtils.strDec(password, "1","2","3");
		password = DigestUtils.md5Hex(password);
		User user = userService.getUserByUserCodeAndTypeForPc(userCode,Constants.USER_FOURTH);
		if(user != null){
			if(user.getPassword()!= null && password.equals(user.getPassword())){
				getRequest().getSession().setAttribute("CURRENT_USER", user);
				return  goAction("/manage/Manage_toList.do?tagTopFlag=dbgl&tagFlag=wdjs");
			} else {
				return returnScriptAlertWindow("用户名或密码错误");
			}
		} else {
			return returnScriptAlertWindow("用户名或密码错误");
		}
    }
    
    /**
     * 退出
     * @return
     */
    public String logOut(){
    	getRequest().getSession().setAttribute("CURRENT_USER", null);
    	return "success";
    }
    
    
    /**
	 * 跳转到订单列表页面
	 * @return
	 */
	public String toList(){
		getSession().setAttribute("CURRENT_MENU",Constants.MENU_LICENSE);
		return goUrl("list");
	}
	
	/**
	 * 获取分页列表
	 * @return
	 */
	public String ajaxLicenseList(){
		JSONObject json = new JSONObject();
		try{
			res.setPageNo(pageNo);
			User currentUser = getSessionUser();
			res = licenseService.findPage(entity ,res,currentUser);
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy/MM/dd"));
			JSONObject resJson =  JSONObject.fromObject(res, config);
			json.put("optSts", "0");
			json.put("data", resJson);
			json.put("optMsg", "获取列表成功！");
		}catch(Exception e){
			e.printStackTrace();
			json.put("optSts", "1");
			json.put("optMsg", "获取列表失败！");
		}finally{
			this.sendResponseMessage(json.toString());
		}
		return null;
	}
	
	/**
	 * 跳转到详情页面
	 * @return
	 */
	public String toLicenseDetail(){
		String id = getParameter("id");
		try {
			entity = licenseService.findEntityById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return  goAction("/manage/Manage_toList.do?tagTopFlag=dbgl&tagFlag=wdjs");
		}
		// 获取附件信息
		attachmentList = attachmentService.findAttachmentsByBusinessId(id);
		return goUrl("detail");
	}
    
	/**
	 * 下载EXCEL
	 * @return
	 */
	public String exportExcel() {
		try{
			User currentUser = getSessionUser();
			List<License> result = licenseService.findListBy(entity,currentUser);
			ExportExcel tempExcel = new ExportExcel("",License.class);
			tempExcel.setDataList(result);
			String time = System.currentTimeMillis()+"";
			tempExcel.write(getResponse(),"行驶证列表-"+time+".xlsx");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	} 
	
	
    /**
     * 修改密码
     * @return
     */
    public String modifyPwd(){
    	JSONObject json = new JSONObject();
		try{
			User cu = (User)getSessionUser();
			userCode = cu.getUserCode();
			String oldPwd = getParameter("oldPwd");
			String password = getParameter("passWord");
			oldPwd = DigestUtils.md5Hex(oldPwd);
			User user = userService.getUserByUserCodeAndType(userCode,"1");
			if(oldPwd.equals(user.getPassword())){
				user.setPassword(DigestUtils.md5Hex(password));
				getSession().setAttribute("CURRENT_USER", user);
				userService.updateEntity(user);
				json.put("optSts", "0");
				json.put("optMsg", "修改密码成功");
			} else {
				json.put("optSts", "1");
				json.put("optMsg", "修改密码失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			json.put("optSts", "1");
			json.put("optMsg", "修改密码失败");
		}finally{
			this.sendResponseMessage(json.toString());
		}
    	return null;
    }
    
    
	public String getResultJSON() {
		return resultJSON;
	}

	public void setResultJSON(String resultJSON) {
		this.resultJSON = resultJSON;
	}

	public License getEntity() {
		return entity;
	}

	public void setEntity(License entity) {
		this.entity = entity;
	}

	public Page<License> getRes() {
		return res;
	}

	public void setRes(Page<License> res) {
		this.res = res;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMenuFlag() {
		return menuFlag;
	}

	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}


	public  static  void main(String[] args){
		String t = String.valueOf(null);
		System.out.print(t);
	}
}
