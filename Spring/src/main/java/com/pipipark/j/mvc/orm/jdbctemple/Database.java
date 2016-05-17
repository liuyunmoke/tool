package com.pipipark.j.mvc.orm.jdbctemple;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pipipark.j.mvc.core.PPPContext;
import com.pipipark.j.system.core.PPPDate;
import com.pipipark.j.system.core.PPPString;
import com.pipipark.j.system.entity.PPPEntity;

@SuppressWarnings("serial")
@Repository("PPParkDatabase")
public class Database extends PPPEntity {
	
	@Resource
	private JdbcTemplate jdbc;
	


	public static JdbcTemplate jdbc() {
		Database db = (Database)PPPContext.getBean("PPParkDatabase");
		return db.jdbc;
	}
	
	
	 /** 
     * 获取日期 
     *  
     * @param timestamp 
     * @return Date 
     */  
    public Date getDate(Timestamp timestamp) {  
        return toDate(timestamp, null);  
    }  
  
    /** 
     * 获取日期 
     *  
     * @param timestamp 
     * @param format 
     * @return Date 
     */  
    public Date getDate(Timestamp timestamp, String format) {  
        return toDate(timestamp, format);  
    }  
  
    /** 
     * Timestamp按格式转换成Date 
     *  
     * @param timestamp 
     * @param format 
     * @return Date 
     */  
    public Date toDate(Timestamp timestamp, String format) {  
    	PPPDate date = null;  
        if (!PPPString.isEmpty(format)){
        	date = PPPDate.set(timestamp, format);
        }else{
        	date = PPPDate.set(timestamp);
        }
        return date.time();
    }
	
	@Override
	public void desc(StringBuilder string) throws Exception {
		string.append("this is database tool.");
	}
}
