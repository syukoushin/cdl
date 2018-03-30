package com.ibm.cdl.manage.pojo;

import com.ibm.core.util.excel.annotation.ExcelField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 发票实体类
 * @author zhuxiangxin
 *
 */
public class InvoiceExport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8023334088673007751L;
	private String id;
	// 开票日期
	@ExcelField(title="开票日期", align=2, sort=1)
	private String printDate;
	// 发票号码
	@ExcelField(title="发票号码", align=2, sort=2)
	private String number;
	// 姓名
	@ExcelField(title="购买方税号", align=2, sort=3)
	private String name;
	// 车辆类型
	@ExcelField(title="发票代码", align=2, sort=4)
	private String invoiceNo;
	// 车架号码
	@ExcelField(title="车架号码", align=2, sort=5)
	private String frameNo;
	// 价税合计
	@ExcelField(title="价税合计", align=2, sort=6)
	private BigDecimal tax;

	/** add  by zhuxiangxin 增加购买方名称、身份证/组织机构代码、厂牌型号、合格证号、发动机号码 字段 2018-03-25**/
	// 购买方名称
	private String buyerName;
	// 身份证/组织机构代码
	private String idCard;
	// 厂牌型号
	private String bandNo;
	// 合格证号
	private String okNo;
	// 发动机号码
	private String enginNo;
	/** add end **/


	private String dmsFlag;
	// 创建人
	private String createUser;

	// 附件
	private String storePath;

	// 创建时间
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());;

	public String getId() {
		return id;
	}

	public BigDecimal getTax() {
		return tax;
	}


	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPrintDate() {
		return printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public String getDmsFlag() {
		return dmsFlag;
	}

	public void setDmsFlag(String dmsFlag) {
		this.dmsFlag = dmsFlag;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBandNo() {
		return bandNo;
	}

	public void setBandNo(String bandNo) {
		this.bandNo = bandNo;
	}

	public String getOkNo() {
		return okNo;
	}

	public void setOkNo(String okNo) {
		this.okNo = okNo;
	}
	public String getEnginNo() {
		return enginNo;
	}

	public void setEnginNo(String enginNo) {
		this.enginNo = enginNo;
	}
}
