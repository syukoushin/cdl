package com.ibm.cdl.attachment.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.attachment.service.AttachmentService;
import com.ibm.core.action.DefaultBaseAction;
import com.ibm.core.util.AssertUtils;
import com.opensymphony.xwork2.Action;

import net.sf.json.JSONObject;
/**
 * 附件数据传递及请求处理类
   * @create.date: 2011-5-4 上午09:49:56     
   * @comment: <p>TODO</p>
   * @see: com.chinawsoft.portal.app.portal.attachment.action
   * @author: songyuliang
   * @modify.by: songyuliang
   * @modify.date: 2011-5-4 上午09:49:56
 */
public class AttachmentAction extends DefaultBaseAction {
	private static final Logger log = Logger.getLogger(AttachmentAction.class);
	private AttachmentService attachmentService;
	private Attachment attachment = new Attachment();
	private transient InputStream inputStream;
	private String fileName;
	private Long fileLength;
	private String result;
	private String courseItemId;
	private String firstTitleType;
	
	/**
	 * 下载附件
	 */
	public String download(){
		try{
//			String userCode = this.getSessionUser().getUserCode();//获取当前登录用户code	
//			String userName = this.getSessionUser().getUserName();//获取当前登录用户code	
			String id = getParameter("id");
			AssertUtils.hasText(attachment.getId()+"", "附件主键ID cat not be null");
			attachment = attachmentService.findAttachmentById(id);
			fileName = new String(attachment.getRealName().getBytes("GBK"),"ISO8859-1");
			File file = new File(attachment.getStorePath()+File.separator+attachment.getStoreName());
			inputStream = new FileInputStream(file);
			if(StringUtils.isNotEmpty(courseItemId)){
				courseItemId = courseItemId.replace("^", "");
			}
			//moaLogService.saveLog(new MOALog(userCode,userName,courseItemId,firstTitleType,"download",userName + "下载了：【"+attachment.getRealName()+"】"));
			return "download";
		}catch (Exception e) {
			log.error("系统找不到指定文件，文件或被删除", e);
			return this.returnScriptAlertWindow("系统找不到指定文件，文件或被删除！");
		}
	}
	
	/**
	 * 下载附件
	 */
	public String downloadByBId(){
		try{
			AssertUtils.hasText(attachment.getId()+"", "附件主键ID cat not be null");
			attachment = attachmentService.findAttachmentByBId(attachment.getId());
			fileName = new String(attachment.getRealName().getBytes("GBK"),"ISO8859-1");
			File file = new File(attachment.getStorePath()+File.separator+attachment.getStoreName());
			inputStream = new FileInputStream(file);
			fileLength=file.length();
			if(StringUtils.isNotEmpty(courseItemId)){
				courseItemId = courseItemId.replace("^", "");
			}
			//moaLogService.saveLog(new MOALog(userCode,userName,courseItemId,firstTitleType,"download",userName + "下载了：【"+attachment.getRealName()+"】"));
			return "download";
		}catch (Exception e) {
			log.error("系统找不到指定文件，文件或被删除", e);
			return this.returnScriptAlertWindow("系统找不到指定文件，文件或被删除！");
		}
	}
	
	/**
	 * 删除附件
	 * @comment: <p></p>  
	 * @create.date: 2011-5-4 ( 上午09:46:30 )
	 * @author: zjg
	 * @return:
	 */
	public String deleteFile(){
		JSONObject json = new JSONObject();
		try{
			AssertUtils.hasText(attachment.getId()+"", "附件主键ID cat not be null");
			System.out.println(attachment.getId());
			if(attachment.getId() == null || "".equals(attachment.getId())){
				json.put("optSts", "1");
				json.put("optMsg", "参数错误！");
			} else {
				attachment = attachmentService.findAttachmentById(attachment.getId());
				if(attachment == null){
					json.put("optSts", "1");
					json.put("optMsg", "无该附件记录！");
				} else {
					File file = new File(attachment.getStorePath()+File.separator+attachment.getStoreName());
					file.delete();//根据附件存储路径及存储名称删除附件
					this.attachmentService.deleteAttachmentById(attachment.getId());//删除附件与业务表的关联
					json.put("optSts", "0");
					json.put("optMsg", "成功！");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			json.put("optSts", "1");
			json.put("optMsg", "失败！");
		}finally{
			this.sendResponseMessage(json.toString());
		}
		return null;
	}
	
	/**
	 * 删除附件
	 * @comment: <p></p>  
	 * @create.date: 2011-5-4 ( 上午09:46:30 )
	 * @author: zjg
	 * @return:
	 */
	public String deleteFileById(){
		try{
			AssertUtils.hasText(getParameter("id")+"", "附件主键ID cat not be null");
			attachment = attachmentService.findAttachmentById(getParameter("id"));
			File file = new File(attachment.getStorePath()+File.separator+attachment.getStoreName());
			file.delete();//根据附件存储路径及存储名称删除附件
			this.attachmentService.deleteAttachmentById(attachment.getId());//删除附件与业务表的关联
			this.result="0";//返回前台删除成功标识
			return Action.SUCCESS;
		}catch (Exception e) {
			log.error("删除附件出现异常!", e);
			this.result="1";//返回前台删除失败标识
			return Action.ERROR;
		}
	}
	
	/**
	 * 获取下载文件列表
	 * @return
	 */
	public String getFileList(){
		JSONObject json = new JSONObject();
		try{
			List<Attachment> list = attachmentService.findAttachmentsByBusinessId(getParameter("businessId"));
			json.put("optSts", "0");
			json.put("optMsg", "获取下载列表成功");
			json.put("data", list);
		}catch(Exception e){
			json.put("optSts", "1");
			json.put("optMsg", "获取下载列表失败！");
		}finally{
			this.sendResponseMessage(json.toString());
		}
		return null;
	}
	
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCourseItemId() {
		return courseItemId;
	}

	public void setCourseItemId(String courseItemId) {
		this.courseItemId = courseItemId;
	}

	public String getFirstTitleType() {
		return firstTitleType;
	}

	public void setFirstTitleType(String firstTitleType) {
		this.firstTitleType = firstTitleType;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

}
