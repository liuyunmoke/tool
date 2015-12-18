package com.pipipark.j.database.exception;

import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.exception.PPPServiceException;


@SuppressWarnings("serial")
public class PPPSqlException extends PPPServiceException {

	public PPPSqlException(String msg) {
		super(msg);
	}
	
	public PPPSqlException(String msg, Exception e){
		super(msg, e);
		PPPLogger.info(this, msg, e);
	}

}
