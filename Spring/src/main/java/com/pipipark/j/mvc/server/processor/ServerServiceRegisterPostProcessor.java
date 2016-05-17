package com.pipipark.j.mvc.server.processor;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.pipipark.j.mvc.PPPServerPostProcessor;
import com.pipipark.j.mvc.core.PPPContext;
import com.pipipark.j.mvc.server.exception.PPPServiceExecuteMethodRepeatException;
import com.pipipark.j.mvc.server.scaner.PPPServiceScaner;
import com.pipipark.j.system.annotation.PPPIndex;
import com.pipipark.j.system.classscan.v2.PPPScanEntity;
import com.pipipark.j.system.classscan.v2.PPPScanerManager;
import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.core.PPPString;

/***
 * 服务注册处理器,
 * 载入并注册为SpringBean.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
@PPPIndex(PPPConstant.Indexs.HIGHEST_INDEX)
public class ServerServiceRegisterPostProcessor implements PPPServerPostProcessor {
	
	@Override
	public void handler() {
		PPPServiceScaner scaner = (PPPServiceScaner)PPPScanerManager.scaner(PPPString.lowFirst(PPPString.className(PPPServiceScaner.class)));
		List<PPPScanEntity> list = scaner.list();
		for (PPPScanEntity entity : list) {
			Class<?> clazz = entity.getType();
			PPPLogger.info("register service: "+clazz.getName()+" ["+entity.getName()+"]");
			Method[] ms = clazz.getDeclaredMethods();
			Set<String> set = new LinkedHashSet<String>();
			for (Method method : ms) {
				if(PPPContext.EXECUTE_METHOD.equals(method.getName()) && !set.add(method.getName())){
					throw new PPPServiceExecuteMethodRepeatException();
				}
			}
			PPPContext.addBean(entity.getName(), clazz);
		}
		
		
		JdbcTemplate jdbc = (JdbcTemplate)PPPContext.getBean("jdbc");
//		DriverManagerDataSource ds = (DriverManagerDataSource)jdbc.getDataSource();
//		ds.setDriverClassName("com.mysql.jdbc.Driver");
//		ds.setUrl(PPPContext.properties("jdbc.location"));
//		ds.setUsername(PPPContext.properties("jdbc.username"));
//		ds.setPassword(PPPContext.properties("jdbc.password"));
	}
}
