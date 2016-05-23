package com.pipipark.j.mvc.server.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.pipipark.j.mvc.PPPMvcListener;
import com.pipipark.j.system.classscan.v2.PPPInitMethod;

public class DefaultServletContextListener implements ServletContextListener,PPPMvcListener,PPPInitMethod<DefaultServletContextListener> {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener end");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("ServletContextListener Start");
	}

	@Override
	public void init_method(DefaultServletContextListener t) {
		System.out.println("ServletContextListener");
	}

}
