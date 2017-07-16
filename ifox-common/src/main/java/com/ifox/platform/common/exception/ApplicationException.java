/**
 * $Header: ApplicationException.java $
 *
 * -----------------------------------------------------------------------------
 * Version         Modified By     	Modified On   		Modification
 * -----------------------------------------------------------------------------
 * 1.0             Naren Koka     	Oct 1, 2009   	Created.
 * -----------------------------------------------------------------------------
 *
 * Copyright (c) 2009 WIPRO TECHNOLOGIES, Bangalore, India.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of WIPRO TECHNOLOGIES
 * Copying or reproduction without prior written approval is prohibited.
 */
package com.ifox.platform.common.exception;


public class ApplicationException extends Exception {
	private static final long serialVersionUID = 1L;

	protected String errorCode;
	protected Object obj;

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public ApplicationException() {
		super();
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
		if (getCause() == null) {
			initCause(cause);
		}
	}

	public ApplicationException(Throwable cause) {
		super(cause);
		if (getCause() == null) {
			initCause(cause);
		}
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}
}
