
package com.amida.easydb.bean;

import java.util.ArrayList;

import com.amida.easydb.DbTable;

public class TableInfo {
    private String mTableName;
    private ArrayList<ColumnInfo> mCloumnInfos = new ArrayList<ColumnInfo>();

    public String getEasyColumnIdName() {
        return DbTable.COLUMN_NAME_ID;
    }
    public String getTableName() {
        return mTableName;
    }

    public void setTableName(String tableName) {
        mTableName = tableName;
    }

    public ArrayList<ColumnInfo> getCloumnInfos() {
        return mCloumnInfos;
    }

    public void setCloumnInfos(ArrayList<ColumnInfo> cloumnInfos) {
        mCloumnInfos = cloumnInfos;
    }

}
