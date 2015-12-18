package com.pipipark.j.sms.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.core.PPPEnum;
import com.pipipark.j.system.entity.PPPEntity;
import com.pipipark.j.system.entity.field.validate.annontation.FieldValidate;
import com.pipipark.j.system.exception.PPPServiceException;

/**
 * 短信发送者配置类
 * @see 配置重要参数
 * @author cwj
 */
public class SmsConfig extends PPPEntity {
	
	
	private static final long serialVersionUID = 3319502574679055473L;

	/*
	 * 访问短信服务器是否使用rest风格
	 */
	@FieldValidate
	private Boolean restful = false;
	
	/*
	 * 发送方式
	 */
	@FieldValidate
	private PPPEnum.HttpMethod method = PPPEnum.HttpMethod.get;

	/*
	 * 发送地址
	 */
	@FieldValidate
	private String address = null;
	
	/*
	 * 帐号
	 */
	@FieldValidate
	private String username = null;
	
	/*
	 * 密码
	 */
	@FieldValidate
	private String password = null;
	
	/*
	 * 时间戳
	 */
	private Date time = null;
	
	/*
	 * 其他自定义参数
	 */
	private Map<String,String> params = new HashMap<String, String>();
	
	
	/**
	 * 拼接组装(暂时不使用restful风格)
	 * @throws ServiceException 
	 */
	@Override
	public void desc(StringBuilder string) throws Exception{
		string.append(address);
		string.append("?");
		string.append(this.serializedName("username"));
		string.append("=");
		string.append(username);
		string.append("&");
		string.append(this.serializedName("password"));
		string.append("=");
		string.append(password);
		string.append("&");
		time = Calendar.getInstance().getTime();
		string.append("time=");
		string.append(time.getTime());
		for (Iterator<String> ite = params.keySet().iterator();ite.hasNext();) {
			String key = ite.next();
			string.append("&");
			string.append(key);
			string.append("=");
			try {
				String val = params.get(key);
				val = URLEncoder.encode(val, PPPConstant.Systems.DEFAULT_CHARSET);
				string.append(val);
			} catch (UnsupportedEncodingException e) {
				throw new PPPServiceException("url encoder happen Exception!", e);
			}
		}
	}
	
	public Boolean restful() {
		return restful;
	}
	public SmsConfig restful(Boolean restful) {
		this.restful = restful;
		return this;
	}
	public PPPEnum.HttpMethod method() {
		return method;
	}
	public SmsConfig method(PPPEnum.HttpMethod method) {
		this.method = method;
		return this;
	}
	public String address() {
		return address;
	}
	public SmsConfig address(String address) {
		this.address = address;
		return this;
	}
	public String username() {
		return username;
	}
	public SmsConfig username(String username) {
		this.username = username;
		return this;
	}
	public String password() {
		return password;
	}
	public SmsConfig password(String password) {
		this.password = password;
		return this;
	}
	public Map<String, String> params() {
		return params;
	}
	public SmsConfig addParams(String key, String value) {
		this.params.put(key, value);
		return this;
	}
}
