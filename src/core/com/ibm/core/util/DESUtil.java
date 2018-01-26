package com.ibm.core.util;

import java.security.Key;

public class DESUtil {
	//加密解密的密钥数组
	private static byte[] key={-83,69,20,70,94,-71,32,64,-12};
	
	private static Key DEFAULT_KEY=null;

	private static DESUtil desUtil=null;
	
	private DESUtil(){
		
		try {
			DEFAULT_KEY=DESCodec.toKey(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DESUtil getInstance(){
		if(desUtil==null){
			desUtil=new DESUtil();
		}
		return desUtil;
	};
	
	/**
	 * 加密函数
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String encrypt(String s){
		byte[] encryptData=null;
		String encryptString=null;
		try {
			encryptData=DESCodec.encrypt(s.getBytes(), DEFAULT_KEY);
			encryptString=Hex.encode(encryptData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encryptString;
	}
	
	/**
	 * 解密函数
	 * @param s
	 * @return
	 */
	public String decrypt(String s){
		byte[] hexByte = Hex.decode(s);
		byte[] decryptData=null;
		try {
			decryptData=DESCodec.decrypt(hexByte, DEFAULT_KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(decryptData);
	}
	
	/**
	 * 解密函数
	 * @param s
	 * @return
	 */
	public String decrypt(String s,String key) throws Exception {
		byte[] hexByte = Hex.decode(s);
		byte[] decryptData=null;
		try {
			decryptData=DESCodec.decrypt(hexByte, DESCodec.toKey(Hex.decode(key)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(decryptData,"utf-8");
	}
	
	
	/**
	 * 解密函数
	 * @param s
	 * @return
	 */
	public String decrypt(byte[] hexByte){
		byte[] decryptData=null;
		try {
			decryptData=DESCodec.decrypt(hexByte, DEFAULT_KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(decryptData);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(Hex.encode(key));
		// TODO Auto-generated method stub
		//String s="中文参数加密~!@#$%^&*()_+=-~`:;'[]{}|/.,<>?";
		String s="getFramVersion";
		
		System.out.println("加密前："+s);
		String encryptString=DESUtil.getInstance().encrypt(s);
		System.out.println("加密后的函数:"+encryptString);
		
		String decryptData=DESUtil.getInstance().decrypt("07B1875F951297855FF2CE0242C24DB9");
		System.out.println("解密后:"+decryptData);
	}
	
}