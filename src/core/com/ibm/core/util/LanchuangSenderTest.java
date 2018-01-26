package com.ibm.core.util;


public class LanchuangSenderTest {
	public static void main(String[] args) {
		String url = LanchuangConfig.APIURL;// 应用地址
		String account = LanchuangConfig.ACCOUNT;// 账号
		String pswd = LanchuangConfig.PSWD;// 密码
//		String mobile = "15910501535,18686621569,13051633237,18675592697";// 手机号码，多个号码使用","分割
		String mobile = "15822714492";// 手机号码，多个号码使用","分割
		String msg = "亲爱的用户，您的验证码是123456，5分钟内有效。";// 短信内容
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String product = null;// 产品ID
		String extno = null;// 扩展码
		String randomMsg = LanchuangSendSmsUtils.getRandomMsg();
		System.out.println(randomMsg);
		 LanchuangSendSmsUtils lanchuangSendSmsUtils = new LanchuangSendSmsUtils();
         /*String returnString = lanchuangSendSmsUtils.batchSend(mobile, "【蜘蛛蜂】短信验证码：" + valid + "," + "欢迎注册蜘蛛蜂会员。");*/
         try {
        	 lanchuangSendSmsUtils.batchSend(mobile, "【蜘蛛蜂】短信验证码：，" + "欢迎注册蜘蛛蜂会员。");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		try {
//			String returnString = LanchuangSendSmsUtils.batchSend(mobile, msg);
//			System.out.println(returnString);
//			// TODO 处理返回值,参见HTTP协议文档
//		} catch (Exception e) {
//			// TODO 处理异常
//			e.printStackTrace();
//		}
	}
}
