package com.pipipark.database.test.model;

import com.pipipark.database.test.MyDB;
import com.pipipark.j.database.PPPModel;
import com.pipipark.j.database.annotation.Db;
import com.pipipark.j.database.annotation.PrimaryKey;

@Db(MyDB.class)
public class Persons extends PPPModel {

	private static final long serialVersionUID = -3531909629428947002L;

	@PrimaryKey
	private Integer id;
	private String name;
	private Integer num;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
}
