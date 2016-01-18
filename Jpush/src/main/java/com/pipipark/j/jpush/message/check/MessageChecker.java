package com.pipipark.j.jpush.message.check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pipipark.j.jpush.exception.JPushGwrong;
import com.pipipark.j.jpush.message.JMessage;

public class MessageChecker {

	private Map<MessageCheckLevel, List<IMessageCheck>> checks = new HashMap<MessageCheckLevel, List<IMessageCheck>>();
	
	public void addMessageCheck(IMessageCheck check, MessageCheckLevel level){
		List<IMessageCheck> cs = checks.get(level);
		if(cs==null){
			cs = new ArrayList<IMessageCheck>();
			checks.put(level, cs);
		}
		cs.add(check);
	}
	
	public void addMessageCheck(IMessageCheck check){
		addMessageCheck(check, MessageCheckLevel.LEVEL_DEFAULT);
	}
	
	public void check(JMessage msg) throws JPushGwrong{
		MessageCheckLevel[] mcs = MessageCheckLevel.values();
		Arrays.sort(mcs, new Comparator<MessageCheckLevel>() {
			@Override
			public int compare(MessageCheckLevel o1, MessageCheckLevel o2) {
				if(o1.id()<o2.id()){
					return -1;
				}else if(o1.id()>o2.id()){
					return 1;
				}
				return 0;
			}
			
		});
		for (int i=0; i<mcs.length; i++) {
			List<IMessageCheck> list = checks.get(mcs[i]);
			if(list==null){
				continue;
			}
			for (IMessageCheck c : list) {
				c.handler(msg);
			}
		}
	}
}
