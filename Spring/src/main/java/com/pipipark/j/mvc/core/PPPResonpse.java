package com.pipipark.j.mvc.core;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.core.PPPCode;


/***
 * 返回值对象.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPResonpse implements IPPPark {

	/*
	 * 默认值: 000000,使用默认方式处理返回.
	 * 成功值: 1xxxxx,第一位使用1表示成功,后续表示功能和业务分类.
	 * 失败值: 2xxxxx,第一位使用2表示失败,后续表示功能和业务分类.
	 * 其他值: 待定.
	 */
	private String state = PPPCode.StateCode.DEFAULT;
	
	/*
	 * 默认值: 0000,采取客户端自定义方式实现动作.
	 * 其他值: 待定.
	 */
	private String action = PPPCode.ActionCode.DEFAULT;
	
	//消息体
	private String message;
	
	//返回数据
	private Object data;
	

	public String state() {
		return state;
	}

	public void state(String state) {
		this.state = state;
	}
	
	public String action() {
		return action;
	}

	public void action(String action) {
		this.action = action;
	}

	public String message() {
		return message;
	}

	public void message(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
