package com.pipipark.j.mvc.server.processor;

import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.pipipark.j.mvc.PPPMvcPostProcessor;
import com.pipipark.j.mvc.PPPServerPostProcessor;
import com.pipipark.j.mvc.server.scaner.PPPPostProcessorScaner;
import com.pipipark.j.mvc.server.scaner.PPPServerPostProcessorScaner;
import com.pipipark.j.system.classscan.v2.PPPScanEntity;
import com.pipipark.j.system.classscan.v2.PPPScanerManager;
import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.core.PPPString;

/***
 * Bean加载(已加载入内存)处理器.
 * @author Administrator
 *
 */
public class BeanAfterInitProcessor implements InitializingBean,ServletContextAware {

	@Override
	public void afterPropertiesSet() throws Exception {
		if(BeanBeforeInitProcessor.state()){
			PPPServerPostProcessorScaner serverScaner = (PPPServerPostProcessorScaner)PPPScanerManager.scaner(PPPString.lowFirst(PPPString.className(PPPServerPostProcessorScaner.class)));
			Set<PPPScanEntity> serverSet = serverScaner.getData();
			for (PPPScanEntity serverPost : serverSet) {
				PPPLogger.info("post class: "+serverPost.getType().getName());
				PPPServerPostProcessor pp = (PPPServerPostProcessor)serverPost.getTarget();
				pp.handler();
			}
			
			//service post
			PPPPostProcessorScaner scaner = (PPPPostProcessorScaner)PPPScanerManager.scaner(PPPString.lowFirst(PPPString.className(PPPPostProcessorScaner.class)));
			Set<PPPScanEntity> set = scaner.getData();
			for (PPPScanEntity post : set) {
				PPPLogger.info("post class: "+post.getType().getName());
				PPPMvcPostProcessor pp = (PPPMvcPostProcessor)post.getTarget();
				pp.handler();
			}
			PPPLogger.systemInfo("Post processor end");
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		
	}

}
