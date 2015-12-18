package com.pipipark.j.sms.data;

import com.pipipark.j.database.dialect.sqlite.SqliteDB;


/***
 * 短信记录库
 * @see 记录接受人手机号和发送内容,并记录发送时间.
 * @author cwj
 */
public class SmsDB extends SqliteDB {
	private static final long serialVersionUID = -2944752855137687608L;

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
