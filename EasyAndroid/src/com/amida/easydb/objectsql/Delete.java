
package com.amida.easydb.objectsql;

import com.amida.easydb.DbTable;

public class Delete implements Sqlable {

    public Delete() {

    }

    public From from(Class<? extends DbTable> table) {
        return new From(this, table);
    }

    @Override
    public String toSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE ");
        return sql.toString();
    }

}
