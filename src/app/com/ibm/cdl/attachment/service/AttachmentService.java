package com.ibm.cdl.attachment.service;

import java.io.File;
import java.util.List;

import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.attachment.domain.AttachmentVO;


public interface AttachmentService {
	public Attachment findAttachmentById(String id);
	public Attachment findAttachmentByBId(String id);
	
	public void deleteAttachmentById(String id);
	
	public void deleteAttachmentByBId(String id);
	
	public List<Attachment> findAttachmentsByBusinessId(String businessId);
	
	public List<AttachmentVO> findAttachmentsVOByBusinessId(String businessId);
	
	public void save(List<Attachment> attachmentId);
	
	/**
	 * 保存附件 返回attachmentid
	 * @param file
	 * @param fileName
	 * @param fileContentType
	 * @return
	 */
	public String saveAttachmentSingle(File tempFile,String tempFileName,String tmepFileType,String folderName);

	/**
	 * 保存附件
	 * @param tempFile
	 * @param tempFileName
	 * @param tmepFileType
	 * @param folderName
	 * @return
	 */
	public String saveAttachmentSingle(String bussinessId,String createUser,File tempFile,String tempFileName,String tmepFileType,String folderName);
	
	/**
	 * 保存附件
	 * @param tempFile
	 * @param tempFileName
	 * @param tmepFileType
	 * @param folderName
	 * @return
	 */
	public String saveAttachmentForApp(String bussinessId,String updateFlag,File tempFile,String tempFileName,String tmepFileType,String folderName);
	
	/**
	 * 保存附件 返回attachmentid
	 * @param file
	 * @param fileName
	 * @param fileContentType
	 * @return
	 */
	public Attachment saveAttachment(File tempFile,String tempFileName,String tmepFileType,String folderName);

	/**
	 * 带bussinessId 查询
	 * @param bussinessId
	 * @param tempFile
	 * @param tempFileName
	 * @param tmepFileType
	 * @param folderName
	 * @return
	 */
	public Attachment saveAttachmentWithBID(String bussinessId,File tempFile,String tempFileName,String tmepFileType,String folderName);
}
