package com.pipipark.j.mvc.server.handler.servicehandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pipipark.j.mvc.core.PPPResonpse;
import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.core.PPPVersion;
import com.pipipark.j.system.entity.PPPEntity;

@SuppressWarnings("serial")
public abstract class AbsSpringMvcServiceHandler extends PPPEntity implements IPPPark {

	private Map<String, Object> services;
	
	public abstract PPPResonpse access(final String serviceName, final PPPVersion ver, HttpServletRequest request, HttpServletResponse response)  throws Exception ;

	public void setService(Map<String, Object> services) {
		this.services = services;
	}
	public Object getService(String name) {
		return services.get(name);
	}
	public List<Object> list() {
		return Arrays.asList(services.values().toArray(new Object[0]));
	}
}
