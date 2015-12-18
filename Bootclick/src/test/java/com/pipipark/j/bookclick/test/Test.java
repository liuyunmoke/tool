package com.pipipark.j.bookclick.test;

import com.pipipark.j.bootclick.PPPApplicationBoot;
import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.exception.PPPServiceException;

public class Test {

	public static void main(String[] args) throws PPPServiceException {
		PPPApplicationBoot.getInstance().boot();
		PPPLogger.info("messagepush server bootup");
	}

}
