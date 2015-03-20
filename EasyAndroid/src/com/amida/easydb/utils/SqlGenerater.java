
package com.amida.easydb.utils;

import java.util.ArrayList;

import android.text.TextUtils;

import com.amida.easydb.DbTable;
import com.amida.easydb.bean.ColumnInfo;
import com.amida.easydb.bean.TableInfo;
import com.amida.easylog.EasyLog;

/**
 * 用于根据参数生成sql语句
 * 
 * @author liaoqianchuan
 */
public class SqlGenerater {

    private static SqlGenerater mSqlGenerater;

    private SqlGenerater() {

    }

    public static SqlGenerater getInstance() {
        if (mSqlGenerater == null) {
            mSqlGenerater = new SqlGenerater();
        }
        return mSqlGenerater;
    }

    //@formatter:off
    /**
     * 根据tableInfo生成创建表的sql语句。
     * 
     *
     * CREATE TABLE Persons ( Id_P int, 
     *                        LastName varchar(255),
     *                        FirstName varchar(255), 
     *                        Address varchar(255), 
     *                        City varchar(255) 
     *                      ) 
     *
     * @param tableInfo
     * @return
     */
    //@formatter:on
    public String generateCreateTableSql(TableInfo tableInfo) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ")
                .append(tableInfo.getTableName())
                .append(" (");

        ArrayList<ColumnInfo> cloumns = tableInfo.getCloumnInfos();
        ArrayList<String> cloumnNames = new ArrayList<String>();
        for (ColumnInfo cloumnInfo : cloumns) {
           
            if (cloumnInfo.getColumnName().equals(tableInfo.getEasyColumnIdName())) {
                cloumnNames.add(cloumnInfo.getColumnName() + " " + cloumnInfo.getColumnType() + " PRIMARY KEY AUTOINCREMENT");
            } else {
                cloumnNames.add(cloumnInfo.getColumnName() + " " + cloumnInfo.getColumnType());
            }
        }
        sql.append(TextUtils.join(",", cloumnNames));
        sql.append(")");

        EasyLog.d(sql.toString());
        return sql.toString();
    }
}
