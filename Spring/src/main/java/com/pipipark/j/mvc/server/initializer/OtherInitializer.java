package com.pipipark.j.mvc.server.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import com.pipipark.j.mvc.PPPMvcInitializer;
import com.pipipark.j.system.core.PPPLogger;


/***
 * 其他功能初始化,
 * 启动顺位: No.10 .
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
@Order(10)
public class OtherInitializer implements WebApplicationInitializer,PPPMvcInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		PPPLogger.systemInfo("Other initializer");
		//加载其他web服务器启动选项
		
	}
}
