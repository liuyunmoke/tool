package com.pipipark.j.mvc.server.initializer;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.pipipark.j.mvc.PPPMvcInitializer;
import com.pipipark.j.mvc.server.configuration.DispatchServletConfig;
import com.pipipark.j.mvc.server.scaner.PPPConfigurationScaner;
import com.pipipark.j.system.classscan.v2.PPPScanerManager;
import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.core.PPPString;


/***
 * 全局配置初始化,
 * 启动顺位: No.2 .
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
@Order(2)
public class ConfigInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements PPPMvcInitializer {

	/**
	 * 配置文件
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		PPPLogger.systemInfo("Config initializer");
		PPPConfigurationScaner scaner = (PPPConfigurationScaner)PPPScanerManager.scaner(PPPString.lowFirst(PPPString.className(PPPConfigurationScaner.class)));
		List<Class<?>> list = scaner.types();
		return list.toArray(new Class[0]);
	}

	/**
	 * 映射参数
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {DispatchServletConfig.class};
	}

	/**
     * DispatcherServlet的映射路径 
     */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}


}
