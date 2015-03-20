
package com.amida.easydb.objectsql;

import com.amida.easydb.DbInfoFecher;
import com.amida.easydb.DbTable;

public class From extends Operator {
    private Sqlable mPreSql;
    private Class<? extends DbTable> mTable;

    public From(Sqlable preSql, Class<? extends DbTable> table) {
        mPreSql = preSql;
        mTable = table;
    }

    public Where where(String whereSql) {
        return new Where(this, whereSql);
    }
    
    public OrderBy orderBy(String columnName) {
        return new OrderBy(this, columnName);
    }
    
    public Limit limit(int limitCount) {
        return new Limit(this, limitCount);
    }

    @Override
    public String toSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(mPreSql.toSql());
        sql.append("FROM " + DbInfoFecher.getInstance().getDbInfo().getTableInfo(mTable).getTableName() + " ");
        return sql.toString();
    }

    @Override
    Class<? extends DbTable> getTable() {
        return mTable;
    }

}
