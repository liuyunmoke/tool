package com.pipipark.j.random.data.model;

import java.sql.Timestamp;

import com.pipipark.j.database.PPPModel;
import com.pipipark.j.database.annotation.Db;
import com.pipipark.j.database.annotation.PrimaryKey;
import com.pipipark.j.random.data.RandomCodeDB;

@Db(RandomCodeDB.class)
@SuppressWarnings("serial")
public class RCode extends PPPModel {

	@PrimaryKey
	private Integer id;
	
	/*
	 * 关键字(MD5过的串).
	 */
	private String key;
	
	/*
	 * 随机码.
	 */
	private String code;
	
	/*
	 * 更新时间.
	 */
	private Timestamp updateDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
}
