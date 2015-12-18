package com.pipipark.j.random;

import com.pipipark.j.system.entity.SimplePPPEntity;

/***
 * 随机码结果集.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class Random extends SimplePPPEntity {
	/**
	 * 计算后保存的随机码
	 */
	private String content = null;
	
	public Random(String content){
		this.content = content;
	}
	
	/**
	 * 返回根据随机码内容生成的图片
	 */
	public byte[] toImage(){
		return null;
	}
	
	/**
	 * 返回随机码值
	 */
	@Override
	public String toString(){
		return content.toString();
	}
	
	
	
}
