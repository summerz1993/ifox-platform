/**
 * $Header: ServiceException.java $
 *
 * -----------------------------------------------------------------------------
 * Version         Modified By     	Modified On   		Modification
 * -----------------------------------------------------------------------------
 * 1.0             Naren Koka     		Dec 22, 2009   			Created.
 * -----------------------------------------------------------------------------
 *
 * Copyright (c) 2009 WIPRO TECHNOLOGIES, Bangalore, India.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of WIPRO TECHNOLOGIES
 * Copying or reproduction without prior written approval is prohibited.
 */
package com.ifox.platform.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceException extends ApplicationException {

	private static final long serialVersionUID = 203351085671021997L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceException.class);

	public ServiceException() {
		super();
	}

	public ServiceException(String str, String errorCode, Object obj) {
		super(str);
		this.errorCode = errorCode;
		this.obj = obj;
		LOGGER.error("ServiceException  message str :"+str+" errorcode : "+errorCode);
	}

	public ServiceException(String message) {
		super(message);
		LOGGER.error("ServiceException  message :"+message);
	}

	public ServiceException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		LOGGER.error("ServiceException errorCode :"+errorCode+ " message :"+message);
	}

	public ServiceException(String errorCode, String message,
							Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		LOGGER.error("ServiceException errorCode :"+errorCode+ " message :"+message+ " cause : "+cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		LOGGER.error("ServiceException  message :"+message);
		if (getCause() == null) {
			initCause(cause);
		}
	}
	
}
