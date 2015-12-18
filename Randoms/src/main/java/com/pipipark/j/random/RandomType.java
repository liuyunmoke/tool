package com.pipipark.j.random;

/***
 * 随机码样式类型,
 * 用来组装获取随机码的内容规则,需要子类具体实现.
 * @author pipipark:cwj
 */
public interface RandomType<T> {
	
	T get();
}
