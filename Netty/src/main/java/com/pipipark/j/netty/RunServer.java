package com.pipipark.j.netty;

import com.pipipark.j.netty.handler.ServerBaseHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class RunServer implements Runnable {

	@Override
	public void run() {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel sc)
								throws Exception {
							sc.pipeline().addLast(
									new LineBasedFrameDecoder(1024));
							sc.pipeline().addLast(new StringEncoder());
							sc.pipeline().addLast(new ServerBaseHandler());
//							sc.pipeline().addFirst(new ChunkedReadHandler(5*1024));
						}
					});
			ChannelFuture f;
			try {
				JNIOServer s = JNIOServer.getInstance();
				f = bootstrap.bind(s.getServerPort()).sync();
				f.channel().closeFuture().sync();
			} catch (InterruptedException e) {
			}
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
}
