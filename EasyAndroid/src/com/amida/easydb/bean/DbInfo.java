
package com.amida.easydb.bean;

import java.util.Collection;
import java.util.HashMap;

public class DbInfo {
    private String mDbName;
    private int mDbVersion;
    private HashMap<Class<?>, TableInfo> mTablesMap;

    public String getDbName() {
        return mDbName;
    }

    public void setDbName(String dbName) {
        mDbName = dbName;
    }

    public int getDbVersion() {
        return mDbVersion;
    }

    public void setDbVersion(int dbVersion) {
        mDbVersion = dbVersion;
    }

    public Collection<TableInfo> getTablesInfo() {
        return  mTablesMap.values();
    }
    
    public TableInfo getTableInfo(Class<?> cls) {
        return mTablesMap.get(cls);
    }

    public void setTablesMap(HashMap<Class<?>, TableInfo> tablesMap) {
        mTablesMap = tablesMap;
    }

}
