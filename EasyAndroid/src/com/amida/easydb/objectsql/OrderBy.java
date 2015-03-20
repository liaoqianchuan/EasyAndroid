
package com.amida.easydb.objectsql;

import com.amida.easydb.DbTable;

public class OrderBy extends Operator {
    private Operator mPreOperator;
    private String mColumnName;
    private boolean mIsAsc = true;
    private boolean mAppendOrderBy = false;

    public OrderBy(Operator preOperator, String columnName) {
        mPreOperator = preOperator;
        mColumnName = columnName;
    }

    /**
     * 降序排列
     * 
     * @return
     */
    public OrderBy desc() {
        mIsAsc = false;
        return this;
    }

    /**
     * 升序排列
     * 
     * @return
     */
    public OrderBy asc() {
        mIsAsc = true;
        return this;
    }

    public void orderBy(String columnName) {
        mAppendOrderBy= true;
        new OrderBy(this, columnName);
    }
    
    public Limit limit(int limitCount) {
        return new Limit(this, limitCount);
    }

    @Override
    public String toSql() {
        StringBuffer sql = new StringBuffer();

        sql.append(mPreOperator.toSql())
                .append("ORDER BY ")
                .append(mColumnName)
                .append(" ")
                .append(mIsAsc ? "ASC" : "DESC")
                .append(mAppendOrderBy?", ":"")
                .append(" ");
        return sql.toString();
    }

    @Override
    Class<? extends DbTable> getTable() {
        return mPreOperator.getTable();
    }

}
