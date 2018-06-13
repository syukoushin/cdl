package com.ibm.cdl.attachment.service;

import com.ibm.cdl.attachment.domain.Attachment;

import java.io.File;
import java.util.List;

/**
 * 附件服务接口--实现
 *
 * @create.date: 2011 -5-4 上午09:33:28
 * @comment: <p>TODO</p>
 * @author: zjg
 * @modify.by: zjg
 * @modify.date: 2011 -5-4 上午09:33:28
 */
public interface AttachmentService   {

	String saveAttachment(String businessId,String createUser,File file,String fileName,String fileType);

	void deleteAttachment(String businessId);

	List<Attachment> findAttachmentsByBusinessId(String id);

	/**
	 * 保存附件
	 * @param tempFile
	 * @param tempFileName
	 * @param tmepFileType
	 * @param folderName
	 * @return
	 */
	public String saveAttachmentForApp(String bussinessId,String updateFlag,File tempFile,String tempFileName,
									   String tmepFileType,String createUser);

}
