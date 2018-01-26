package com.ibm.cdl.manage.action;

import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.manage.pojo.PartGroup;
import com.ibm.cdl.manage.service.PartGroupService;
import com.ibm.core.action.DefaultBaseAction;
import com.ibm.core.orm.Page;
import com.ibm.core.util.DateJsonValueProcessor;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GroupAction extends DefaultBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private PartGroupService groupService;
	
	private PartGroup entity = new PartGroup();
	private Page<PartGroup> res = new Page<PartGroup>();   //分页对象
	private String resultJSON=null;
	private String result = null;
	private File attachFile;                              //附件上传对象
	private String attachFileFileName;                    //附件名称
	private String attachFileContentType;                 //附件类型
	private List<Attachment> attachments = new ArrayList<Attachment>(); 
    
    
    /**
	 * 跳转到订单列表页面
	 * @return
	 */
	public String toList(){
		getSession().setAttribute("CURRENT_MENU", Constants.MENU_GROUP);
		return goUrl("list");
	}
	
	/**
	 * 查询公司列表
	 * @return
	 */
	public String ajaxFindList() {
		JSONObject json = new JSONObject();
		try{
			List<PartGroup> pgList =  groupService.findAll();
			json.put("optSts", "0");
			json.put("optMsg", "成功");
			json.put("data", pgList);
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
	 * 添加
	 * @return
	 */
	public String ajaxAddEntity(){
//		String filename = attachFileFileName;
//        if ((filename != null) && (filename.length() > 0)) {
//            int dot = filename.lastIndexOf('.');
//            if ((dot > -1) && (dot < (filename.length() - 1))) {
//                filename = filename.substring(dot + 1);
//            }
//        }
//        SimpleDateFormat sfd = new SimpleDateFormat("yyyyMMddhhmmss");
//        Date date = new Date(System.currentTimeMillis());
//        String time = sfd.format(date);
//        String name = time + "." + filename;
//        groupService.saveEntity(attachFile,name, entity);
//        return goUrl("list");
		JSONObject json = new JSONObject();
		try{
			String name= getParameter("name");
			
			// 检查userCode是否存在
			if(StringUtils.isNotEmpty(name)){
				if(groupService.checkExistGroupCode(name)){
					json.put("optSts", "2");
					json.put("optMsg", "名称已存在");
				} else {
					PartGroup enginner = new PartGroup();
					enginner.setName(name);
					groupService.saveEntity(attachFile,null,enginner);
					json.put("optSts", "0");
					json.put("optMsg", "成功");
				}
			} else {
				json.put("optSts", "1");
				json.put("optMsg", "名称为空");
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
	 * 更新
	 * @return
	 */
	public String ajaxUpdateEntity(){
		JSONObject json = new JSONObject();
		try{
			String name= getParameter("name");
			String id= getParameter("id");
			
			// 检查userCode是否存在
			if(StringUtils.isNotEmpty(id)){
				
				// 检查userCode是否存在
				if(StringUtils.isNotEmpty(id)){
					PartGroup user = groupService.queryGroupById(id);
					user.setName(name);
					groupService.updateEntity(user);
					json.put("optSts", "0");
					json.put("optMsg", "成功");
				} else {
					json.put("optSts", "3");
					json.put("optMsg", "参数错误");
				}
			} else {
				json.put("optSts", "1");
				json.put("optMsg", "参数错误");
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
	 * 查找
	 * @return
	 */
	public String ajaxFindEntity(){
		JSONObject json = new JSONObject();
		try{
			String id= getParameter("id");
			
			// 检查userCode是否存在
			if(StringUtils.isNotEmpty(id)){
				PartGroup user = groupService.queryGroupById(id);
				if(user == null){
					json.put("optSts", "2");
					json.put("optMsg", "用户不存在");
				} else {
					json.put("optSts", "0");
					json.put("data",user);
					json.put("optMsg", "成功");
				}
			} else {
				json.put("optSts", "3");
				json.put("optMsg", "参数错误");
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
	 * 删除
	 * @return
	 */
	public String ajaxDeleteEntity(){
		JSONObject json = new JSONObject();
		try{
			String id= getParameter("id");
			
			// 检查userCode是否存在
			if(StringUtils.isNotEmpty(id)){
				groupService.delEntity(id);
				json.put("optSts", "0");
				json.put("optMsg", "成功");
			} else {
				json.put("optSts", "3");
				json.put("optMsg", "参数错误");
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
	 * 获取分页列表
	 * @return
	 */
	public String ajaxGroupList(){
		JSONObject json = new JSONObject();
		try{
			res.setPageNo(pageNo);
			res = groupService.findPage(entity ,res);
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
	
    
    
	public String getResultJSON() {
		return resultJSON;
	}

	public void setResultJSON(String resultJSON) {
		this.resultJSON = resultJSON;
	}

	

	public PartGroup getEntity() {
		return entity;
	}

	public void setEntity(PartGroup entity) {
		this.entity = entity;
	}

	public Page<PartGroup> getRes() {
		return res;
	}

	public void setRes(Page<PartGroup> res) {
		this.res = res;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public File getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(File attachFile) {
		this.attachFile = attachFile;
	}

	public String getAttachFileFileName() {
		return attachFileFileName;
	}

	public void setAttachFileFileName(String attachFileFileName) {
		this.attachFileFileName = attachFileFileName;
	}

	public String getAttachFileContentType() {
		return attachFileContentType;
	}

	public void setAttachFileContentType(String attachFileContentType) {
		this.attachFileContentType = attachFileContentType;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
}
