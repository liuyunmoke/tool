package com.pipipark.j.jpush;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.pipipark.j.jpush.client.JPushClientFactory;
import com.pipipark.j.jpush.exception.JPushGwrong;
import com.pipipark.j.jpush.message.JMessage;
import com.pipipark.j.jpush.message.check.JMessageMustDataCheck;
import com.pipipark.j.jpush.message.check.MessageCheckLevel;
import com.pipipark.j.jpush.message.check.MessageChecker;
import com.pipipark.j.system.core.PPPString;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.utils.StringUtils;

/****
 * 极光推送工具类
 * @author Cwj
 */
public class JPush {
	
	private static JPush me = null;
	private JPushClientFactory factory = null;
	private MessageChecker checker = new MessageChecker();
	private Timer aliasTimer = null;
	private Vector<String> lazyAlias = new Vector<String>();
	
	private JPush(){
		factory = new JPushClientFactory();
		checker = new MessageChecker();
		checker.addMessageCheck(new JMessageMustDataCheck(), MessageCheckLevel.LEVEL_HIGHTEST);
	}
	
	/**
	 * 极光推送客户端
	 */
	

	/***
	 * 根据分组发送消息
	 * @param from
	 * @param param
	 * @param toTags
	 * @return 极光返回码
	 * @throws JPushGwrong 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static String sendByTags(JMessage msg, String... toTags){
		JPush push = getInstance();
		String returnMsg="";
		try {
//			push.checker.check(msg);
			if(StringUtils.isEmpty(msg.content())){
				msg.content("您有新消息");
			}
//			returnMsg = msg.sendPayload(push.createPayload(msg,Audience.tag(JPushMD5.batchMd5(toTags))).toArray(new PushPayload[1]));
			returnMsg = msg.sendPayload(push.createPayload(msg,Audience.tag(toTags)).toArray(new PushPayload[1]));
		} catch (Exception e) {
//			e.printStackTrace();
			returnMsg = "极光推送发生内部错误.错误类型:"+e.getClass().getName();
		}
		return returnMsg;
	}
	
	/**
	 * 根据别名发送消息
	 * @param from
	 * @param param
	 * @param alias
	 * @return 极光返回码
	 * @throws JPushGwrong 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static String sendByAlias(JMessage msg, String... alias){
		JPush push = getInstance();
		String returnMsg="";
		try {
//			push.checker.check(msg);
			if(StringUtils.isEmpty(msg.content())){
				msg.content("您有新消息");
			}
//			returnMsg = msg.sendPayload(push.createPayload(msg,Audience.alias(JPushMD5.batchMd5(alias))).toArray(new PushPayload[1]));
			returnMsg = msg.sendPayload(push.createPayload(msg,Audience.alias(alias)).toArray(new PushPayload[1]));
		} catch (Exception e) {
//			e.printStackTrace();
			returnMsg = "极光推送发生内部错误.错误类型:"+e.getClass().getName();
		}
		return returnMsg;
	}
	
	public static String sendByAliasToIPhone(JMessage msg, String... alias){
		JPush push = getInstance();
		String returnMsg="";
		try {
			if(StringUtils.isEmpty(msg.content())){
				msg.content("您有新消息");
			}
			returnMsg = msg.sendPayload(push.createIPhonePayload(msg,Audience.alias(alias)));
		} catch (Exception e) {
			returnMsg = "极光推送发生内部错误.错误类型:"+e.getClass().getName();
		}
		return returnMsg;
	}
	
	public static String sendByAliasToAndroid(JMessage msg, String... alias){
		JPush push = getInstance();
		String returnMsg="";
		try {
			if(StringUtils.isEmpty(msg.content())){
				msg.content("您有新消息");
			}
			returnMsg = msg.sendPayload(push.createAndroidPayload(msg,Audience.alias(alias)));
		} catch (Exception e) {
			returnMsg = "极光推送发生内部错误.错误类型:"+e.getClass().getName();
		}
		return returnMsg;
	}
	
	public static String sendLazyByTags(JMessage msg, String... tags){
//		JPush push = getInstance();
		String returnMsg="";
		
		return returnMsg;
	}
	
	public static void sendLazyByAlias(JMessage msg, String... alias){
		JPush push = getInstance();
		try {
			push.checker.check(msg);
		}catch(JPushGwrong ex){
			return;
		}
		if(push.lazyAlias.size()==0){
			push.aliasTimer = new Timer();
			TimerTask task = new JPushTimer(msg,push.aliasTimer,push.lazyAlias);
			push.aliasTimer.schedule(task, 5000);
		}
		for (String str : alias) {
			push.lazyAlias.add(str);
		}
	}
	
	static class JPushTimer extends TimerTask{
		
		private Timer t = null;
		private Vector<String> a;
		private JMessage m;
		
		public JPushTimer(JMessage msg, Timer timer,Vector<String> alias) {
			t = timer;
			a = alias;
			m = msg;
		}

		@Override
		public void run() {
			System.out.println("send lazy data: 共"+a.size()+"条");
			JPush push = getInstance();
			List<String> data = new ArrayList<String>();
			List<List<PushPayload>> list = new ArrayList<List<PushPayload>>();
			for (int i=1;i<=a.size();i++) {
				data.add(a.get(i-1));
				if(i%1000==0){
					try {
						list.add(push.createPayload(m,Audience.alias(PPPString.md5s(data.toArray(new String[0])))));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
					data.clear();
				}
				
			}
			if(data.size()!=0 && list.size()==0){
				try {
					list.add(push.createPayload(m,Audience.alias(PPPString.md5s(data.toArray(new String[0])))));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
			for (List<PushPayload> payloads : list) {
				m.sendPayload(payloads.toArray(new PushPayload[0]));
			}
			a.clear();
//			push.createPayload(msg,Audience.alias(JPushMD5.batchMd5(alias));
			synchronized (t) {
				t.cancel();
			}
		}
		
	}
	
	public static JMessage buildMessage(String appKey, String masterSecret){
		getInstance();
		return me.factory.build(appKey, masterSecret);
	}
	
	private static JPush getInstance(){
		if(me==null){
			me = new JPush();
		}
		return me;
	}
	
	/***
	 * 创建Payload
	 * @param from
	 * @param param
	 * @param audience
	 * @return Payload
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	private List<PushPayload> createPayload(JMessage param, Audience audience) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		List<PushPayload> payloads = new ArrayList<PushPayload>();
		
		//iosPayload
		payloads.add(createIPhonePayload(param, audience));
		
		//androidPayload
		payloads.add(createAndroidPayload(param, audience));
		
//		cn.jpush.api.push.model.PushPayload.Builder iosBuilder = PushPayload.newBuilder();
//		iosBuilder.setPlatform(Platform.android_ios())
//			.setAudience(audience)
//			.setNotification(
//					Notification.newBuilder()
//					.addPlatformNotification(iosNotificationBuild.build())
//					.build())
//			.setMessage(cn.jpush.api.push.model.Message.content(param.content()))
//			.setOptions(Options.newBuilder().setApnsProduction(true).build());
//		PushPayload iosPayload = iosBuilder.build();
//		payloads.add(iosPayload);
		
		return payloads;
	}
	
	private PushPayload createIPhonePayload(JMessage param, Audience audience) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		cn.jpush.api.push.model.PushPayload.Builder iosBuilder = PushPayload.newBuilder();
		cn.jpush.api.push.model.notification.IosNotification.Builder iosNotificationBuild = IosNotification.newBuilder().setAlert(param.content());
		Class<?> clazz = param.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			String fieldName = field.getName();
			Method m = null;
			try{
				m = clazz.getMethod(fieldName);
				if(m==null){
					continue;
				}
			}catch(NoSuchMethodException e){
				continue;
			}
			Object value = m.invoke(param);
			if(value==null){
				continue;
			}
			iosNotificationBuild.addExtra(fieldName, value.toString());
		}
		//sound
		switch(param.sound().intValue()){
			case 1 : iosNotificationBuild.setSound("sound.caf");break;
		}
		
		//badge
		iosNotificationBuild.setBadge(param.badge());
		
		iosBuilder.setPlatform(Platform.ios())
			.setAudience(audience)
			.setNotification(
					Notification.newBuilder()
					.addPlatformNotification(iosNotificationBuild.build())
					.build())
			.setMessage(cn.jpush.api.push.model.Message.content(param.content()))
			.setOptions(Options.newBuilder().setApnsProduction(true).build());
		return iosBuilder.build();
	}
	
	private PushPayload createAndroidPayload(JMessage param, Audience audience) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		cn.jpush.api.push.model.PushPayload.Builder androidBuilder = PushPayload.newBuilder();
		cn.jpush.api.push.model.Message.Builder androidNotificationBuild = Message.newBuilder().setMsgContent(param.content());
		
		Class<?> clazz = param.getClass();
		Field[] fields = clazz.getDeclaredFields();
		//extra
		for (Field field : fields) {
			String fieldName = field.getName();
			Method m = null;
			try{
				m = clazz.getMethod(fieldName);
				if(m==null){
					continue;
				}
			}catch(NoSuchMethodException e){
				continue;
			}
			Object value = m.invoke(param);
			if(value==null){
				continue;
			}
			androidNotificationBuild.addExtra(fieldName, value.toString());
		}
		
		androidBuilder.setPlatform(Platform.android())
			.setAudience(audience)			
			.setMessage(cn.jpush.api.push.model.Message.content(param.content()));
//			.setMessage(androidNotificationBuild.build());
		return androidBuilder.build();
	}
	
	
	public static String alertAll(JMessage alert) {
		return alert.sendPayload(PushPayload.alertAll(alert.content()));
	}
	
	
	public static void main(String[] args) {
//		System.out.println(JPushMD5.getMD5("4a700c38edbbfecb1c5eb7cf14c1bb0130d7fe3"));
//		JPush.alertAll(JPush.buildMessage("a32ee4da7de5842f38107fed", "9b99ebb6efbd2273711440d0"));
		String key="ba587ae13094f3be876d8474";
		String secret="d34bc3e5414fc2230e1c4ca1";
		List<String> list1 = new ArrayList<String>();
//		for (int i=0;i<5000;i++) {
//			list1.add(i+"");
//		}
		list1.add("4a700c38edbbfecb1c5eb7cf14c1bb0130d7fe3");
		for (int i=0;i<list1.size();i++) {
			try{
				System.out.println(i);
				JMessage message = JPush.buildMessage(key,secret);
				message.sender(i+"")
				.type(3)
				.sound(1).shake(1).badge(1);
				JPush.sendByAlias(message, list1.toArray(new String[0]));
			}catch(Exception ex){
				continue;
			}
		}
		
//		JMessage message = JPush.buildMessage(key,secret);
//		message.sender("Other")
//		.type(1)
//		.num(1)
//		.title("极光极限测试2")
//		.content("测试成功2.")
//		.sound(1).shake(1).badge(1);
//		JPush.sendByAlias(message, list1.toArray(new String[0]));
		
	}
}
