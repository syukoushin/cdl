package com.ibm.core.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESCodec{

	// 算法名称
	public static final String KEY_ALGORITHM = "DES";
	// 算法名称/加密模式/填充方式
	// DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
	public static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
	
	/**
	 * 初始化密钥
	 * 
	 * @return byte[] 密钥
	 * @throws Exception
	 */
	public static byte[] initSecretKey() throws Exception {
		// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 初始化此密钥生成器，使其具有确定的密钥大小
		kg.init(56);
		// 生成一个密钥
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 转换密钥
	 * 
	 * @param key 二进制密钥
	 * @return Key 密钥
	 * @throws Exception
	 */
	public static Key toKey(byte[] key) throws Exception {
		// 实例化DES密钥规则
		DESKeySpec dks = new DESKeySpec(key);
		// 实例化密钥工厂
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		// 生成密钥
		SecretKey secretKey = skf.generateSecret(dks);
		return secretKey;
	}
	
	/**
	 * 加密
	 * 
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data 待加密数据
	 * @param key 二进制密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data 待加密数据
	 * @param key 二进制密钥
	 * @param cipherAlgorithm 加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 加密
	 * 
	 * @param data 待加密数据
	 * @param key 密钥
	 * @param cipherAlgorithm 加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * @param data 待解密数据
	 * @param key 二进制密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密
	 * 
	 * @param data 待解密数据
	 * @param key 密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * 解密
	 * 
	 * @param data 待解密数据
	 * @param key 二进制密钥
	 * @param cipherAlgorithm 加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 解密
	 * 
	 * @param data 待解密数据
	 * @param key 密钥
	 * @param cipherAlgorithm 加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 输出byte
	 * 
	 * @param data
	 * @return
	 */
	public static String showByteArray(byte[] data) {
		if (null == data) {
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for (byte b : data) {
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		// 初始化秘钥
		byte[] key = initSecretKey();
		System.out.println("key:"+Hex.encode(key));
		System.out.println("key：" + showByteArray(key));
		// 秘钥转换
		Key k = toKey(key);
		// 测试数据
		String data = "中文参数加密";
		System.out.println("加密前数据: string:" + data);
		System.out.println("加密前数据: byte[]:" + showByteArray(data.getBytes()));
		System.out.println();
		// 加密
		byte[] encryptData = encrypt(data.getBytes(), k);
		String hexData = Hex.encode(encryptData);//二进制转换成16进制数据
		System.out.println("加密后数据: byte[]:" + showByteArray(encryptData));
		System.out.println("加密后数据: hexStr:" + hexData);
		System.out.println();
		// 解码
		byte[] hexByte = Hex.decode(hexData);//将机密后转化成16进制的数据转化成二进制  准备进行解码
		// 对称秘钥解密
		byte[] decryptData = decrypt(hexByte, k);
		System.out.println("解密后数据: byte[]:" + showByteArray(decryptData));
		System.out.println("解密后数据: string:" + new String(decryptData));
	}
}
