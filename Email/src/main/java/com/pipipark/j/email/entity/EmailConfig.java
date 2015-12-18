package com.pipipark.j.email.entity;

import com.pipipark.j.system.entity.PPPEntity;
import com.pipipark.j.system.entity.field.validate.FieldValidateEnum;
import com.pipipark.j.system.entity.field.validate.annontation.FieldValidate;

public class EmailConfig extends PPPEntity {
	
	private static final long serialVersionUID = 6670782399746874316L;

	@FieldValidate({FieldValidateEnum.NotNull,FieldValidateEnum.NotEmpty})
	private String host;
	
	@FieldValidate({FieldValidateEnum.NotNull,FieldValidateEnum.NotEmpty})
	private String username;
	
	@FieldValidate({FieldValidateEnum.NotNull,FieldValidateEnum.NotEmpty})
	private String dispalyname;
	
	@FieldValidate({FieldValidateEnum.NotNull,FieldValidateEnum.NotEmpty})
	private String password;
	
	
	
	public String host() {
		return host;
	}

	public EmailConfig host(String host) {
		this.host = host;
		return this;
	}
	
	public String dispalyname() {
		return dispalyname;
	}

	public EmailConfig dispalyname(String dispalyname) {
		this.dispalyname = dispalyname;
		return this;
	}

	public String username() {
		return username;
	}

	public EmailConfig username(String username) {
		this.username = username;
		return this;
	}

	public String password() {
		return password;
	}

	public EmailConfig password(String password) {
		this.password = password;
		return this;
	}

	@Override
	public void desc(StringBuilder string) throws Exception {
		
	}


}
