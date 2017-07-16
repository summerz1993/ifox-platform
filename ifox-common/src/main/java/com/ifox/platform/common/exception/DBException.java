/**
 * $Header: DBException.java $
 *
 * -----------------------------------------------------------------------------
 * Version         Modified By     	Modified On   		Modification
 * -----------------------------------------------------------------------------
 * 1.0             Naren Koka     	Nov 19, 2009   			Created.
 * -----------------------------------------------------------------------------
 *
 * Copyright (c) 2009 WIPRO TECHNOLOGIES, Bangalore, India.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of WIPRO TECHNOLOGIES
 * Copying or reproduction without prior written approval is prohibited.
 */
package com.ifox.platform.common.exception;


public class DBException extends ApplicationException {

	private static final long serialVersionUID = 7050974795190193735L;

	// This errorcode can be used to propagate to the biz layers
	private String errorCode;

	public String getErrorCode() {
		return this.errorCode;
	}

	public DBException() {
		super();
	}

	public DBException(String message) {
		super(message);
	}

	public DBException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public DBException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
}