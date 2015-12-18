package com.pipipark.j.database.tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;

public class PPPRecordListHandler extends AbstractListHandler<PPPRecord> {
	
    private final RowProcessor convert;

    public PPPRecordListHandler() {
        this(new BasicRowProcessor());
    }

    public PPPRecordListHandler(RowProcessor convert) {
        super();
        this.convert = convert;
    }

	@Override
	protected PPPRecord handleRow(ResultSet rs) throws SQLException {
		return new PPPRecord(this.convert.toMap(rs));
	}

}
