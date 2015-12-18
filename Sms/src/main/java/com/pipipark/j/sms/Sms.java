package com.pipipark.j.sms;

import com.pipipark.j.sms.entity.SmsConfig;
import com.pipipark.j.sms.entity.SmsContent;
import com.pipipark.j.system.entity.SimplePPPEntity;
import com.pipipark.j.system.exception.PPPServiceException;

/**
 * 短信发送工具
 * @see 抽象类，须实现config并进行必要配置后方可发送短信
 * @author cwj
 */
@SuppressWarnings("serial")
public abstract class Sms extends SimplePPPEntity {
	
	/*
	 * 默认短信类型.
	 */
	public static final Integer DEFAULT_SMS_TYPE = 1;
	
	
	/*
	 * 发送者
	 */
	private String sendUser=null;
	
	public Sms(){}
	public Sms(String sendUser){
		this.sendUser = sendUser;
	}

	/*
	 * 用户自定义配置
	 */
	public abstract void config(SmsConfig conf);
	
	public String getSendUser() {
		return sendUser;
	}
	
	/**
	 * 执行短信发送
	 * @param content
	 * @throws ServiceException
	 */
	public void send(SmsContent content) throws PPPServiceException{
		SmsManager.getInstance().handler(this,content);
	}
	
}
