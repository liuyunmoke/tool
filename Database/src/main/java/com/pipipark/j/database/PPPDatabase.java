package com.pipipark.j.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang.StringUtils;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.pipipark.j.database.annotation.PrimaryKey;
import com.pipipark.j.database.dialect.mysql.MysqlDB;
import com.pipipark.j.database.dialect.sqlite.SqliteDB;
import com.pipipark.j.database.exception.PPPSqlException;
import com.pipipark.j.database.tool.PPPDb;
import com.pipipark.j.database.tool.PPPRecord;
import com.pipipark.j.database.tool.PPPRecordListHandler;
import com.pipipark.j.system.entity.SimplePPPEntity;
import com.pipipark.j.system.entity.field.PPPField;
import com.pipipark.j.system.exception.PPPServiceException;
import com.pipipark.j.system.exception.PPPTipsException;

@SuppressWarnings("serial")
public abstract class PPPDatabase extends SimplePPPEntity {

	private static final String INSERT_INTO_TEXT = "insert into ";
	private static final String INSERT_TEXT = "insert ";
	private static final String UPDATE_TEXT = "update ";
	private static final String DELETE_TEXT = "delete from ";
	private static final String SELECT_TEXT = "select ";
	private static final String SELECT_FROM_TEXT = "from ";

	private String name = null;
	private String user = null;
	private String pass = null;
	
	public abstract String dbName();
	public abstract String dbUser();
	public abstract String dbPassword();
	public abstract void dataSource(BasicDataSource dataSource);
	@SuppressWarnings("rawtypes")
	public abstract String dialect(Class type);

	public String name() {
		if(StringUtils.isEmpty(name)){
			String name = dbName();
			if (StringUtils.isEmpty(name)) {
				name = PPPDb.DEFULT_TABLE_NAME;
			}
			this.name = name;
		}
		return name;
	}
	public String user() {
		if(StringUtils.isEmpty(user)){
			String user = dbUser();
			if (StringUtils.isEmpty(user)) {
				user = PPPDb.DEFULT_USER;
			}
			this.user = user;
		}
		return user;
	}
	public String password() {
		if(StringUtils.isEmpty(pass)){
			String pass = dbPassword();
			if (StringUtils.isEmpty(pass)) {
				pass = PPPDb.DEFULT_PASSWORD;
			}
			this.pass = pass;
		}
		return pass;
	}

	public <M extends PPPDatabase> M open() throws PPPSqlException {
		return this.<M>open(false);
	}

	public <M extends PPPDatabase> M openTransaction() throws PPPSqlException {
		return this.<M>open(true);
	}

	@SuppressWarnings("unchecked")
	private <M extends PPPDatabase> M open(Boolean transaction) throws PPPSqlException {
		// 初始化数据库名字
		name();

		// 初始化数据源
		Connection conn = PPPDb.getConnection(this);
		if (transaction) {
			try {
				conn.setAutoCommit(false);
			} catch (SQLException | NullPointerException e) {
				throw new PPPSqlException("Open connection set autoCommit Exception!", e);
			}
		}
		return (M) this;
	}

	public void commit() throws PPPSqlException {
		Connection conn = PPPDb.getConnection(this);
		if (conn == null) {
			throw new PPPSqlException("Cannot commit transaction, connection '"
					+ name() + "' not available!");
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			throw new PPPSqlException("Cannot commit transaction, connection '"
					+ name() + "' commit failure!", e);
		}
	}

	public void rollback() throws PPPSqlException {
		Connection conn = PPPDb.getConnection(this);
		if (conn == null) {
			throw new PPPSqlException(
					"Cannot rollback transaction, connection '" + name()
							+ "' not available!");
		}
		try {
			conn.rollback();
		} catch (SQLException e) {
			throw new PPPSqlException(
					"Cannot rollback transaction, connection '" + name()
							+ "' commit failure!", e);
		}
	}

	public void close() throws PPPSqlException {
		PPPDb.closeConnection(this);
	}
	
	private <M extends PPPModel> Boolean isExist(String name) throws PPPSqlException{
		Connection conn = PPPDb.getConnection(this);
		QueryRunner runner = new QueryRunner();
		try {
			if(this instanceof SqliteDB){
				List<Map<String, Object>> result = runner.query(conn, "select * from sqlite_master where type='table' and name ='"+name+"';", new MapListHandler());
				if(result!=null && result.size()>0){
					return true;
				}
			}else if(this instanceof MysqlDB){
				List<Map<String, Object>> result = runner.query(conn, "select table_name from information_schema.tables where table_name='"+name+"';", new MapListHandler());
				if(result!=null && result.size()>0){
					return true;
				}
			}
		} catch (SQLException e) {
			throw new PPPSqlException("Table isExist happen Exception!", e);
		}
		return false;
	}
	
