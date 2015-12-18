package com.pipipark.j.bootclick.handler.shutdown;

import com.pipipark.j.bootclick.handler.PPPBootclickHandler;


/***
 * 程序扫尾接口.
 * @author pipipark:cwj
 */
public interface  BootclickShutdownHandler extends PPPBootclickHandler {

	/**
	 * 关闭程序前调用.
	 */
	public void shutdown();
}
