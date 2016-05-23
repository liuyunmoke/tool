package com.pipipark.j.bootclick.comparator;

import com.pipipark.j.system.core.PPPComparator;
import com.pipipark.j.system.core.PPPConstant;

/***
 * 启动类比较器,
 * 实现启动类根据索引排序.
 * @see IndexBootstrap.class
 * @author pipipark:cwj
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class BootclickComparator implements PPPComparator<Class> {

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Class a, Class b) {
		IndexBootclick ai = (IndexBootclick)a.getAnnotation(IndexBootclick.class);
		IndexBootclick bi = (IndexBootclick)b.getAnnotation(IndexBootclick.class);
		int aInt = PPPConstant.Index.Default.value();
		int bInt = PPPConstant.Index.Default.value();
		if(ai!=null){
			aInt = ai.value().value();
		}
		if(bi!=null){
			bInt = bi.value().value();
		}
		if(aInt>bInt){
			return 1;
		}else if(aInt<bInt){
			return -1;
		}
		return 0;
	}

}
