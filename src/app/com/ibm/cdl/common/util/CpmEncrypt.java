package com.ibm.cdl.common.util;
/*
 * 创建日期 2009-4-23
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * @author chenc
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CpmEncrypt implements IntEncrypt {
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	

	private static final byte [] DESkey = {(byte)0xFF, (byte)0xFE, (byte)0x54, (byte)0x0, (byte)0x50,
		(byte)0x0, (byte)0x4A, (byte)0x0, (byte)0x4F, (byte)0x0, (byte)0x4D, (byte)0x0, (byte)0x52,
		(byte)0x0, (byte)0x43, (byte)0x0, (byte)0x54, (byte)0x0, (byte)0x50, (byte)0x0, (byte)0x41,
		(byte)0x0, (byte)0x4D, (byte)0x0, (byte)0x4C, (byte)0x0, (byte)0x30, (byte)0x0, (byte)0x30,
		(byte)0x0, (byte)0x30, (byte)0x0, (byte)0x65, (byte)0x0, (byte)0x34, (byte)0x0, (byte)0x66,
		(byte)0x0, (byte)0x39, (byte)0x0, (byte)0x38, (byte)0x0, (byte)0x35, (byte)0x0, (byte)0x32,
		(byte)0x0, (byte)0x30, (byte)0x0, (byte)0x34, (byte)0x0, (byte)0x30, (byte)0x0, (byte)0x38,
		(byte)0x0, (byte)0x32, (byte)0x0, (byte)0x30, (byte)0x0, (byte)0x31, (byte)0x0, (byte)0x31,
		(byte)0x0, (byte)0x33, (byte)0x0, (byte)0x30, (byte)0x0};//设置密钥
	private static final byte[] DESIV = {(byte)0x12,(byte) 0x34, (byte)0x56, (byte)0x78, (byte)0x90,
		(byte)0xAB, (byte)0xCD, (byte) 0xEF};//设置向量

	private static AlgorithmParameterSpec iv =null;//加密算法的参数接口，IvParameterSpec是它的一个实现
	private static Key key = null;

	
	private static CpmEncrypt cpm = new CpmEncrypt();

	private CpmEncrypt() {
		DESKeySpec keySpec;
		try {
			keySpec = new DESKeySpec(DESkey);

			iv = new IvParameterSpec(DESIV);//设置向量
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");//获得密钥工厂
			key = keyFactory.generateSecret(keySpec);//得到密钥对象
		
		} catch (InvalidKeyException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}//设置密钥参数
	}


	public static IntEncrypt getInstance() {
		return cpm;
	}

	private BASE64Encoder base64Encoder = new BASE64Encoder();

	/**
	 * 加密
	 * @param data 加密数据
	 * @return 加密结果
	 * @throws Exception
	 */
	public  String getEncString (String data) {
		try {
			Cipher enCipher  =  Cipher.getInstance("DES/CBC/PKCS5Padding");//得到加密对象Cipher
			enCipher.init(Cipher.ENCRYPT_MODE,key,iv);
			byte[] pasByte = enCipher.doFinal(data.getBytes("unicode"));
			return base64Encoder.encode(pasByte);
		} catch (InvalidKeyException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}//设置工作模式为加密模式，给出密钥和向量

		return "";
	}

	/**
	 * 解密
	 * @param data 解密数据
	 * @return 解密结果
	 * @throws Exception
	 */
	public  String getDesString (String data) throws Exception{
		
		Cipher deCipher   =  Cipher.getInstance("DES/CBC/PKCS5Padding");
		deCipher.init(Cipher.DECRYPT_MODE,key,iv);
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] pasByte=deCipher.doFinal(base64Decoder.decodeBuffer(data));
		return new String(pasByte,"unicode");
	}

	/**
	 * 解密
	 * @param data 解密数据
	 * @return 解密结果
	 * @throws Exception
	 */
	public  String getDesStringWithCode (String data, String code) throws Exception{
		
		Cipher deCipher   =  Cipher.getInstance("DES/CBC/PKCS5Padding");
		deCipher.init(Cipher.DECRYPT_MODE,key,iv);
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] pasByte=deCipher.doFinal(base64Decoder.decodeBuffer(data));
		return new String(pasByte, code);
	}
	
	/**
		 * 加密
		 * @param data 加密数据
		 * @param codeType 编码类型
		 * @return 加密结果
		 * @throws Exception
		 */
		public  String getEncString (String data, String codeType) {
			try {
				Cipher enCipher  =  Cipher.getInstance("DES/CBC/PKCS5Padding");//得到加密对象Cipher
				enCipher.init(Cipher.ENCRYPT_MODE,key,iv);
				byte[] pasByte = enCipher.doFinal(data.getBytes(codeType));
				return base64Encoder.encode(pasByte);
			} catch (InvalidKeyException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}//设置工作模式为加密模式，给出密钥和向量

			return "";
		}

		/**
		 * 解密
		 * @param data 解密数据
		 * @param codeType 编码类型
		 * @return 解密结果
		 * @throws Exception
		 */
		public  String getDesString (String data, String codeType) throws Exception{
	
			Cipher deCipher   =  Cipher.getInstance("DES/CBC/PKCS5Padding");
			deCipher.init(Cipher.DECRYPT_MODE,key,iv);
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] pasByte=deCipher.doFinal(base64Decoder.decodeBuffer(data));
			return new String(pasByte, codeType);
		}
	/* （非 Javadoc）
	 * @see com.tjmcc.process.sso.IntEncrypt#getEncrStr()
	 */
	public String getEncrStr() {
		// TODO 自动生成方法存根
		return null;
	}


	/* （非 Javadoc）
	 * @see com.tjmcc.process.sso.IntEncrypt#getEncrStrByUid(java.lang.String)
	 */
	public String getEncrStrByUid(String uid) {
		// TODO 自动生成方法存根
		System.out.println("getDesString" + uid);
		return getEncString(uid);
	}


	/* （非 Javadoc）
	 * @see com.tjmcc.process.sso.IntEncrypt#getEncrStrByEmploynum(java.lang.String)
	 */
	public String getEncrStrByEmploynum(String employeenum) {
		// TODO 自动生成方法存根
		return null;
	}


	/* （非 Javadoc）
	 * @see com.tjmcc.process.sso.IntEncrypt#getEncrStrByArr(java.lang.String[])
	 */
	public String getEncrStrByArr(String[] arr) {
		// TODO 自动生成方法存根
		return null;
	}

	public static void main(String[] args) {
		//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//	System.out.println(CpmEncrypt.getInstance().getEncrStrByUid("diyue"));
	try {
		String testStr = "liulin";
		String encStr = CpmEncrypt.getInstance().getEncString(testStr);
		String desStr = CpmEncrypt.getInstance().getDesString("");
		System.out.println("testStr=" + testStr);
		System.out.println("encStr=" + encStr);
		System.out.println("desStr=" + desStr);
	} catch (Exception e) {
		// TODO 自动生成 catch 块
		e.printStackTrace();
	}
	
	}

	/* （非 Javadoc）
	 * @see com.tjmcc.process.sso.IntEncrypt#getDesStringByUid(java.lang.String)
	 */
	public String getDesStringByUid(String data) {
		// TODO 自动生成方法存根
		return null;
	}

	/* （非 Javadoc）
	 * @see com.tjmcc.process.sso.IntEncrypt#getEncrStrByUidAndRemoteAppId(java.lang.String, java.lang.String)
	 */
	public String getEncrStrByUidAndRemoteAppId(
		String uid,
		String remoteAppId) {
		// TODO 自动生成方法存根
		return null;
	}

}
