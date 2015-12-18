package com.pipipark.j.sms.exception;

import com.pipipark.j.system.exception.PPPServiceException;


@SuppressWarnings("serial")
public class SmsException extends PPPServiceException {
	
	public SmsException(String msg) {
		super(msg);
	}

	public SmsException(String msg, Exception e) {
		super(msg, e);
	}

}
