package com.pipipark.j.database.tool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.dbcp.BasicDataSource;

import com.pipipark.j.database.PPPDatabase;
import com.pipipark.j.database.PPPModel;
import com.pipipark.j.database.annotation.Db;
import com.pipipark.j.database.exception.PPPSqlException;
import com.pipipark.j.system.classscan.v2.PPPScan;
import com.pipipark.j.system.core.PPPString;
import com.pipipark.j.system.core.map.PPPDoubleMap;


public class PPPDb {

	public static final String DEFULT_TABLE_NAME = "pipipark";
	public static final String DEFULT_USER = null;
	public static final String DEFULT_PASSWORD = "123456";
	private static final ThreadLocal<PPPDoubleMap<String, Class<?>, Connection>> local = new ThreadLocal<PPPDoubleMap<String, Class<?>, Connection>>();
	
	private static PPPDb _me = null;
	private final static Object syncLock = new Object();
	private ConcurrentMap<String, Map<Class<?>, BasicDataSource>> ds = new ConcurrentHashMap<String, Map<Class<?>, BasicDataSource>>();
	
	private ConcurrentMap<Class<? extends PPPDatabase>, List<Class<?>>> ms = new ConcurrentHashMap<Class<? extends PPPDatabase>, List<Class<?>>>();
	
	public static PPPDb getInstance(){
		if(_me==null){
			synchronized (syncLock) {
				_me = new PPPDb();
			}
		}
		return _me;
	}
	
	public <M extends PPPDatabase> BasicDataSource dataSource(M db){
		if(db==null){
			return null;
		}
		Map<Class<?>, BasicDataSource> dbs = ds.get(db.name());
		if(dbs==null){
			dbs = new HashMap<Class<?>, BasicDataSource>();
			ds.put(db.name(), dbs);
		}
		BasicDataSource ds = dbs.get(db.getClass());
		if(ds==null){
			ds = new BasicDataSource();
			db.dataSource(ds);
			dbs.put(db.getClass(), ds);
		}
		return ds;
	}
	
	public static <M extends PPPDatabase> Connection getConnection(M db) throws PPPSqlException{
		PPPDoubleMap<String, Class<?>, Connection> map = local.get();
		if(map==null){
			map = new PPPDoubleMap<String, Class<?>, Connection>();
			local.set(map);
		}
		Connection conn = map.get(db.name(), db.getClass());
		if(conn==null){
			BasicDataSource ds = PPPDb.getInstance().dataSource(db);
			try {
				if (db.user() == null) {
					conn = ds.getConnection();
				} else {
					conn = ds.getConnection(db.user(), db.password());
				}
			} catch (SQLException e) {
				throw new PPPSqlException("Connection can't be got Exception!", e);
			}
			map.put(db.name(), db.getClass(), conn);
		}
		return conn;
	}
	
	public static <M extends PPPDatabase> void closeConnection(M db) throws PPPSqlException{
		PPPDoubleMap<String, Class<?>, Connection> map = local.get();
		if(map==null){
			return;
		}
		try {
			Connection conn = map.get(db.name(), db.getClass());
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			throw new PPPSqlException("Close connection Exception!", e);
		} finally {
			map.remove(db.name(), db.getClass());
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <M extends PPPModel> void initModels() throws PPPSqlException{
		if(ms.size()>0){
			return;
		}
		PPPScan scaner = new PPPScan();
//		String packagePath = "lib.com.pipipark";
		String packagePath = "com.pipipark";
		Set<Class> clazzs = scaner.doScan(packagePath);
		for(Class clazz : clazzs){
			if(PPPModel.class.isAssignableFrom(clazz)){
				Class<M> mc = (Class<M>)clazz;
				Db annotation = mc.getAnnotation(Db.class);
				if(annotation==null){
					continue;
				}
				Class<? extends PPPDatabase> dbClazz = annotation.value();
				List list = ms.get(dbClazz);
				if(list==null){
					list = new ArrayList<M>();
					ms.put(dbClazz, list);
				}
				list.add(clazz);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <M extends PPPModel, N extends PPPDatabase> Class<M> model(String name, N db) throws PPPSqlException{
		List<Class<?>> list = ms.get(db.getClass());
		if(list==null){
			throw new PPPSqlException("ModelClass: "+name+" not found, you must be create it!");
		}
		for (Class<?> obj : list) {
			if(name.equals(PPPString.<M>className((Class<M>)obj).toLowerCase())){
				return (Class<M>)obj;
			}
		}
		throw new PPPSqlException("ModelClass: "+name+" not found, you must be create it!");
	}
}
