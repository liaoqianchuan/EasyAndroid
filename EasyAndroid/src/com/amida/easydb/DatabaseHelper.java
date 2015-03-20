package com.amida.easydb;

import java.util.Collection;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;

import com.amida.easydb.bean.TableInfo;
import com.amida.easydb.utils.SqlGenerater;

public final class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		super(context, DbInfoFecher.getInstance().getDbInfo().getDbName(), null, DbInfoFecher.getInstance().getDbInfo().getDbVersion());
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    //TODO UPGRADE TABLE
	}

	private void createTables(SQLiteDatabase db) {
		Collection<TableInfo> tablse = DbInfoFecher.getInstance().getDbInfo().getTablesInfo();
		for (TableInfo tableInfo : tablse) {
			db.execSQL(SqlGenerater.getInstance().generateCreateTableSql(tableInfo));
		}
	}
}
