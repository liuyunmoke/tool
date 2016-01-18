package com.pipipark.j.jpush.client;

import java.util.HashMap;
import java.util.Map;

import com.pipipark.j.jpush.message.JMessage;

public class JPushClientFactory {
	
	private Map<String, JPushClient> jpushClients = new HashMap<String, JPushClient>();

	public JMessage build(String appKey, String secret){
		JMessage msg = null;
		String key = appKey+"-"+secret;
		if(jpushClients.containsKey(key)){
			msg = new JMessage(jpushClients.get(key));
		}else{
			JPushClient jc = new JPushClient(secret,appKey);
			jpushClients.put(key, jc);
			msg = new JMessage(jc);
		}
		return msg;
	}
}
