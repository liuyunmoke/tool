package com.pipipark.j.bookclick.test;

import com.pipipark.j.bootclick.comparator.IndexBootclick;
import com.pipipark.j.bootclick.handler.startup.BootclickStartUpHandler;
import com.pipipark.j.system.core.PPPConstant;

@SuppressWarnings("serial")
@IndexBootclick(PPPConstant.Indexs.HIGHEST_INDEX)
public class BootclickTest implements BootclickStartUpHandler {

	@Override
	public void startUp() {
		System.out.println(1);
	}

}
