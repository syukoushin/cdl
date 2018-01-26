/**
 * 
 */
package com.ibm.core.service;

import com.ibm.core.util.BaseException;

public class BaseServiceException extends BaseException {

	/**
	 * 
	 */
	public BaseServiceException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public BaseServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public BaseServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BaseServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public BaseServiceException(String errorCode, String message, Throwable throwable) {
		super(errorCode, message, throwable);

	}

}
