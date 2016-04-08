package com.pipipark.j.netty.message;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;










import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONObject;

import com.pipipark.j.netty.constant.NID;
import com.pipipark.j.netty.util.JsonUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 服务端接收消息
 * @author cwj
 * @see protocolID: 消息所属协议.
 * 
 */
public class ReceiveMessage {

	private Integer protocolID = null;
	private ByteBuf msg = null;
	private ChannelHandlerContext handlerContext = null;
	private JSONObject content = null;
	private Map<String, Object> chacheMap = null;
	private Object chacheBean = null;
	
	public Integer getProtocolID() {
		return protocolID;
	}
	public void setProtocolID(Integer protocolID) {
		this.protocolID = protocolID;
	}
	public JSONObject getContent() {
		return content;
	}
	public ByteBuf getMsg() {
		return msg;
	}
	public ChannelHandlerContext getHandlerContext() {
		return handlerContext;
	}
	public ReceiveMessage(ChannelHandlerContext context, Object buf) throws Exception{
		handlerContext = context;
		msg = (ByteBuf)buf;
		byte[] data = new byte[msg.readableBytes()];
		msg.readBytes(data);
		String msgDataStr = new String(data,"utf-8");
		if(msgDataStr.charAt(0) == '@' && msgDataStr.charAt(1) == '1'){
			content = JSONObject.fromObject(msgDataStr.substring(2,msgDataStr.length()));
			protocolID = content.getInt(NID.PROTOCOL_ID);
		}else{
			byte[] fileData = Base64.decodeBase64(data);
			String fileStr = new String(fileData,"utf-8");
			Map<String, byte[]> file = new HashMap<String, byte[]>();
			file.put("file", fileStr.getBytes());
			content = JSONObject.fromObject(file);
			protocolID = 39393939;
		}
		content.remove(NID.PROTOCOL_ID);
		
//		String msgStr = new String(msgData,"utf-8");
//		
//		
//		if("@1".equals(type)){
//			content = JSONObject.fromObject(str);
//			protocolID = content.getInt(NID.PROTOCOL_ID);
//		}else{
//			Map<String, byte[]> file = new HashMap<String, byte[]>();
//			file.put("file", str.getBytes());
//			content = JSONObject.fromObject(file);
//			protocolID = 39393939;
//		}
	}
	
	public Map<String, Object> toMap(){
		if(chacheMap==null){
			chacheMap = JsonUtil.jsonToMap(content);
		}
		return chacheMap;
	}
	
	
	public Object get(String key){
		return toMap().get(key);
	}
	
	public String getString(String key){
		return toMap().get(key).toString();
	}
	
	public Integer getInt(String key){
		Object val = toMap().get(key);
		return Integer.parseInt(val.toString());
	}
	
	public Float getFloat(String key){
		Object val = toMap().get(key);
		return Float.parseFloat(val.toString());
	}
	
	@SuppressWarnings("unchecked")
	public <T> T toBean(Class<T> clazz){
		if(chacheBean==null){
			chacheBean = JsonUtil.jsonToBean(clazz, content);
		}		
		return (T)chacheBean;
	}
	
	public void reply(Object param){
		SendMessage.send(handlerContext, protocolID, param);
	}
}
