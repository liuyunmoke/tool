package com.pipipark.j.jpush.message.check;

import com.pipipark.j.jpush.exception.JPushGwrong;
import com.pipipark.j.jpush.message.JMessage;

public interface IMessageCheck {

	public void handler(JMessage message) throws JPushGwrong;
}
