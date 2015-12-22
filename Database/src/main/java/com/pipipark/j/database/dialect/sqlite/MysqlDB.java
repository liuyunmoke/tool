package com.pipipark.j.database.dialect.sqlite;

import java.sql.Blob;
import java.sql.Clob;

import org.apache.commons.dbcp.BasicDataSource;

import com.pipipark.j.database.PPPDatabase;
import com.pipipark.j.database.exception.PPPSqlException;
import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.core.PPPString;

@SuppressWarnings("serial")
public abstract class MysqlDB extends PPPDatabase {
	
	public final static String DEFAULT_MYSQL_IP="127.0.0.1";
	public final static Integer DEFAULT_MYSQL_PORT=3306;
	
	@Override
	public void dataSource(BasicDataSource ds) {
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		StringBuilder builder = new StringBuilder();
		String ip = ip();
		Integer port = port();
		builder.append("jdbc:mysql://");
		builder.append(PPPString.isRealEmpty(ip)?DEFAULT_MYSQL_IP:ip);
		builder.append(":");
		builder.append((port==null||port==0)?DEFAULT_MYSQL_IP:port);
		builder.append("/");
		builder.append(this.name());
		builder.append(".db");
		ds.setUrl(builder.toString());
	}
	
	protected String ip(){
		return DEFAULT_MYSQL_IP;
	}
	
	protected Integer port(){
		return DEFAULT_MYSQL_PORT;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String dialect(Class type){
		if(type == Integer.class || type == Long.class){
			return "integer";
		}else if(type == Double.class || type == Float.class){
			return "real";
		}else if(type == String.class || type == Clob.class){
			return "text";
		}else if(type == Blob.class){
			return "blob";
		}else{
			return "numeric";
		}
	}
	
	public static void main(String[] args){
		MysqlDB db=new MysqlDB() {
			
			@Override
			public String dbUser() {
				return null;
			}
			
			@Override
			public String dbPassword() {
				return null;
			}
			
			@Override
			public String dbName() {
				return "pipipark";
			}

			@Override
			public String ip() {
				return null;
			}

			@Override
			public Integer port() {
				return null;
			}
		};
		try {
			db.<MysqlDB>open().executeUpdate("insert into person ('name','password') values ('jean','123')");
			db.close();
		} catch (PPPSqlException e) {
			e.printStackTrace();
		}
		
	}
	
}
