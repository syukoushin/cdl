package com.ibm.core.util;

import java.security.Key;

public class CodecEntity{

	/**
	 * 空构造函数，通过该构造函数将会初始化key
	 */
	public CodecEntity() {
		this.initKey();
	}

	/**
	 * 解密构造函数
	 * 
	 * @param key hex秘钥
	 * @param value 密文
	 */
	public CodecEntity(String key, String value) {
		try {
			this.k = DESCodec.toKey(Hex.decode(key));
			// 解码
			byte[] hexByte = Hex.decode(value);
			// 对称秘钥解密
			byte[] decryptData = DESCodec.decrypt(hexByte, this.k);
			this.value = new String(decryptData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 装载key，构造加解密类
	 * 
	 * @param key 秘钥
	 */
	public CodecEntity(String key) {
		try {
			this.keyHex = key;
			this.k = DESCodec.toKey(Hex.decode(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化秘钥，将会创建key，通过getK(),getKeyHex()获取秘钥 初始化之后可以通过getKey获取秘钥，
	 * 并且可以通过秘钥进行加密
	 */
	public void initKey() {
		try {
			byte[] key = DESCodec.initSecretKey();
			this.k = DESCodec.toKey(key);
			this.keyHex = Hex.encode(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密
	 * 
	 * @param value 需要加密的值
	 * @return 加密后的值
	 */
	public String encrypt(String value) {
		try {
			if (this.k == null) {
				this.initKey();
			}
			byte[] encryptData = DESCodec.encrypt(value.getBytes(), this.k);
			this.keyHex = Hex.encode(this.k.getEncoded());
			return Hex.encode(encryptData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private Key k;
	private String value;
	private String keyHex;

	public String getKeyHex() {
		return keyHex;
	}

	public void setKeyHex(String keyHex) {
		this.keyHex = keyHex;
	}

	public Key getK() {
		return k;
	}

	public void setK(Key k) {
		this.k = k;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static void main(String args[]) {
		try {
			String data = "13asdnlkanfklanfgklanfalksmflnalfanl";
			// 加密
			CodecEntity entity = new CodecEntity(data);
			System.out.println(entity.getKeyHex());
			System.out.println(entity.getValue());
			CodecEntity entity1 = new CodecEntity("F4856B3E8AADB51A", "B0E230307FED02FB797EEF3EF0697E497C2088815C8FF705A1D50038EF3423D75C27A90B995B1567");
			System.out.println(entity1.getValue());
			CodecEntity entity2 = new CodecEntity();
			System.out.println("key:" + entity2.getKeyHex() + "-----value:" + entity2.encrypt(data));
			System.out.println(entity2.encrypt("123131313adasad"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
