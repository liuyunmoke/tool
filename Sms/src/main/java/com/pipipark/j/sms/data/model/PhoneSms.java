package com.pipipark.j.sms.data.model;

import java.sql.Timestamp;
import java.util.Date;

import com.pipipark.j.database.PPPModel;
import com.pipipark.j.database.annotation.Db;
import com.pipipark.j.database.annotation.PrimaryKey;
import com.pipipark.j.sms.data.SmsDB;

@SuppressWarnings("serial")
@Db(SmsDB.class)
public class PhoneSms extends PPPModel {

	
	@PrimaryKey
	private Integer id;
	
	/*
	 * 手机号.
	 */
	private String phone;
	
	/*
	 * 内容.
	 */
	private String content;
	
	/*
	 * 类型.
	 */
	private Integer type;
	
	/*
	 * 发送人
	 */
	private String sender = "System";
	
	/*
	 * 发送时间.
	 */
	private Timestamp updateDate;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
}
