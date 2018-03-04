package com.ibm.cdl.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.security.provider.SecureRandom;

public class DESUtil {
	
	private static String password = "edc123ER";   
     
	/**
	 * 加密
	 * @param datasource
	 * @param password
	 * @return
	 */
	 public static byte[] desCrypto(byte[] datasource, String password) {              
		       try{  
		       SecureRandom random = new SecureRandom();  
		       DESKeySpec desKey = new DESKeySpec(password.getBytes());  
		        //创建一个密匙工厂，然后用它把DESKeySpec转换成   
		        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
		        SecretKey securekey = keyFactory.generateSecret(desKey);  
		        //Cipher对象实际完成加密操作   
		       Cipher cipher = Cipher.getInstance("DES");  
		        //用密匙初始化Cipher对象   
		        cipher.init(Cipher.ENCRYPT_MODE, securekey);  
		       //现在，获取数据并加密   
		       //正式执行加密操作   
		       return cipher.doFinal(datasource);  
		       }catch(Throwable e){  
		                e.printStackTrace();  
		        }  
		        return null;  
		}  

	/**
	 * 解密
	 * @param src
	 * @param password
	 * @return
	 * @throws Exception
	 */
	 public static byte[] decrypt(byte[] src, String password) throws Exception {  
		         // DES算法要求有一个可信任的随机数源   
		         SecureRandom random = new SecureRandom();  
		         // 创建一个DESKeySpec对象   
		         DESKeySpec desKey = new DESKeySpec(password.getBytes());  
		         // 创建一个密匙工厂   
		         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
		         // 将DESKeySpec对象转换成SecretKey对象   
		         SecretKey securekey = keyFactory.generateSecret(desKey);  
		         // Cipher对象实际完成解密操作   
		         Cipher cipher = Cipher.getInstance("DES");  
		         // 用密匙初始化Cipher对象   
		         cipher.init(Cipher.DECRYPT_MODE, securekey);  
		         // 真正开始解密操作   
		         return cipher.doFinal(src);  
		 }  

	 /**
	  * 加密
	  * @return
	  */
	 public static String encryption(String str){
		 byte[] result = DESUtil.desCrypto(str.getBytes(),password);  
		 // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher异常，   
         // 不能把加密后的字节数组直接转换成字符串   
		 return Base64Utils.encode(result);  

	 }
	 
	 /**
	  * 加密
	  * @return
	  */
	 public static String encryption(String str,String inPassword){
		 byte[] result = DESUtil.desCrypto(str.getBytes(),inPassword);  
		 // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher异常，   
         // 不能把加密后的字节数组直接转换成字符串   
		 return Base64Utils.encode(result);  

	 }
	 
	 /**
	  * 解密
	  * @return
	 * @throws Exception 
	 * @throws Exception 
	  */
	 public static String  decryption(String str) throws Exception{
		  try{
			    byte[] strs = Base64Utils.decode(str.toCharArray());
				byte[] result = DESUtil.decrypt(strs,password);
				return new String(result);
		  }catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		 }
	 }
	 
	 /**
	  * 解密
	  * @return
	 * @throws Exception 
	 * @throws Exception 
	  */
	 public static String  decryption(String str,String inPassword) throws Exception{
		  try{
			    byte[] strs = Base64Utils.decode(str.toCharArray());
				byte[] result = DESUtil.decrypt(strs,inPassword);
				return new String(result);
		  }catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		 }
	 }
	 
	 public static void main(String[] args) throws Exception {
//		 //待加密内容   
//		 String str = DateUtils.parseDate(new Date(), "yyyy-MM-dd HH:mm:ss")+"--"+"zhengzq";
//		 //密码，长度要是8的倍数   
//		 String password = "qazwsxed";  
//		 byte[] result = DESUtil.desCrypto(str.getBytes(),password);  
//		 System.out.println("加密后内容为："+new String(result));  
//		
//		 try {
//			byte[] result1 = DESUtil.decrypt(result,password);
//			System.out.println("解密后内容为："+new String(result1));  
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		 String str = encryption(DateUtils.parseDate(new Date(), "yyyy-MM-dd HH:mm:ss")+"--"+"zhengzq"+"--18600503212");
//		 String ss = new String(str);
//		 System.out.println(ss);
//		 
		 System.out.println(decryption("f6H/6fsr11EKyCSFkrDJ46hyXpaYMfrFbsIvr9VJ/bJqPgD8FDHmrOC0vxYRIori"));
		 
	}
}
