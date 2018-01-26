package com.ibm.cdl.manage.pojo;

import com.ibm.core.util.excel.annotation.ExcelField;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by zhuxiangxin on 2018/1/25.
 */
public class BusinessLicense implements Serializable {

    // id
    private  String id;

    // 姓名
    @ExcelField(title="名称", align=2, sort=1)
    private  String name;

    // 注册号
    @ExcelField(title="注册号", align=2, sort=2)
    private  String regNumber;

    // 社会信用代码
    @ExcelField(title="社会信用代码", align=2, sort=3)
    private String creditCode;

    // 地址
    @ExcelField(title="地址", align=2, sort=4)
    private String address;

    // 法定代表人
    @ExcelField(title="法定代表人", align=2, sort=5)
    private String incorporator;

    // 有效日期
    @ExcelField(title="有效日期", align=2, sort=6)
    private String endDate;

    // 创建人
    private String createUser;

    // 创建时间
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIncorporator() {
        return incorporator;
    }

    public void setIncorporator(String incorporator) {
        this.incorporator = incorporator;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
}
