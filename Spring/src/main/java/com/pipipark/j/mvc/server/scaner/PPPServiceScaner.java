package com.pipipark.j.mvc.server.scaner;

import com.pipipark.j.mvc.PPPMvcService;
import com.pipipark.j.mvc.core.PPPContext;
import com.pipipark.j.system.classscan.v2.PPPClassesScaner;
import com.pipipark.j.system.classscan.v2.PPPScanEntity;

/***
 * 扫描器(服务接口).
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPServiceScaner extends PPPClassesScaner<PPPMvcService> {
	
	@Override
	protected Object target(PPPScanEntity entity){
		return PPPContext.getBean(entity.getName());
	}
}
