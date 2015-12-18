package com.pipipark.j.sms.exception;

import com.pipipark.j.system.exception.PPPTipsException;


@SuppressWarnings("serial")
public class SmsPrompt extends PPPTipsException {
	
	public SmsPrompt(String msg) {
		super(msg);
	}

	public SmsPrompt(String msg, Exception e) {
		super(msg, e);
	}

}
