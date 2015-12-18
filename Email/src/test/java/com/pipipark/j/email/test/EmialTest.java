package com.pipipark.j.email.test;

import org.apache.commons.mail.EmailException;

import com.pipipark.j.email.entity.EmailContent;
import com.pipipark.j.system.exception.PPPServiceException;

public class EmialTest {

	public static void main(String[] args) throws PPPServiceException, EmailException {
		new MyEmail().send(new EmailContent().subject("身体情况").content("Good2.").receiver("liuyunmoke@163.com","408200606@qq.com"));
	}

}
