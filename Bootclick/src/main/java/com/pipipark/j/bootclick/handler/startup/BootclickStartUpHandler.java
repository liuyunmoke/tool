package com.pipipark.j.bootclick.handler.startup;

import com.pipipark.j.bootclick.handler.PPPBootclickHandler;


/***
 * 启动完成接口.
 * @author pipipark:cwj
 */
public interface  BootclickStartUpHandler extends PPPBootclickHandler {

	/**
	 * 启动后调用
	 */
	public void startUp();
}
