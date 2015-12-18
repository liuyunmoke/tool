package com.pipipark.j.bootclick.handler.initialize;

import com.pipipark.j.bootclick.handler.PPPBootclickHandler;

/***
 * 启动初始化接口.
 * @author pipipark:cwj
 */
public interface  BootclickInitHandler extends PPPBootclickHandler {

	/**
	 * 启动前初始化调用
	 */
	public void init();
}
