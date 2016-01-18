package com.pipipark.j.netty.message;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

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
		if(ctx==null){
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
			synchronized (ctx) {
				ctx.channel()
						.writeAndFlush(
								Unpooled.copiedBuffer(addRN(json.toString())
										.getBytes()));
			}
			return;
		}
		throw new MessageException("sendMessage json is null!");
	}

	private static String addRN(String msg) {
		return (msg += "\r\n");
	}

}
