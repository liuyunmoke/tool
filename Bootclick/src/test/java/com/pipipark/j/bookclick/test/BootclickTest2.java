package com.pipipark.j.bookclick.test;

import com.pipipark.j.bootclick.comparator.IndexBootclick;
import com.pipipark.j.bootclick.handler.startup.BootclickStartUpHandler;

@SuppressWarnings("serial")
@IndexBootclick(20)
public class BootclickTest2 implements BootclickStartUpHandler {

	public static void main(String[] args) {
		
	}

	@Override
	public void startUp() {
		System.out.println(2);
	}

}
