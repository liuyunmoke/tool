package com.pipipark.j.mvc.server.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import com.pipipark.j.mvc.PPPMvcInitializer;
import com.pipipark.j.system.classscan.v2.PPPScan;
import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.core.PPPString;
import com.pipipark.j.web.core.PPPContext;

/***
 * 扫描加载初始化,
 * 启动顺位: No.1 .
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
@Order(1)
public class StartedInitializer implements WebApplicationInitializer,PPPMvcInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		PPPLogger.systemInfo("Server initializer start");
		//启动
		String packages = servletContext.getInitParameter("scan");
		PPPString.split(packages, ",");
		PPPContext.servletContext(servletContext);
		PPPScan.scaner(BASE_PACKAGE, packages);
	}

}
