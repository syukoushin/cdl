package com.ibm.cdl.attachment.dao;

import java.util.List;

import com.ibm.cdl.attachment.domain.AttachmentVO;

/**
 * 
   * @create.date: 2011-4-14 下午02:41:15     
   * @comment: <p>附件管理数据持久层接口--定义</p>
   * @see: com.chinawsoft.portal.app.portal.attachment.dao
   * @author: zjg
   * @modify.by: zjg
   * @modify.date: 2011-4-14 下午02:41:15
 */
public interface AttachmentDao {

	public List<AttachmentVO> findAttachmentsVOByBusinessId(String businessId);
}
