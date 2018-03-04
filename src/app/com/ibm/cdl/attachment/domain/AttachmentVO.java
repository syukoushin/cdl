package com.ibm.cdl.attachment.domain;

/**
 * 
   * @create.date: 2011-4-14 下午02:08:43     
   * @comment: <p>系统附件表</p>
   * @see: com.chinawsoft.portal.app.portal.attachment.domain
   * @author: zjg
   * @modify.by: zjg
   * @modify.date: 2011-4-14 下午02:08:43
 */
public class AttachmentVO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1074536014615905386L;
	private String realName;    //附件真实名称
	private String storeName;	//附件存储名称
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
}
