package com.pipipark.j.random.data;

import com.pipipark.j.database.dialect.sqlite.SqliteDB;


@SuppressWarnings("serial")
public class RandomCodeDB extends SqliteDB {

	@Override
	public String dbName() {
		return "pipipark";
	}

	@Override
	public String dbUser() {
		return null;
	}

	@Override
	public String dbPassword() {
		return null;
	}

}
