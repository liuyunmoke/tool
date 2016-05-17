package com.pipipark.j.mvc.server.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pipipark.j.mvc.PPPMvcController;
import com.pipipark.j.mvc.core.PPPResonpse;
import com.pipipark.j.mvc.server.handler.exception.PPPServiceHandlerError;
import com.pipipark.j.mvc.server.handler.servicehandler.DefaultSpringMvcServiceHandler;
import com.pipipark.j.mvc.server.handler.servicehandler.AbsSpringMvcServiceHandler;
import com.pipipark.j.mvc.server.handler.servicehandler.SpringMvcServiceHandler;
import com.pipipark.j.mvc.server.scaner.PPPServiceHandlerScaner;
import com.pipipark.j.mvc.server.scaner.PPPServiceScaner;
import com.pipipark.j.system.classscan.v2.PPPScanEntity;
import com.pipipark.j.system.classscan.v2.PPPScanerManager;
import com.pipipark.j.system.core.PPPString;
import com.pipipark.j.system.core.PPPVersion;
import com.pipipark.j.system.core.exception.PPPVersionException;
import com.pipipark.j.system.entity.PPPEntity;

/***
 * 版本控制器, 接受所有业务http请求, 分析请求头和特殊参数.
 * 
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
@RestController("SpringMvcControllerHandler")
public final class SpringMvcControllerHandler extends PPPEntity implements
		PPPMvcController {

	AbsSpringMvcServiceHandler serviceHandler;

	@RequestMapping("/service/{name}")
	public PPPResonpse service(@PathVariable("name") String serviceName,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setContentType("application/json;charset=utf-8");
		res.setHeader("Cache-Control", "no-cache");

		// 处理请求名
		final String[] serviceNameTemp = PPPString.split(serviceName, ".");
		// 服务名称
		final String service;
		// 返回对象
		final PPPResonpse response = new PPPResonpse();

		// 断言serviceNameTemp是否空
		if (serviceNameTemp == null) {
			response.message("this is wrong access.");
			return response;
		}

		// 断言serviceNameTemp的访问地址是否空.
		try {
			service = serviceNameTemp[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			response.message("this is wrong access.");
			return response;
		}
		if (PPPString.isEmpty(service)) {
			response.message("this is wrong access.");
			return response;
		}

		// 断言serviceNameTemp的版本标识是否空,赋值默认值"Rat"
		PPPVersion version;
		if (serviceNameTemp.length < 2) {
			version = PPPVersion.Rat;
		} else {
			try {
				version = PPPVersion.get(serviceNameTemp[1]);
			} catch (PPPVersionException e) {
				version = PPPVersion.Rat;
			} catch (IllegalArgumentException e) {
				version = PPPVersion.Rat;
			}
		}

		// 初始化service
		initService();
		
		if (serviceHandler == null) {
			throw new PPPServiceHandlerError("serviceHandler not init.");
		}

		return serviceHandler.access(serviceName, version, req, res);
	}

//	@RequestMapping("/{entity}/{method}")
	public String entity(@PathVariable("name") String serviceName,
			HttpServletRequest request, HttpServletResponse response) {
		String path = request.getServletPath();
		String[] pathParam = PPPString.split(path, "/");

		for (String param : pathParam) {
			System.out.println(param);
		}
		// return springMvcServiceHandler.access(serviceName, ver==null?1:ver,
		// request, response);
		return "";
	}

	private void initService() {
		if(serviceHandler==null){
			// 扫描ServiceHandler,如果没有找到自定义的ServiceHandler,则使用默认的DefaultServiceHandler.
			PPPServiceHandlerScaner serviceHandlerScaner = (PPPServiceHandlerScaner) PPPScanerManager
					.scaner(PPPString.lowFirst(PPPString
							.className(PPPServiceHandlerScaner.class)));
			Set<PPPScanEntity> serviceHandlerSet = serviceHandlerScaner.getData();
			HanderCounter newer = null;
			for (PPPScanEntity entity : serviceHandlerSet) {
				SpringMvcServiceHandler handler = (SpringMvcServiceHandler) entity
						.getTarget();
				HanderCounter counter = superHandler(new HanderCounter(handler));
				if (newer == null || newer.getCount() < counter.getCount()) {
					newer = counter;
				}
			}
			if (newer == null) {
				serviceHandler = new DefaultSpringMvcServiceHandler();
			} else {
				serviceHandler = newer.getHandler();
			}
			// 初始化service
			PPPServiceScaner serviceScaner = (PPPServiceScaner) PPPScanerManager
					.scaner(PPPString.lowFirst(PPPString
							.className(PPPServiceScaner.class)));
			Set<PPPScanEntity> serviceSet = serviceScaner.getData();
			Map<String, Object> serviceObj = new HashMap<String, Object>();
			for (PPPScanEntity entity : serviceSet) {
				serviceObj.put(entity.getName(), entity.getTarget());
			}
			serviceHandler.setService(serviceObj);
		}
	}

	private final class HanderCounter {
		private SpringMvcServiceHandler handler;
		private int count = 0;

		HanderCounter(final SpringMvcServiceHandler handler) {
			this.handler = handler;
		}

		public int getCount() {
			return count;
		}

		public void addCount() {
			this.count++;
		}

		public SpringMvcServiceHandler getHandler() {
			return handler;
		}
	}

	private HanderCounter superHandler(HanderCounter counter) {
		Class<?> superclass = counter.getHandler().getClass().getSuperclass();
		if (superclass == SpringMvcServiceHandler.class) {
			return counter;
		} else {
			counter.addCount();
			return superHandler(counter);
		}
	}

	@Override
	public void desc(StringBuilder string) throws Exception {
		string.append("this is controller.");
	}
}
