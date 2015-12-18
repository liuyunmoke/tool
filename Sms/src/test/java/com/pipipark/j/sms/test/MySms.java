package com.pipipark.j.sms.test;

import com.pipipark.j.sms.Sms;
import com.pipipark.j.sms.entity.SmsConfig;


@SuppressWarnings("serial")
public class MySms extends Sms {

	public MySms(String sendUser) {
		super(sendUser);
	}

	@Override
	public void config(SmsConfig conf) {
		conf.address("http://218.207.176.76:9000/sendXSms.do")
		.username("haixia")
		.password("4001178123")
		.addParams("productid", "149075")
		.addParams("dstime", "")
		.addParams("xh", "");
	}

}
