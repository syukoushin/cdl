package com.ibm.cdl.attachment.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.cdl.attachment.dao.impl.AttachmentDaoImpl;
import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.attachment.domain.AttachmentVO;
import com.ibm.cdl.attachment.service.AttachmentService;
import com.ibm.cdl.attachment.utils.UploadUtils;
import com.ibm.cdl.datamap.action.DataMapUtils;
import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.datamap.pojo.DataMap;
import com.ibm.cdl.datamap.pojo.SubDataMap;
import com.ibm.cdl.datamap.service.DataMapService;
import com.ibm.cdl.datamap.service.SubDataMapService;
/**
 * 附件服务接口--实现
   * @create.date: 2011-5-4 上午09:33:28     
   * @comment: <p>TODO</p>
   * @see: com.chinawsoft.portal.app.portal.attachment.service.impl
   * @author: zjg
   * @modify.by: zjg
   * @modify.date: 2011-5-4 上午09:33:28
 */
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {
	
	@Autowired
	private AttachmentDaoImpl attachmentDao;
	
	public Attachment findAttachmentById(String id) {
		return attachmentDao.findUniqueBy("id",id);
	}
	public Attachment findAttachmentByBId(String id) {
		List<Attachment> aList = attachmentDao.findBy("businessId",id);
		if(aList != null && !aList.isEmpty()){
			return aList.get(0);
		} else {
			return null;
		}
		
	}
	public List<Attachment> findAttachmentsByBusinessId(String businessId) {
		return attachmentDao.findBy("businessId", businessId);
	}
	
	public List<AttachmentVO> findAttachmentsVOByBusinessId(String businessId) {
		return attachmentDao.findAttachmentsVOByBusinessId(businessId);
	}
	public void deleteAttachmentById(String id) {
		this.attachmentDao.delete(id);
	}
	
	
	public void deleteAttachmentByBId(String id) {
		String sql = "delete from  ATTACHMENT where BUSINESS_ID = '"+id+"'";
		this.attachmentDao.deleteBySql(sql);
		
	}
	public void save(List<Attachment> attachmentList) {
		attachmentDao.save(attachmentList);
	}
	@Autowired
	private DataMapService dataMapService;
	@Autowired
	private SubDataMapService subDataMapService;
	
	/**
	 * 保存附件 返回attachmentid
	 * @param file
	 * @param fileName
	 * @param fileContentType
	 * @return
	 */
	public String saveAttachmentSingle(File tempFile,String tempFileName,String tmepFileType,String folderName){
		/**保存附件信息*/
		Attachment attachment = new Attachment();
		attachment.setRealName(tempFileName);//设置附件真实名称
		UploadUtils uploadUtils = new UploadUtils();
		attachment.setStoreName(uploadUtils.reName(tempFileName));//设置以当前毫秒数定制文件存储名称
		attachment.setStorePath(uploadUtils.getFilePath(folderName));//设置附件存储路径
		attachment.setAttachType(tmepFileType);//设置附件类型
		attachment.setCreateDate(new Date());//设置创建时间
		attachmentDao.save(attachment);
		uploadUtils.mkdir(attachment.getStorePath());//创建文件夹
		try {
			FileUtils.copyFile(tempFile, new File(attachment.getStorePath() + File.separator + attachment.getStoreName()));//上传附件到指定路径
		} catch (IOException e) {
			throw new RuntimeException("上传文件出现异常！");
		}
		return attachment.getId();
	}


	/**
	 * 保存附件
	 * @param bussinessId
	 * @param tempFile
	 * @param tempFileName
	 * @param tmepFileType
	 * @param folderName
	 * @return
	 */
	public String saveAttachmentSingle(String bussinessId,String createUser,File tempFile,String tempFileName,String tmepFileType,String folderName){
		/**保存附件信息*/
		Attachment attachment = new Attachment();
		attachment.setBusinessId(bussinessId);
		attachment.setRealName(tempFileName);//设置附件真实名称
		UploadUtils uploadUtils = new UploadUtils();
		attachment.setStoreName(uploadUtils.reName(tempFileName));//设置以当前毫秒数定制文件存储名称
		attachment.setStorePath(uploadUtils.getFilePath(folderName));//设置附件存储路径
		attachment.setAttachType(tmepFileType);//设置附件类型
		attachment.setCreateDate(new Date());//设置创建时间
		attachment.setCreateUser(createUser);
		attachmentDao.save(attachment);
		uploadUtils.mkdir(attachment.getStorePath());//创建文件夹
		try {
			FileUtils.copyFile(tempFile, new File(attachment.getStorePath() + File.separator + attachment.getStoreName()));//上传附件到指定路径
		} catch (IOException e) {
			throw new RuntimeException("上传文件出现异常！");
		}
		return attachment.getId();
	}
	
	/**
	 * 保存附件
	 * @param bussinessId
	 * @param tempFile
	 * @param tempFileName
	 * @param tmepFileType
	 * @param folderName
	 * @return
	 */
	public String saveAttachmentForApp(String bussinessId,String updateFlag,File tempFile,String tempFileName,String tmepFileType,String folderName){
		/**保存附件信息*/
		Attachment attachment = new Attachment();
		attachmentDao.deleteBySql("DELETE FROM ATTACHMENT WHERE BUSINESS_ID = '"+bussinessId+"'");
		attachment.setBusinessId(bussinessId);
		attachment.setRealName(tempFileName);//设置附件真实名称
		UploadUtils uploadUtils = new UploadUtils();
		attachment.setStoreName(uploadUtils.reName(tempFileName));//设置以当前毫秒数定制文件存储名称
		attachment.setStorePath(uploadUtils.getFilePath(folderName));//设置附件存储路径
		attachment.setAttachType(tmepFileType);//设置附件类型
		attachment.setCreateDate(new Date());//设置创建时间
		attachmentDao.save(attachment);
		uploadUtils.mkdir(attachment.getStorePath());//创建文件夹
		try {
			FileUtils.copyFile(tempFile, new File(attachment.getStorePath() + File.separator + attachment.getStoreName()));//上传附件到指定路径
		} catch (IOException e) {
			throw new RuntimeException("上传文件出现异常！");
		}
		SubDataMap sub = subDataMapService.findSubDataMapByCode(Constants.VERSION);
		if(sub != null){
			sub.setName(bussinessId+"-"+updateFlag);
		}
		subDataMapService.update(sub);
		DataMapUtils.refresh();
		return attachment.getId();
	}
	/**
	 * 保存附件 返回attachmentid
	 * @param file
	 * @param fileName
	 * @param fileContentType
	 * @return
	 */
	public Attachment saveAttachment(File tempFile,String tempFileName,String tmepFileType,String folderName){
		/**保存附件信息*/
		Attachment attachment = new Attachment();
		attachment.setRealName(tempFileName);//设置附件真实名称
		UploadUtils uploadUtils = new UploadUtils();
		attachment.setStoreName(uploadUtils.reName(tempFileName));//设置以当前毫秒数定制文件存储名称
		attachment.setStorePath(uploadUtils.getFilePath(folderName));//设置附件存储路径
		attachment.setAttachType(tmepFileType);//设置附件类型
		attachment.setCreateDate(new Date());//设置创建时间
		attachmentDao.save(attachment);
		uploadUtils.mkdir(attachment.getStorePath());//创建文件夹
		try {
			FileUtils.copyFile(tempFile, new File(attachment.getStorePath() + File.separator + attachment.getStoreName()));//上传附件到指定路径
		} catch (IOException e) {
			throw new RuntimeException("上传文件出现异常！");
		}
		return attachment;
	}

	/**
	 * 带bussineessId 插入
	 * @param bussinessId
	 * @param tempFile
	 * @param tempFileName
	 * @param tmepFileType
	 * @param folderName
	 * @return
	 */
	public Attachment saveAttachmentWithBID(String bussinessId,File tempFile,String tempFileName,String tmepFileType,String folderName){
		/**保存附件信息*/
		Attachment attachment = new Attachment();
		attachment.setBusinessId(bussinessId);
		attachment.setRealName(tempFileName);//设置附件真实名称
		UploadUtils uploadUtils = new UploadUtils();
		attachment.setStoreName(uploadUtils.reName(tempFileName));//设置以当前毫秒数定制文件存储名称
		attachment.setStorePath(uploadUtils.getFilePath(folderName));//设置附件存储路径
		attachment.setAttachType(tmepFileType);//设置附件类型
		attachment.setCreateDate(new Date());//设置创建时间
		attachmentDao.save(attachment);
		uploadUtils.mkdir(attachment.getStorePath());//创建文件夹
		try {
			FileUtils.copyFile(tempFile, new File(attachment.getStorePath() + File.separator + attachment.getStoreName()));//上传附件到指定路径
		} catch (IOException e) {
			throw new RuntimeException("上传文件出现异常！");
		}
		return attachment;
	}
}
