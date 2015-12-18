package com.pipipark.j.database.tool;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class PPPRecord extends HashMap<String, Object> {

	
	public PPPRecord(){}
	public PPPRecord(Map<String,Object> map){
		super(map);
	}

	
	public static final <M> PPPRecord newInstance(Class<M> clazz){
		Field[] fields = clazz.getDeclaredFields();
		if(fields==null || fields.length<=0){
			return null;
		}
		PPPRecord r = new PPPRecord();
		for (Field field : fields) {
			if(field.getName().equals("serialVersionUID")){
				continue;
			}
			r.put(field.getName(), null);
		}
		return r;
	}
}
