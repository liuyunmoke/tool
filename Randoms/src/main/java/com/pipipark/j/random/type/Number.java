package com.pipipark.j.random.type;

import org.apache.commons.lang.math.RandomUtils;

import com.pipipark.j.random.RandomType;

public class Number implements RandomType<Integer> {
	
	private Integer[] nums = {1,2,3,4,5,6,7,8,9,0};

	@Override
	public Integer get() {
		return nums[RandomUtils.nextInt(nums.length)];
	}
	
	public static void main(String[] args){
		Number c = new Number();
		System.out.println(c.get());
	}

}
