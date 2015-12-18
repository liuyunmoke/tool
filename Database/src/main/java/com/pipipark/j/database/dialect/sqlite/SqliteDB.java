package com.pipipark.j.database.dialect.sqlite;

import java.sql.Blob;
import java.sql.Clob;

import org.apache.commons.dbcp.BasicDataSource;

import com.pipipark.j.database.PPPDatabase;
import com.pipipark.j.database.exception.PPPSqlException;
import com.pipipark.j.system.core.PPPConstant;

@SuppressWarnings("serial")
public abstract class SqliteDB extends PPPDatabase {

	
	@Override
	public void dataSource(BasicDataSource ds) {
		ds.setDriverClassName("org.sqlite.JDBC");
		StringBuilder builder = new StringBuilder();
		builder.append("jdbc:sqlite:");
		String usrHome = PPPConstant.Systems.USER_HOME;
		builder.append(usrHome);
		builder.append("/sqlite-");
		builder.append(this.name());
		builder.append(".db");
		ds.setUrl(builder.toString());
//		ds.setDefaultAutoCommit(false);
		ds.setInitialSize(100);
		ds.setMaxActive(0);
		ds.setMaxIdle(10);
		ds.setValidationQuery("select 1");
		ds.setMaxWait(10000);
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
		SqliteDB db=new SqliteDB() {
			
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
		};
		try {
			db.<SqliteDB>open().executeUpdate("insert into person ('name','password') values ('jean','123')");
			db.close();
		} catch (PPPSqlException e) {
			e.printStackTrace();
		}
		
	}
	
}
