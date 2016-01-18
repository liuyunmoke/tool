package com.pipipark.j.netty.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> jsonToMap(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		Set keys = json.keySet();
	    for(Object key : keys){
	        Object o = json.get(key);
	        if(o instanceof JSONArray)
	            map.put((String) key, jsonToList((JSONArray) o));
	        else if(o instanceof JSONObject)
	            map.put((String) key, jsonToMap((JSONObject) o));
	        else
	            map.put((String) key, o);
	    }
	    return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object jsonToList(JSONArray json){
		List list = new ArrayList();
	    for(Object o : json){
	        if(o instanceof JSONArray)
	            list.add(jsonToList((JSONArray) o));
	        else if(o instanceof JSONObject)
	            list.add(jsonToMap((JSONObject) o));
	        else
	            list.add(o);
	    }
	    return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBean(Class<T> clazz, JSONObject json){
		T tObj = (T)JSONObject.toBean(JSONObject.fromObject(json), clazz);
		System.out.println(tObj);
		return tObj;
	}
	
	public static <T> JSONObject beanToJson(T bean){
		return JSONObject.fromObject(bean);
	}
	
	public static JSONObject mapToJson(Map<String, Object> map){
		return JSONObject.fromObject(map);
	}
	
	public static JSONArray listToJson(List<Object> list){
		return JSONArray.fromObject(list);
	}
}
