package com.pipipark.j.random.test;

import com.pipipark.j.random.RandomCode;
import com.pipipark.j.random.Random;

public class RandomTest {

	
	public static void main(String[] args){
		Random rr = new RandomCode("15606979674").get();
//		new RandomCode("15606979674").validate("");
		System.out.println(rr.toString());
	}
}
