package com.pipipark.j.netty;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.pipipark.j.netty.annotation.KeyService;
import com.pipipark.j.netty.event.JNIOEvent;
import com.pipipark.j.netty.handler.IHandler;
import com.pipipark.j.system.classscan.v1.PPPScan;

import io.netty.channel.ChannelHandlerContext;

public class JNIOClient{

	private static JNIOClient me = null;
	private JNIOClient(){}
	public static JNIOClient getInstance(){
		if(me==null){
			me = new JNIOClient();
		}
		return me;
	}
	
	private String packagePath = "";
	private String connectIP = null;
	private Integer connectPort = null;
	private ChannelHandlerContext context = null;
	private JNIOEvent event = null;
	private Map<Integer, Class<IHandler>> keys = new HashMap<Integer, Class<IHandler>>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void connect(String ip, Integer port){
		connectIP = ip;
		connectPort = port;
		
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
		
		new Thread(new RunClient()).start();
	}
	public String getConnectIP() {
		return connectIP;
	}
	public void setConnectIP(String connectIP) {
		this.connectIP = connectIP;
	}
	public Integer getConnectPort() {
		return connectPort;
	}
	public void setConnectPort(Integer connectPort) {
		this.connectPort = connectPort;
	}
	public ChannelHandlerContext getContext() {
		return context;
	}
	public void setContext(ChannelHandlerContext context) {
		this.context = context;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
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
	public JNIOEvent getEvent() {
		return event;
	}
	public void setEvent(JNIOEvent event) {
		this.event = event;
	}
}
