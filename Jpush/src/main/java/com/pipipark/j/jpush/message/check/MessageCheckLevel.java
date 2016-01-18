package com.pipipark.j.jpush.message.check;

public enum MessageCheckLevel {

	LEVEL_HIGHTEST(1),
	LEVEL_DEFAULT(2),
	LEVEL_LOWEST(3);
	
	int _id;
	MessageCheckLevel(int id){
		_id = id;
	}
	public int id(){
		return _id;
	}
}
