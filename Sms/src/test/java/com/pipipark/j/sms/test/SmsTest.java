package com.pipipark.j.sms.test;

import com.pipipark.j.sms.entity.SmsContent;
import com.pipipark.j.system.exception.PPPServiceException;

public class SmsTest {

	public static void main(String[] args) throws PPPServiceException {
		MySms sms = new MySms("xiaowu");
//		sms.send(new SmsContent().content("我是卖报的小行家").mobile("15606979674"));
		sms.send(new SmsContent().content("啦啦啦啦满街跑,一边跑一边叫,今天的新闻真正好.").mobile("15606979674"));
	}

}
