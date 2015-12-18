package com.pipipark.j.email.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pipipark.j.system.entity.PPPEntity;
import com.pipipark.j.system.entity.field.validate.FieldValidateEnum;
import com.pipipark.j.system.entity.field.validate.annontation.FieldValidate;

public class EmailContent extends PPPEntity {

	
	private static final long serialVersionUID = 8666476985701475898L;

	private String subject;
	
	private String content;
	
	@FieldValidate({FieldValidateEnum.NotNull,FieldValidateEnum.NotEmpty})
	private List<String> receiver = new ArrayList<String>();
	
	
	public String subject() {
		return subject;
	}
	public EmailContent subject(String subject) {
		this.subject = subject;
		return this;
	}
	public String content() {
		return content;
	}
	public EmailContent content(String content) {
		this.content = content;
		return this;
	}
	public List<String> receiver() {
		return receiver;
	}
	public EmailContent receiver(String... receiver) {
		this.receiver.clear();
		this.receiver.addAll(Arrays.asList(receiver));
		return this;
	}
	@Override
	public void desc(StringBuilder string) throws Exception {
		
	}
	
}
