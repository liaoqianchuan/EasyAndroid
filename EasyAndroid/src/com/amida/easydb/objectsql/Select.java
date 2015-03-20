
package com.amida.easydb.objectsql;

import com.amida.easydb.DbTable;

public class Select implements Sqlable {

    public Select() {

    }

    public From from(Class<? extends DbTable> table) {
        return new From(this, table);
    }

    @Override
    public String toSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * ");
        return sql.toString();
    }

}
