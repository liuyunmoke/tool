package com.pipipark.j.netty.event;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JNIOEvent {
	
	//TCP连接断开事件
	public static final String DISCONNECT_EVENT = "disconnect";

	private ChannelHandlerContext ctx = null;
	private Map<String, List<IEvent>> events = new HashMap<String, List<IEvent>>();
	
	public JNIOEvent(ChannelHandlerContext ctx){
		this.ctx = ctx;
	}
	
	public void addListener(String eventName, IEvent eve){
		List<IEvent> list = events.get(eventName);
		if(list==null){
			list = new ArrayList<IEvent>();
		}
		list.add(eve);
	}
	
	public void dispatchListener(String eventName){
		List<IEvent> list = events.get(eventName);
		if(list==null){
			return;
		}
		for (IEvent eve : list) {
			eve.handler(this);
		}
	}
	
	public ChannelHandlerContext getChannelHandlerContext(){
		return ctx;
	}
	public void clear(){
		ctx = null;
		events = null;
	}
}
