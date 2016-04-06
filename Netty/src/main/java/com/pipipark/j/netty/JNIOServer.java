package com.pipipark.j.netty;


import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.pipipark.j.netty.annotation.KeyService;
import com.pipipark.j.netty.event.JNIOEvent;
import com.pipipark.j.netty.handler.IHandler;
import com.pipipark.j.system.classscan.v1.PPPScan;


public class JNIOServer{

	private static JNIOServer me = null;
	private JNIOServer(){}
	public static JNIOServer getInstance(){
		if(me==null){
			me = new JNIOServer();
		}
		return me;
	}
	
	
	private String packagePath = "com";
	private String serverIP = "127.0.0.1";
	private Integer serverPort = 9999;
	private Map<Integer, Class<IHandler>> keys = new HashMap<Integer, Class<IHandler>>();
	private Map<ChannelHandlerContext, JNIOEvent> clients = new HashMap<ChannelHandlerContext, JNIOEvent>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void boot(){
		//1. 读取KeyService接口
		PPPScan scaner = new PPPScan();
		Set<Class> clazzs = scaner.getPackageAllClasses(packagePath, true);
		for(Class<?> clazz : clazzs){
			for(Class<?> ic : clazz.getInterfaces()){
				if(ic == IHandler.class){
					//是否KeyService
					if(clazz.isAnnotationPresent(KeyService.class)){
						KeyService sb = clazz.getAnnotation(KeyService.class);
						if(keys.containsKey(sb.value())){
							break;
						}
						keys.put(sb.value(), (Class<IHandler>)clazz);
					}
					break;
				}
			}
		}
		
		//2. 创建线程启动netty服务器
		new Thread(new RunServer()).start();
	}
	
	
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	public String getServerIP() {
		return serverIP;
	}
	public Integer getServerPort() {
		return serverPort;
	}
	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}
	public Map<Integer, Class<IHandler>> getKeys() {
		return keys;
	}
	public void setKeys(Map<Integer, Class<IHandler>> keys) {
		this.keys = keys;
	}
	public Class<IHandler> getKey(Integer key) {
		return keys.get(key);
	}
	public void setKey(Integer key, Class<IHandler> value) {
		this.keys.put(key, value);
	}
	public Map<ChannelHandlerContext, JNIOEvent> getClients() {
		return clients;
	}
	public Object getClient(ChannelHandlerContext ctx) {
		return clients.get(ctx);
	}
	public void setClient(ChannelHandlerContext ctx, JNIOEvent value) {
		this.clients.put(ctx, value);
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
}
