package com.ibm.core.util;


/**
 * Created by kviuff on 16/3/4.
 */
public class LanchuangConfig {


    /**
     * 发短信常量类
     *
     * @author kviuff
     * @version 2015-10-20 15:20:00
     */

    private static PropertiesLoader propertiesLoader = new PropertiesLoader("lanchuang.properties");
    /**
     * 发送短信的请求地址
     */
    public static final String APIURL = propertiesLoader.getProperty("url");
    /**
     * 用户账号
     */
    public static final String ACCOUNT = propertiesLoader.getProperty("account");
    /**
     * 认证密钥
     */
    public static final String PSWD = propertiesLoader.getProperty("pswd");

    /**
     * 产品ID
     */
    public static final String PRODUCT = propertiesLoader.getProperty("product");

    /**
     * 扩展码
     */
    public static final String EXTNO = propertiesLoader.getProperty("extno");

    /**
     * 是否需要状态报告，需要true，不需要false
     */
    public static final String NEEDSTATUS = propertiesLoader.getProperty("needstatus");
    /**
     * 待审核信息
     */
    public static final String WAITAUDIT = "【蜘蛛蜂】感谢注册蜘蛛蜂会员！我们将尽快完成审核，期间请保持电话畅通，谢谢！本条为系统自动发送，请勿回复。";
    /**
     * 审核信息
     */
    public static final String AUDIT = "【蜘蛛蜂】恭喜您已通过审核正式成为蜘蛛蜂会员！请登录蜘蛛蜂网站，即刻享受全配量贩、实时速达的全方位服务。";
    /**
     * 待修改审核信息
     */
    public static final String WAITAUDITMODIFY = "【蜘蛛蜂】您的企业资料已提交修改，请确认是您本人操作，我们将尽快完成审核，如有疑问请致电：400-658-0890，谢谢！本条为系统自动发送，请勿回复。";
    /**
     * 修改审核信息
     */
    public static final String AUDITMODIFY = "【蜘蛛蜂】您的资料已修改成功。请登录蜘蛛蜂网站，即刻享受全配量贩、实时速达的全方位服务。";

    /**
     * 为入住审核未通过
     */
    public static final String CKECKNOTTHROUGH ="【蜘蛛蜂】您的申请审核未通过，如有疑问请致电：400-658-0890，谢谢！本条为系统自动发送，请勿回复。";

    /**
     *修改申请未通过 
     */
    public static final String APPLYNOTTHROUGH="【蜘蛛蜂】您修改的信息审核未通过，如有疑问请致电：400-658-0890，谢谢！本条为系统自动发送，请勿回复。";
    /**
     *重置密码
     */
    public static final String RESETPASA="【蜘蛛蜂】您的密码成功生成，请尽快登录网站修改密码。密码是";
    /**
     *修改密码
     */
    public static final String UPDATEPASA="【蜘蛛蜂】您的密码修改成功，新密码是";
    /**
     * 铜根源审核信息补全
     */
    public static final String TGYAUDITMODIFY = "【蜘蛛蜂】您申请铜根源补全的资料审核成功。请登录蜘蛛蜂网站，申请铜根源账号开通服务。";

}