	private <M extends PPPModel> void createTable(Class<M> model) throws PPPSqlException{
		
		PPPRecord record = PPPRecord.<M>newInstance(model);
		
		if(record.isEmpty()){
			throw new PPPSqlException("Create datatable failure, because no field in Model!");
		}
		
		String name = model.getName().toLowerCase();
		name = name.split("$")[0];
		name = name.substring(name.lastIndexOf(".")+1, name.length());
		
		StringBuilder builder = new StringBuilder();
		builder.append("create table ");
		builder.append(name);
		builder.append("(");
		
		for (Iterator<String> ite = record.keySet().iterator();ite.hasNext();) {
			String key = ite.next();
			
			builder.append(key);
			builder.append(" ");
			try {
				PPPField<?> pppField = PPPField.newInstance(model, key);
				builder.append(dialect(pppField.type()));
				PrimaryKey primary = pppField.annotation(PrimaryKey.class);
				if(primary!=null){
					builder.append(" primary key");
					builder.append(" not null");
					builder.append(" auto_increment,");
					
				}else{
					builder.append(" null,");
				}
				
//				List<PPPField> fs = model.fields();
			} catch (PPPServiceException e) {
				throw new PPPSqlException(e.getMessage(), e);
			}
		}
		builder.replace(builder.length()-1, builder.length(), ")");
		builder.append(";");
		
		Connection conn = PPPDb.getConnection(this);
		QueryRunner runner = new QueryRunner();
		
		try {
			runner.update(conn, builder.toString());
		} catch (SQLException e) {
			throw new PPPSqlException("Create datatable Exception!", e);
		}
	}
	
	public List<PPPRecord> executeQuery(String oSql) throws PPPSqlException{
		String sql = oSql.trim().toLowerCase();
		PPPDb.getInstance().initModels();
		String dbName = getDbName(sql);
		if(!isExist(dbName==null?name():dbName)){
//			createTable(PPPDb.getInstance().model(dbName, this));
			throw new PPPSqlException("No database \""+dbName+"\" exist, can't execute query!");
		}
		Connection conn = PPPDb.getConnection(this);
		QueryRunner runner = new QueryRunner();
		List<PPPRecord> result;
		try {
			result = runner.query(conn, sql, new PPPRecordListHandler());
		} catch (SQLException e) {
			throw new PPPSqlException("ExecuteQuery sql: " + sql +" happen Exception!", e);
		}
		return result;
	}
	
	public <M extends PPPModel> List<M> executeQuery(String oSql, Class<M> clazz) throws PPPSqlException{
		String sql = oSql.trim();
		PPPDb.getInstance().initModels();
		String dbName = getDbName(sql);
		if(!isExist(dbName==null?name():dbName)){
			createTable(PPPDb.getInstance().model(dbName, this));
//			throw new PPPSqlException("No database \""+dbName+"\" exist, can't execute query!");
		}
		Connection conn = PPPDb.getConnection(this);
		QueryRunner runner = new QueryRunner();
		try {
			return runner.query(conn, sql, new BeanListHandler<M>(clazz));
		} catch (SQLException e) {
			throw new PPPSqlException("ExecuteQuery sql: " + sql +" happen Exception!", e);
		}
	}
	
	public Integer executeUpdate(String oSql) throws PPPSqlException{
		String sql = oSql.trim();
		PPPDb.getInstance().initModels();
		String dbName = getDbName(sql);
		if(!isExist(dbName==null?name():dbName)){
			createTable(PPPDb.getInstance().model(dbName, this));
		}
		Connection conn = PPPDb.getConnection(this);
		QueryRunner runner = new QueryRunner();
		try {
			return runner.update(conn, sql);
		} catch (SQLException e) {
			throw new PPPSqlException("ExecuteUpdate sql: " + sql +" happen Exception!", e);
		}
	}
	
	private String getDbName(String sqlString){
		String dbName = null;
		String sql = sqlString.trim().toLowerCase();
		String temp = null;
		if(sql.substring(0, INSERT_INTO_TEXT.length()).equals(INSERT_INTO_TEXT)){
			temp = sql.substring(INSERT_INTO_TEXT.length());
		}else if(sql.substring(0, INSERT_TEXT.length()).equals(INSERT_TEXT)){
			temp = sql.substring(INSERT_TEXT.length());
		}else if(sql.substring(0, UPDATE_TEXT.length()).equals(UPDATE_TEXT)){
			temp = sql.substring(UPDATE_TEXT.length());
		}else if(sql.substring(0, DELETE_TEXT.length()).equals(DELETE_TEXT)){
			temp = sql.substring(DELETE_TEXT.length());
		}else if(sql.substring(0, SELECT_TEXT.length()).equals(SELECT_TEXT)){
			temp = sql.substring(sql.indexOf(SELECT_FROM_TEXT)+SELECT_FROM_TEXT.length())+" ";
		}
		if(temp==null){
			throw new PPPTipsException("SQL grammar wrong: "+sqlString);
		}
		dbName = temp.substring(0,temp.indexOf(" "));
		return dbName;
	}
}
