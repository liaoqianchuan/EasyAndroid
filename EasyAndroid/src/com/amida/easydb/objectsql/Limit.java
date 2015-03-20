package com.amida.easydb.objectsql;

import com.amida.easydb.DbTable;

public class Limit extends Operator {
    private Operator mPreOperator;
    private int mLimitCount;

    public Limit(Operator preOperator, int limitCount) {
        mPreOperator = preOperator;
        mLimitCount = limitCount;
    }


    @Override
    public String toSql() {
        StringBuffer sql = new StringBuffer();

        sql.append(mPreOperator.toSql())
                .append("LIMIT ")
                .append(mLimitCount)
                .append(" ");
        return sql.toString();
    }

    @Override
    Class<? extends DbTable> getTable() {
        return mPreOperator.getTable();
    }
}
