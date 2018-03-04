package com.ibm.cdl.manage.pojo;

import com.ibm.core.util.excel.annotation.ExcelField;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 行驶证实体类
 * @author zhuxiangxin
 *
 */
public class License implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8023334088673007751L;
	private String id;
	// 号牌号码
	@ExcelField(title="号牌号码", align=2, sort=1)
	private String cardNo;
	// 车辆类型
	private String carType;
	// 姓名
	@ExcelField(title="姓名", align=2, sort=2)
	private String name;
	// 地址
	@ExcelField(title="地址", align=2, sort=3)
	private String address;
	// 品牌型号
	@ExcelField(title="品牌型号", align=2, sort=4)
	private String bandNo;
	// 车辆识别代码
	@ExcelField(title="车辆识别代码", align=2, sort=5)
	private String carNo;
	// 发动记号码
	@ExcelField(title="发动机号码", align=2, sort=6)
	private String enginNo;
	// 注册日期
	@ExcelField(title="注册日期", align=2, sort=7)
	private Date registDate;
	// 发证日期
	@ExcelField(title="发证日期", align=2, sort=8)
	private Date passDate;
	// 使用性质
	private String useType;

	private String dmsFlag;
	
	// 创建人
	private String createUser;

	// 附件
	private String storePath;

	// 创建时间
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBandNo() {
		return bandNo;
	}

	public void setBandNo(String bandNo) {
		this.bandNo = bandNo;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getEnginNo() {
		return enginNo;
	}

	public void setEnginNo(String enginNo) {
		this.enginNo = enginNo;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
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
}
