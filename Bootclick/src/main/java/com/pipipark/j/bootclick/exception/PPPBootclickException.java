package com.pipipark.j.bootclick.exception;

import com.pipipark.j.system.exception.PPPServiceException;

/***
 * 业务异常.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPBootclickException extends PPPServiceException {
	
	public PPPBootclickException(String msg) {
		super(msg);
	}

	public PPPBootclickException(String msg, Exception e) {
		super(msg, e);
	}

}
