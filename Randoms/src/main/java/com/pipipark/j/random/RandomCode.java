package com.pipipark.j.random;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.pipipark.j.database.exception.PPPSqlException;
import com.pipipark.j.random.data.RandomCodeDB;
import com.pipipark.j.random.data.model.RCode;
import com.pipipark.j.random.type.Chinese;
import com.pipipark.j.random.type.Letter;
import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.core.PPPDate;
import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.entity.SimplePPPEntity;


/***
 * 随机码生成器.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class RandomCode extends SimplePPPEntity {
	
	private List<RandomType<?>> types = new ArrayList<RandomType<?>>();
	private StringBuilder contentBuilder = new StringBuilder();
	private String key = null;
	
	public RandomCode(){}
	
	public RandomCode(String key){
		this.key = key;
	}
	
	/**
	 * 数字随机码.
	 */
	public RandomCode numberStyle(){
		addStyle(new com.pipipark.j.random.type.Number());
		return this;
	}
	public RandomCode removeNumberStyle(){
		removeStyle(com.pipipark.j.random.type.Number.class);
		return this;
	}
	
	/**
	 * 英文大小写字母随机码.
	 */
	public RandomCode letterStyle(){
		addStyle(new Letter());
		return this;
	}
	public RandomCode removeLetterStyle(){
		removeStyle(Letter.class);
		return this;
	}
	
	/**
	 * 中文字符随机码.
	 */
	public RandomCode chineseStyle(){
		addStyle(new Chinese());
		return this;
	}
	public RandomCode removeChineseStyle(){
		removeStyle(Chinese.class);
		return this;
	}
	
	/**
	 * 添加自定义类型随机码.
	 * @param M extends RandomType
	 */
	public <M extends RandomType<?>> RandomCode addStyle(M type){
		if(!types.contains(type)){
			types.add(type);
		}
		return this;
	}
	
	/**
	 * 删除自定义类型随机码.
	 * @param Class<M extends RandomType>
	 */
	public <M extends RandomType<?>> RandomCode removeStyle(Class<M> type){
		Integer index = null;
		for (int i=0;i<types.size();i++) {
			RandomType<?> randomType = types.get(i);
			if(randomType.getClass() == type){
				index = i;
				break;
			}
		}
		if(index!=null){
			types.remove(index.intValue());
		}
		return this;
	}
	
	/**
	 * 删除全部类型样式.
	 */
	public void removeAll(){
		types.clear();
	}
	
	/**
	 * 默认方式获取结果,
	 * 默认4位字符.
	 */
	public Random get(){
		return get(4);
	}
	
	/**
	 * 获取随机码结果.
	 * @param count 几位随机码
	 */
	public Random get(Integer count){
		if(types.isEmpty()){
			numberStyle();
		}
		contentBuilder.delete(0, contentBuilder.length());
		while(count>0){
			int index = RandomUtils.nextInt(types.size());
			RandomType<?> type = types.get(index);
			contentBuilder.append(type.get().toString());
			count--;
		}
		if(this.key!=null){
			RandomCodeDB db = new RandomCodeDB();
			try {
				db.open();
				List<RCode> code = db.executeQuery("select * from rcode where key='"+key+"'", RCode.class);
				String date = PPPDate.now().format(PPPDate.Dateformat.yyyyMMddHHmmssSSS);
				if(code==null || code.isEmpty()){
					db.executeUpdate("insert into rcode ('key','code','updateDate') values ('"+key+"', '"+contentBuilder.toString()+"', '"+date+"')");
				}else{
					db.executeUpdate("update rcode set code = '"+contentBuilder.toString()+"', updateDate='"+date+"' where key = '"+key+"'");
				}
			} catch (PPPSqlException e) {
				PPPLogger.error("Sql execute query happen Exception!", e);
			} finally{
				try {
					db.close();
				} catch (PPPSqlException e) {
					PPPLogger.error("Db close query happen Exception!", e);
				}
			}
		}
		return new Random(contentBuilder.toString());
	}
	
	
	public Boolean validate(String code){
		if(this.key==null){
			return false;
		}
		RandomCodeDB db = new RandomCodeDB();
		try {
			db.open();
			List<RCode> result = db.executeQuery("select * from rcode where key='" + key + "' and code = '" + code.trim() + "'", RCode.class);
			if(result!=null && !result.isEmpty()){
				return true;
			}
		} catch (PPPSqlException e) {
			PPPLogger.error("Sql execute query happen Exception!", e);
		} finally{
			try {
				db.close();
			} catch (PPPSqlException e) {
				PPPLogger.error("Db close query happen Exception!", e);
			}
		}
		return false;
	}
}
