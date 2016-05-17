package com.pipipark.j.mvc.server.processor;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.pipipark.j.system.core.PPPLogger;


/***
 * Bean加载(尚未载入内存)处理器.
 * @author pipipark:cwj
 */
public class BeanBeforeInitProcessor implements ApplicationListener<ContextRefreshedEvent> {
	
	private static Boolean _state = false;
	
	public static Boolean state(){
		return _state;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//root application context.
		if(event.getApplicationContext().getParent() == null){
			PPPLogger.systemInfo("SpringBean initializer end and Post processor start");
			_state = true;
		}
	}

}
