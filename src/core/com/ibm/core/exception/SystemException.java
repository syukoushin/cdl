package com.ibm.core.exception;

public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SystemException() {
	}

	public SystemException(String msg) {
		super(msg);
	}

	public SystemException(Throwable ex) {
		super(ex);
	}

	public SystemException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
