package com.pipipark.j.mvc.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/***
 * Aop配置.
 * @author pipipark:cwj
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AspectJAutoProxyConfig {
	
}
