package com.ibm.core.util;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author Beyond
 */
public class LanchuangSendSmsUtils {

	private static String randString = "0123456789";// 随机产生的字符串
	
    /**
     * @param mobile 手机号码，多个号码使用","分割
     * @param msg    短信内容
     * @return 返回值定义参见HTTP协议文档
     * @throws Exception
     */
    public static String send(String mobile, String msg) throws Exception {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod();
        try {
            URI base = new URI(LanchuangConfig.APIURL, false);
            method.setURI(new URI(base, "HttpSendSM", false));
            method.setQueryString(new NameValuePair[]{
                    new NameValuePair("account", LanchuangConfig.ACCOUNT),
                    new NameValuePair("pswd", LanchuangConfig.PSWD),
                    new NameValuePair("needstatus", LanchuangConfig.NEEDSTATUS),
                    new NameValuePair("product", LanchuangConfig.PRODUCT),
                    new NameValuePair("extno", LanchuangConfig.EXTNO),
                    new NameValuePair("mobile", mobile),
                    new NameValuePair("msg", msg),
            });
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
        }

    }

    /**
     * @param mobile 手机号码，多个号码使用","分割
     * @param msg    短信内容
     * @return 返回值定义参见HTTP协议文档
     * @throws Exception
     */
    public static String batchSend(String mobile, String msg) throws Exception {
    	HttpClient client = new HttpClient();
        GetMethod post = new GetMethod(LanchuangConfig.APIURL+"HttpBatchSendSM");
        try {
//            URI base = new URI(LanchuangConfig.APIURL, false);
//            method.setURI(new URI(base, "HttpBatchSendSM", false));
        	post.setQueryString(new NameValuePair[]{
                    new NameValuePair("account", LanchuangConfig.ACCOUNT),
                    new NameValuePair("pswd", LanchuangConfig.PSWD),
                    new NameValuePair("needstatus", LanchuangConfig.NEEDSTATUS),
                    new NameValuePair("product", LanchuangConfig.PRODUCT),
                    new NameValuePair("extno", LanchuangConfig.EXTNO),
                    new NameValuePair("mobile", mobile),
                    new NameValuePair("msg", msg),
            });
            int result = client.executeMethod(post);
            if (result == HttpStatus.SC_OK) {
                InputStream in = post.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                throw new Exception("HTTP ERROR Status: " + post.getStatusCode() + ":" + post.getStatusText());
            }
        } finally {
        	post.releaseConnection();
        }

    }
    
    /**
     * 获得随机验证码
     * @return
     */
    public static String getRandomMsg(){
    	Random random = new Random();
    	String randomString = "";
		for (int i = 1; i <= 6; i++) {
			String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
			randomString += rand;
		}
		return randomString;
    }
    
    /*
	 * 获取随机的字符
	 */
	public static String getRandomString(int num) {
		return String.valueOf(randString.charAt(num));
	}
}
