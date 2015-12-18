package com.pipipark.j.bookclick.test;

import com.pipipark.j.bootclick.handler.startup.BootclickStartUpHandler;

@SuppressWarnings("serial")
public class BootclickTest4 implements BootclickStartUpHandler {

	public static void main(String[] args) {
		
	}

	@Override
	public void startUp() {
		System.out.println(4);
	}
}
