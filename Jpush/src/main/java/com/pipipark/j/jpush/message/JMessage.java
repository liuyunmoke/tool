package com.pipipark.j.jpush.message;

import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.pipipark.j.jpush.client.JPushClient;

public class JMessage {

	public JMessage(JPushClient client) {
		this.client = client;
		title = DEFAULT_TITLE;
		sender = DEFAULT_SENDER;
		receive = DEFAULT_RECEIVE;
		type = DEFAULT_TYPE;
		content = DEFAULT_CONTENT;
		sound = DEFAULT_SOUND;
		shake = DEFAULT_SHAKE;
	}

	public static final String DEFAULT_TITLE = "alert";
	public static final String DEFAULT_SENDER = null;
	public static final String DEFAULT_RECEIVE = null;
	public static final Integer DEFAULT_TYPE = null;
	public static final Integer DEFAULT_NUM = null;
	public static final String DEFAULT_CONTENT = "";
	public static final Integer DEFAULT_SOUND = 1;
	public static final Integer DEFAULT_SHAKE = 1;
	private JPushClient client = null;
	private String title = "alert";
	private String sender = null;// 发送者
	private String receive = null;// 接收者
	private Integer type = null;// 消息类型
	private Integer badge = 1;// 数量总数
	private String content = "";// 消息内容
	private Integer sound = 1;// 声音
	private Integer shake = 1;// 震动

	/******** get *********/
	public String title() {
		return this.title;
	}

	public String sender() {
		return this.sender;
	}

	public String receive() {
		return this.receive;
	}

	public Integer type() {
		return this.type;
	}


	public Integer badge() {
		if (this.badge == null) {
			this.badge = 1;
		}
		return this.badge;
	}

	public String content() {
		return this.content;
	}

	public Integer sound() {
		if (this.sound == null) {
			this.sound = 1;
		}
		return this.sound;
	}

	public Integer shake() {
		if (this.shake == null) {
			this.shake = 1;
		}
		return this.shake;
	}

	/******** set *********/
	public JMessage title(String title) {
		this.title = title;
		return this;
	}

	public JMessage sender(String sender) {
		this.sender = sender;
		return this;
	}

	public JMessage receive(String receive) {
		this.receive = receive;
		return this;
	}

	public JMessage type(Integer type) {
		this.type = type;
		return this;
	}

	public JMessage badge(Integer badge) {
		this.badge = badge;
		return this;
	}

	public JMessage content(String content) {
		this.content = content;
		return this;
	}

	public JMessage sound(Integer sound) {
		this.sound = sound;
		return this;
	}

	public JMessage shake(Integer shake) {
		this.shake = shake;
		return this;
	}

	public String sendPayload(PushPayload... payloads) {
		String msg = "";
		try {
			for (PushPayload payload : payloads) {
				PushResult pr = null;
				pr = client.sendPush(payload);
				if (pr == null) {
					continue;
				}
				msg += pr.sendno + ": " + pr.toString() + "\r\n";
			}
		} catch (Exception e) {
			msg += e.getMessage() + "\r\n";
		}
		return msg;
	}
}
