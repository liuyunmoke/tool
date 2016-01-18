package com.pipipark.j.jpush.message.check;

import com.pipipark.j.jpush.exception.JPushGwrong;
import com.pipipark.j.jpush.message.JMessage;

public class JMessageMustDataCheck implements IMessageCheck {

	@Override
	public void handler(JMessage msg) throws JPushGwrong {
//		if(msg.sender() == null || msg.type() == null || msg.num() == null){
//			throw new JPushGwrong("缺少必要参数(发送者,消息类型,未读数量).");
//		}
	}

}
