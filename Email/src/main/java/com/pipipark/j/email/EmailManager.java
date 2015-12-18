package com.pipipark.j.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.pipipark.j.email.entity.EmailConfig;
import com.pipipark.j.email.entity.EmailContent;
import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.entity.field.validate.FieldVaildater;
import com.pipipark.j.system.exception.PPPServiceException;

public class EmailManager {

	
	public static final void handler(Email email, EmailContent content) throws PPPServiceException, EmailException{
		FieldVaildater.vaildate(content);
		
		EmailConfig conf = new EmailConfig();
		email.config(conf);
		FieldVaildater.vaildate(conf);
		
		HtmlEmail sender = new HtmlEmail();
		sender.setCharset(PPPConstant.Systems.DEFAULT_CHARSET);
		sender.setFrom(conf.username());
		sender.setHostName(conf.host());
		sender.setAuthentication(conf.username(),conf.password());
		sender.setSubject(content.subject());
		sender.setHtmlMsg(content.content());
		sender.addTo(content.receiver().toArray(new String[]{}));
		sender.send();
		
	}
}
