package com.pipipark.j.random.type;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.math.RandomUtils;
import com.pipipark.j.random.RandomType;

public class Chinese implements RandomType<String> {

	@Override
	public String get() {
		String str = null;
		int highPos, lowPos;
		highPos = (176 + Math.abs(RandomUtils.nextInt(39)));
		lowPos = 161 + Math.abs(RandomUtils.nextInt(93));
		byte[] b = new byte[2];
		b[0] = (new Integer(highPos)).byteValue();
		b[1] = (new Integer(lowPos)).byteValue();
		try {
			str = new String(b, "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static void main(String[] args){
		Chinese c = new Chinese();
		System.out.println(c.get());
	}

}
