package com.pipipark.j.email.test;

import com.pipipark.j.email.Email;
import com.pipipark.j.email.entity.EmailConfig;

@SuppressWarnings("serial")
public class MyEmail extends Email {

	@Override
	public void config(EmailConfig conf) {
		conf.host("smtp.qq.com").dispalyname("陈文杰").username("14822326@qq.com").password("Qcwj1984611.5");
	}

}
