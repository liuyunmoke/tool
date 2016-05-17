package com.pipipark.j.mvc.server.handler.servicehandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.pipipark.j.mvc.core.PPPResonpse;
import com.pipipark.j.system.core.PPPVersion;

/***
 * 服务功能过滤器,
 * 服务的检查分发.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
@Service("SpringMvcServiceHandler")
public abstract class SpringMvcServiceHandler extends AbsSpringMvcServiceHandler {

	public abstract PPPResonpse access(final String serviceName, final PPPVersion ver, HttpServletRequest request, HttpServletResponse response);

	@Override
	public void desc(StringBuilder string) throws Exception {
		string.append("this is ServiceHandler.");
	}
}
