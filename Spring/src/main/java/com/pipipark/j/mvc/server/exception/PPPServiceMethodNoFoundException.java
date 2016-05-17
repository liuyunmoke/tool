package com.pipipark.j.mvc.server.exception;

import com.pipipark.j.system.exception.PPPSystemError;

/***
 * execute方法检查,
 * 每个服务类只能存在一个execute方法,
 * 不能重载,可继承或重写父类的execute.
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class PPPServiceMethodNoFoundException extends PPPSystemError {

	public PPPServiceMethodNoFoundException() {
		super("Service method\"execute\" no found.");
	}
	
}
