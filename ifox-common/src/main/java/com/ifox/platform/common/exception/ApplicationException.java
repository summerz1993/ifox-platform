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

/**
 * 必须捕获的应用异常 - 自定义异常父类
 * @author Yeager
 */
public class ApplicationException extends Exception {
	private static final long serialVersionUID = 1L;

	public ApplicationException(Integer expStatus, String message) {
	    super("状态码:" + expStatus + "  内容:" + message);
	}

}
