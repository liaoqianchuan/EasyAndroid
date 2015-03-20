
package com.amida.easydb.objectsql;

import java.util.ArrayList;

import android.database.Cursor;
import android.text.TextUtils;

import com.amida.easydb.DbTable;
import com.amida.easydb.EasyDb;
import com.amida.easydb.parser.CursorParser;
import com.amida.easylog.EasyLog;

public abstract class Operator implements Sqlable {

    abstract Class<? extends DbTable> getTable();

    public <T extends DbTable> ArrayList<T> execute() {
        EasyLog.d("excute sql: " + toSql());
        String sql = toSql();
        if (!TextUtils.isEmpty(sql) && sql.contains("SELECT ")) {
            Cursor cursor = EasyDb.getInstance().openDb().rawQuery(toSql(), null);
            return new CursorParser().parse(getTable(), cursor);
        } else {
            EasyDb.getInstance().openDb().execSQL(toSql());
            return null;
        }
    }
    
    public <T extends DbTable> T executeSingle() {
        EasyLog.d("excute sql to get one line: " + toSql());
        String sql = toSql();
        if (!TextUtils.isEmpty(sql) && sql.contains("SELECT ")) {
            Cursor cursor = EasyDb.getInstance().openDb().rawQuery(toSql(), null);
            ArrayList<T> rows =  new CursorParser().parse(getTable(), cursor);
            if (rows != null && rows.size()>0) {
                return rows.get(0);
            } else {
                return null;
            }
        } else {
            EasyDb.getInstance().openDb().execSQL(toSql());
            return null;
        }
    }
    
}
