package com.pipipark.j.netty.handler;

import com.pipipark.j.netty.JNIOClient;
import com.pipipark.j.netty.event.JNIOEvent;
import com.pipipark.j.netty.message.ReceiveMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientBaseHandler extends ChannelHandlerAdapter  {

	
	/**
	 * 连接成功后,记录本地通信对象.
	 */
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client active");
		JNIOClient client = JNIOClient.getInstance();
		client.setContext(ctx);
		client.setEvent(new JNIOEvent(ctx));
    }
	
	/**
	 * 读取服务端发送的信息.
	 */
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ReceiveMessage message = new ReceiveMessage(ctx, msg);
		System.out.println(ctx.name()+" clientRead! msg: "+ message.getContent().toString());
		JNIOClient client = JNIOClient.getInstance();
		Class<IHandler> clazz= client.getKey(message.getProtocolID());
		clazz.newInstance().handler(message, client.getEvent());
    }
	
	
}
