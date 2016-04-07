package com.pipipark.j.netty;

import com.pipipark.j.netty.handler.ClientBaseHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class RunClient implements Runnable {

	// public RunClient(ChannelFuture f){
	// this.f = f;
	// }

	@Override
	public void run() {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel sc)
								throws Exception {
							sc.pipeline().addLast(
									new LineBasedFrameDecoder(1024));
							sc.pipeline().addLast(new StringEncoder());
							sc.pipeline().addLast(new ClientBaseHandler());
						}
					});
			ChannelFuture f;
			JNIOClient bc = JNIOClient.getInstance();
			try {
				f = bootstrap.connect(bc.getConnectIP(), bc.getConnectPort())
						.sync();
				f.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} finally {
			group.shutdownGracefully();
		}
	}
}
