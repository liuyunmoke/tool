package com.pipipark.j.netty.exception;

/**
 * 通信中断异常类
 * @author cwj
 *
 */
public class ProtocolBreakException extends Exception {


	private static final long serialVersionUID = -8788957647196181550L;

	public ProtocolBreakException(String msg){
		super(msg);
	}
	
}
