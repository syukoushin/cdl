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

	boolean deleteAttachment(String businessId);

	List<Attachment> findAttachmentsByBusinessId(String id);
}
