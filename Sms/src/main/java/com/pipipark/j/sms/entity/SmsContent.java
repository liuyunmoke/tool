package com.pipipark.j.sms.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.entity.PPPEntity;
import com.pipipark.j.system.entity.field.validate.FieldValidateEnum;
import com.pipipark.j.system.entity.field.validate.annontation.FieldValidate;
import com.pipipark.j.system.exception.PPPServiceException;

/**
 * 短信内容
 * @author cwj
 */
public class SmsContent extends PPPEntity {
	
	private static final long serialVersionUID = -4681677492674853085L;
	/*
	 * 接收者手机号码.
	 */
	@FieldValidate({FieldValidateEnum.NotEmpty})
	private List<String> mobile = new ArrayList<String>();
	
	/*
	 * 接收内容.
	 */
	@FieldValidate({FieldValidateEnum.NotEmpty})
	private String content = null;
	
	/*
	 * url编码后的内容.
	 */
	private String encodeContent = null;
	
	/*
	 * 接收图片.
	 */
	private String picture;
	
	/*
	 * 短信类型.
	 */
	private Integer type = 1;
	
	
	/**
	 * 拼接(暂时不使用restful风格)
	 * @throws Exception 
	 */
	@Override
	public void desc(StringBuilder string) throws Exception {
		string.append("&");
		string.append(this.serializedName("content"));
		string.append("=");
		try {
			encodeContent = URLEncoder.encode(content, PPPConstant.Charset.Default.value());
		} catch (UnsupportedEncodingException e) {
			throw new PPPServiceException("url encoder happen Exception!", e);
		}
		string.append(encodeContent);
		string.append("&");
		string.append(this.serializedName("mobile"));
		string.append("=");
		for (String phone : mobile) {
			string.append(phone);
			string.append(",");
		}
		string.deleteCharAt(string.length()-1);
	}

	public List<String> mobile() {
		return mobile;
	}

	public SmsContent mobile(String... mobile) {
		this.mobile.clear();
		this.mobile.addAll(Arrays.asList(mobile));
		return this;
	}
	
	public String content() {
		return content;
	}

	public SmsContent content(String content) {
		this.content = content;
		return this;
	}
	
	public String encodeContent() {
		return encodeContent;
	}

	public String picture() {
		return picture;
	}

	public SmsContent picture(String picture) {
		this.picture = picture;
		return this;
	}

	public Integer type() {
		return type;
	}
	
	public SmsContent type(Integer type) {
		this.type = type;
		return this;
	}
}
