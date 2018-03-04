package com.ibm.cdl.attachment.domain;

import java.util.Date;

/**
 * 
   * @create.date: 2011-4-14 下午02:08:43     
   * @comment: <p>系统附件表</p>
   * @see: com.chinawsoft.portal.app.portal.attachment.domain
   * @author: zjg
   * @modify.by: zjg
   * @modify.date: 2011-4-14 下午02:08:43
 */
public class Attachment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	//field
	private String id;			//主键
	private String realName;    //附件真实名称
	private String attachType;  //附件类型
	private String storeName;	//附件存储名称
	private String storePath; 	//附件存储路径
	private String attactType;
	private String businessId;  //业务ID
	private String createUser;	//创建人
	private Date createDate;	//创建时间

	public String getAttactType() {
		return attactType;
	}

	public void setAttactType(String attactType) {
		this.attactType = attactType;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAttachType() {
		return attachType;
	}
	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStorePath() {
		return storePath;
	}
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
