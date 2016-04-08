package com.pipipark.j.netty.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.pipipark.j.netty.constant.NID;
import com.pipipark.j.netty.exception.MessageException;
import com.pipipark.j.netty.util.JsonUtil;

import net.sf.json.JSONObject;

/**
 * 服务端发送消息
 * 
 * @author cwj
 *
 */
public class SendMessage {

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public static void send(ChannelHandlerContext ctx, Integer protocolID,
			Object param) {
		if (ctx == null) {
			throw new MessageException("not connect!");
		}
		JSONObject json = null;
		if (param instanceof Map) {
			json = JsonUtil.mapToJson((Map) param);
		} else {
			json = JsonUtil.beanToJson(param);
		}
		json.put(NID.PROTOCOL_ID, protocolID);
		if (json != null) {
//			try {
//				byte[] data = Base64.encodeBase64(addRN("@1" + json.toString())
//						.getBytes());
//				String str = new String(data, "UTF-8");

				synchronized (ctx) {
					ctx.channel().writeAndFlush(
							Unpooled.copiedBuffer(addRN("@1" + json.toString()).getBytes()));
				}
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
			return;
		}
		throw new MessageException("sendMessage json is null!");
	}

	public static void sendFile(ChannelHandlerContext ctx, File file)
			throws IOException {

		int size = (int) file.length();
		FileInputStream in = new FileInputStream(file);
		byte[] fileByte = new byte[size + 2];
		in.read(fileByte);
		
		
//		System.arraycopy(fileByte, 0, image, 0, fileByte.length);
//		System.arraycopy(rnByte, 0, image, fileByte.length, rnByte.length);

//		byte[] data = Base64.encodeBase64(image);
		// System.out.println(data);
		// byte[] strData = Base64.decodeBase64(data);
		// System.out.println(strData);
//		String str = new String(data, "UTF-8");

		synchronized (ctx) {
			ctx.channel().writeAndFlush(Unpooled.copiedBuffer(fileByte));
		}

		// final ChannelHandlerContext cctx = ctx;
		// ctx.writeAndFlush(String.valueOf(file.length()));
		// ctx.pipeline().addFirst(new ChunkedWriteHandler());
		// ctx.channel().writeAndFlush(new ChunkedFile(file));
		//
		// ChannelFuture f = ctx.writeAndFlush(new ChunkedFile(file));
		//
		// f.addListener(new ChannelFutureListener() {
		//
		// public void operationComplete(ChannelFuture future)
		// throws Exception {
		// cctx.pipeline().removeFirst();
		// }
		// });
		// ChannelFuture ch = ctx.writeAndFlush("end\n");
		// ch.addListener(ChannelFutureListener.CLOSE);

		// FileInputStream fis = new FileInputStream(file);
		// int count = 0;
		// for (;;) {
		// BufferedInputStream bis = new BufferedInputStream(fis);
		// byte[] bytes = new byte[8];
		// int readNum = bis.read(bytes, 0, 8);
		// if (readNum == -1) {
		// return;
		// }
		// ByteBuf buffer = Unpooled.copiedBuffer(bytes, 0, 8);
		// ctx.writeAndFlush(buffer);
		// System.out.println("Send count: " + ++count);
		// }
	}

	private static String addRN(String msg) {
		return (msg += "\r\n");
	}

}
