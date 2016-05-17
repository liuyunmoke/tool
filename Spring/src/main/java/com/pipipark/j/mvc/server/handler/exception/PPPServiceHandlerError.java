package com.pipipark.j.mvc.server.handler.exception;

import com.pipipark.j.system.exception.PPPSystemError;

/***
 *
 */
@SuppressWarnings("serial")
public class PPPServiceHandlerError extends PPPSystemError {

	public PPPServiceHandlerError(String msg) {
		super(msg);
	}
	
}
