package com.pipipark.j.email;

import org.apache.commons.mail.EmailException;

import com.pipipark.j.email.entity.EmailConfig;
import com.pipipark.j.email.entity.EmailContent;
import com.pipipark.j.system.entity.SimplePPPEntity;
import com.pipipark.j.system.exception.PPPServiceException;


public abstract class Email extends SimplePPPEntity {
	
	private static final long serialVersionUID = 4273779211915951680L;

	public abstract void config(EmailConfig conf);
	
	public void send(EmailContent content) throws PPPServiceException, EmailException{
		EmailManager.handler(this,content);
	}
	
}
