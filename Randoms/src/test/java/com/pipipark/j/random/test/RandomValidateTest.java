package com.pipipark.j.random.test;

import com.pipipark.j.random.RandomCode;

public class RandomValidateTest {

	
	public static void main(String[] args){
		Boolean bool = new RandomCode("15606979674").validate("7245");
		System.out.println("validate result: "+bool);
	}
}
