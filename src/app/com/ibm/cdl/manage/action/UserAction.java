package com.ibm.cdl.manage.action;

import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.cdl.manage.service.UserService;
import com.ibm.core.action.DefaultBaseAction;
import com.ibm.core.orm.Page;
import com.ibm.core.util.DateJsonValueProcessor;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

public class UserAction extends DefaultBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;
	
	private User entity = new User();
	private Page<User> res = new Page<User>();   //分页对象
	private String resultJSON=null;
	private String result = null;
    
    
    
    /**
	 * 跳转到订单列表页面
	 * @return
	 */
	public String toList(){
		getSession().setAttribute("CURRENT_MENU", Constants.MENU_USER);
		return goUrl("list");
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String ajaxAddEntity(){
		JSONObject json = new JSONObject();
		try{
			String name= getParameter("userName");
			String userCode= getParameter("userCode");
			String groupId = getParameter("groupId");
			
			// 检查userCode是否存在
			if(StringUtils.isNotEmpty(userCode)){
				if(userService.checkExistUserCode(userCode)){
					json.put("optSts", "2");
					json.put("optMsg", "用户名已存在");
				} else {
					User enginner = new User();
					enginner.setUserName(name);
					enginner.setUserCode(userCode);
					enginner.setPassword(DigestUtils.md5Hex(Constants.INIT_PWD));
					enginner.setJobLevel(Constants.USER_ADMIN);
					enginner.setGroupId(groupId);
					User user = getSessionUser();
					userService.saveEntity(enginner,user);
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
	 * 更新
	 * @return
	 */
	public String ajaxUpdateEntity(){
		JSONObject json = new JSONObject();
		try{
			String id = getParameter("id");
			String name= getParameter("userName");
			String userCode= getParameter("userCode");
			String groupId = getParameter("groupId");
			// 检查userCode是否存在
			if(StringUtils.isNotEmpty(id)){
				
				// 检查userCode是否存在
				if(StringUtils.isNotEmpty(id)){
					User user = userService.queryUserById(id);
					user.setUserCode(userCode);
					user.setUserName(name);
					user.setGroupId(groupId);
					userService.updateEntity(user);
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
				User user = userService.queryUserById(id);
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
				userService.delEntity(id);
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
	public String ajaxUserList(){
		JSONObject json = new JSONObject();
		try{
			res.setPageNo(pageNo);
			User currentUser = getSessionUser();
			res = userService.findPage(entity ,res,currentUser);
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
     * 重置密码
     * @return
     */
    public String modifyPwd(){
    	JSONObject json = new JSONObject();
		try{
			String userCode = getParameter("userCode");
			User u = userService.getUserByUserCode(userCode);
			if(u == null){
			} else {
				u.setPassword(DigestUtils.md5Hex(Constants.INIT_PWD));
				userService.updateEntity(u);
			}
			json.put("optSts", "0");
			json.put("optMsg", "修改密码成功");
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

	

	public User getEntity() {
		return entity;
	}

	public void setEntity(User entity) {
		this.entity = entity;
	}

	public Page<User> getRes() {
		return res;
	}

	public void setRes(Page<User> res) {
		this.res = res;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
