package com.ibm.cdl.attachment.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.ibm.cdl.attachment.dao.AttachmentDao;
import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.attachment.domain.AttachmentVO;
import com.ibm.core.orm.hibernate.SessionFactoryDao;
/**
 * 
   * @create.date: 2011-4-14 下午02:42:51     
   * @comment: <p>附件管理数据持久层接口--实现</p>
   * @see: com.chinawsoft.portal.app.portal.attachment.dao.impl
   * @author: zjg
   * @modify.by: zjg
   * @modify.date: 2011-4-14 下午02:42:51
 */
@Repository("attachmentDao")
public class AttachmentDaoImpl extends SessionFactoryDao<Attachment, String> implements AttachmentDao {

	public List<AttachmentVO> findAttachmentsVOByBusinessId(String businessId){
		StringBuffer sb = new StringBuffer("select attach.STORE_NAME as storeName,attach.REAL_NAME as realName from ATTACHMENT attach where attach.BUSINESS_ID='"+businessId+"'");
		Query queryList = getSession().createSQLQuery(sb.toString());
		queryList.setResultTransformer(Transformers.aliasToBean(AttachmentVO.class));
		List<AttachmentVO> result = (List<AttachmentVO>) queryList.list();
		return result;
	}
	
	public void deleteBySql(String sql){
		Query q = getSession().createSQLQuery(sql);
		q.executeUpdate();
	}
}
