package com.pipipark.j.netty.handler;

import java.util.Map;

import com.pipipark.j.netty.JNIOServer;
import com.pipipark.j.netty.event.JNIOEvent;
import com.pipipark.j.netty.exception.ProtocolBreakException;
import com.pipipark.j.netty.message.ReceiveMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerBaseHandler extends ChannelHandlerAdapter {
	
	private int count = 0;
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ReceiveMessage message = new ReceiveMessage(ctx, msg);
		System.out.println(ctx.name()+" serverRead! msg: "+ message.getContent().toString());
		Class<IHandler> clazz= JNIOServer.getInstance().getKey(message.getProtocolID());
		clazz.newInstance().handler(message, JNIOServer.getInstance().getClients().get(ctx));
    }
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		count++;
		System.out.println("server active : 连接" +count+".");
		JNIOEvent eve = new JNIOEvent(ctx);
		JNIOServer.getInstance().getClients().put(ctx, eve);
    }
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Map<ChannelHandlerContext, JNIOEvent> client = JNIOServer.getInstance().getClients();
		JNIOEvent eve = client.get(ctx);
		if(eve!=null){
			eve.dispatchListener(JNIOEvent.DISCONNECT_EVENT);
			eve.clear();
		}
		client.remove(ctx);
		ctx.close();
		
		throw new ProtocolBreakException(cause.getMessage());
    }
	
	
	

	@Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("added");
    }
	
	@Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("removed");
    }
	
	@Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("registered");
        ctx.fireChannelRegistered();
    }
	
	@Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("inactive");
        ctx.fireChannelInactive();
    }
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("readComplete");
        ctx.fireChannelReadComplete();
    }
	
	@Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		System.out.println("writabilityChanged");
        ctx.fireChannelWritabilityChanged();
    }
	
}
