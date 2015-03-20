package com.amida.easydb.objectsql;

import com.amida.easydb.DbTable;

public class Where extends Operator{
    private Operator mPreOperator;
    private String mWhereSql;
    public Where(Operator preOperator, String whereSql) {
        mPreOperator = preOperator;
        mWhereSql = whereSql;
    }
    
    public void orderBy(String columnName) {
        new OrderBy(this, columnName);
    }
    
    @Override
    public String toSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(mPreOperator.toSql());
        sql.append("WHERE " + mWhereSql + " ");
        return sql.toString();
    }

    @Override
    Class<? extends DbTable> getTable() {
        return mPreOperator.getTable();
    }

}
