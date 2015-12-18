package com.pipipark.j.random.type;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.RandomUtils;
import com.pipipark.j.random.RandomType;
import com.pipipark.j.random.type.tool.LetterEnum;

public class Letter implements RandomType<Character> {
	
	private Character[] upper = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private Character[] lower = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

	
	private LetterEnum type = LetterEnum.all;
	
	public Letter(){}
	public Letter(LetterEnum letter){
		type = letter;
	}
	
	@Override
	public Character get() {
		Character[] array = null;
		switch(type){
			case upper : array = upper;break;
			case lower : array = lower;break;
			default : array = (Character[])ArrayUtils.addAll(upper, lower);
		}
		return array[RandomUtils.nextInt(array.length)];
	}
	
	public static void main(String[] args){
		Letter c = new Letter();
		System.out.println(c.get());
	}

}
