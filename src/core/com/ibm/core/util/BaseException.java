package com.ibm.core.util;
/**
 * 错误编号：
 * 错误描述：
 * 出错原因：
 * 解决方法：
 * @author ahxu
 *
 */
public class BaseException extends Exception {
	
	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public BaseException() {
		// TODO Auto-generated constructor stub
	}

	public BaseException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BaseException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param errorCode
	 * @param arg0
	 * @param arg1
	 */
	public BaseException(String errorCode,String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.errorCode = errorCode;
	}

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
 

}
