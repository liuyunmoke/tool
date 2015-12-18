package com.pipipark.j.bootclick.exception;

import com.pipipark.j.system.exception.PPPSystemError;

/***
 * 严重错误.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPBootclickError extends PPPSystemError {
	
	public PPPBootclickError(String msg) {
		super(msg);
	}

	public PPPBootclickError(String msg, Exception e) {
		super(msg, e);
	}

}
