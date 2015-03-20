
package com.amida.easydb.innertable;

import com.amida.easydb.DbTable;
import com.amida.easydb.annotaions.Column;

public class OneToManyMap extends DbTable {
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PARENTCOLUMNID = "parentColumnId";
    public static final String COLUMN_ITEMCOLUMNID = "itemColumnId";
    @Column(name = COLUMN_TYPE)
    private String type;
    @Column(name = COLUMN_PARENTCOLUMNID)
    private Long parentColumnId;
    @Column(name = COLUMN_ITEMCOLUMNID)
    private Long itemColumnId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(Long parentColumnId) {
        this.parentColumnId = parentColumnId;
    }

    public Long getItemColumnId() {
        return itemColumnId;
    }

    public void setItemColumnId(Long itemColumnId) {
        this.itemColumnId = itemColumnId;
    }
}
