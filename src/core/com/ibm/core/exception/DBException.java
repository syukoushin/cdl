package com.ibm.core.exception;

public class DBException extends SystemException {
	private static final long serialVersionUID = 1L;

	public DBException() {
	}

	public DBException(String msg) {
		super(msg);
	}

	public DBException(Throwable ex) {
		super(ex);
	}

	public DBException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
