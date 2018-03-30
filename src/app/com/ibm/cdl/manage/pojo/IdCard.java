package com.ibm.cdl.manage.pojo;

import com.ibm.core.util.excel.annotation.ExcelField;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by zhuxiangxin on 2018/1/25.
 */
public class IdCard  implements Serializable {

    // id
    private  String id;

    // 姓名
    @ExcelField(title="姓名", align=2, sort=1)
    private  String name;

    @ExcelField(title = "生日", align = 2,sort = 5)
    private Date birthDay;
    // 住址
    @ExcelField(title="住址", align=2, sort=6)
    private String address;

    // 性别
    @ExcelField(title="性别", align=2, sort=2)
    private String sex;

    // 身份证号码
    @ExcelField(title="身份证号", align=2, sort=3)
    private String cardNo;

    // 民族
    @ExcelField(title="民族", align=2, sort=4)
    private String ethnic;

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

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCardNo() {
        return cardNo;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
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
}
