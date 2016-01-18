package com.pipipark.j.netty.handler;

import com.pipipark.j.netty.event.JNIOEvent;
import com.pipipark.j.netty.message.ReceiveMessage;


public interface IHandler {

	void handler(ReceiveMessage msg, JNIOEvent event);
}
