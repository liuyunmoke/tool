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
public class PPPServiceExecuteMethodRepeatException extends PPPSystemError {

	public PPPServiceExecuteMethodRepeatException() {
		super("Service has more 'execute' method, must be only one execute method exists.");
	}
	
}
