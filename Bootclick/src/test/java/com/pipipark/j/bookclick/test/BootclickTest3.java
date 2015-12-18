package com.pipipark.j.bookclick.test;

import com.pipipark.j.bootclick.comparator.IndexBootclick;
import com.pipipark.j.bootclick.handler.startup.BootclickStartUpHandler;
import com.pipipark.j.system.core.PPPConstant;

@SuppressWarnings("serial")
@IndexBootclick(PPPConstant.Indexs.MINIMUM_INDEX)
public class BootclickTest3 implements BootclickStartUpHandler {

	public static void main(String[] args) {
		
	}

	@Override
	public void startUp() {
		System.out.println(3);
	}

}
