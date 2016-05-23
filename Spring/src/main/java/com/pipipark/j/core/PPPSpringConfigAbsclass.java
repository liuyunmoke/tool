package com.pipipark.j.core;


/***
 * 服务器总配置类,
 * 抽象基类,
 * 需要用户实现以下模版方法来获得动态配置.
 * 
 * @author 乱小玖
 * @since 2016/05/24
 */
@SuppressWarnings("serial")
public abstract class PPPSpringConfigAbsclass implements PPPSpringConfigInterface {

	/**
	 * 
	 * @return
	 */
	protected abstract String charEncoding();
	
}
