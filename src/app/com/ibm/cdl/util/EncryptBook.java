package com.ibm.cdl.util;

import org.apache.commons.lang.StringUtils;


public class EncryptBook {
	
	
	
	/**
	 * 加密字符串
	 * @param encryptStr
	 * @return
	 */
	public static String encrypt(String encryptStr){
		if(StringUtils.isNotEmpty(encryptStr)){
			String encrStr=encryptStr;
			try{
				
				encrStr=KeyUtil.encryptHex(encrStr);
				
			}catch(Exception e){
				//不处理
			}
			
			return encrStr;
		}else{
			return "";
		}
	}
	
	/**
	 * 解密字符串
	 * @param decryptStr
	 * @return
	 */
	public static String decryption(String decryptStr){
		String decryStr=decryptStr;
		try{
			decryStr=KeyUtil.decryptHex(decryStr);
			
		}catch(Exception e){
			//不处理
		}
		
		
		return decryStr;
	}
	
public static void main(String[] args){
	String s="测试加密";
	//加密
	String encrStr=KeyUtil.encryptHex(s);
	System.out.println(encrStr);
	
	
	String decryStr=KeyUtil.decryptHex(encrStr);
	System.out.println(decryStr);
}

}
