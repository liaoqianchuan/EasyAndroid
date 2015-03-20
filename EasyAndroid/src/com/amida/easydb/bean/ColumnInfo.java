
package com.amida.easydb.bean;

import android.content.ClipData.Item;

public class ColumnInfo {
    private String mColumnName;
    private String mFiledName;
    private Class<?> mFiledType;
    private int mLength;
    private ColumnType.Type mColumnType;
    private boolean mNotNull;
    private OneToManyExtra mOneToManyExtra;

    public int getLength() {
        return mLength;
    }

    public void setLength(int length) {
        mLength = length;
    }

    public boolean isNotNull() {
        return mNotNull;
    }

    public void setNotNull(boolean notNull) {
        this.mNotNull = notNull;
    }

    public String getFiledName() {
        return mFiledName;
    }

    public void setFiledName(String filedName) {
        mFiledName = filedName;
    }

    public Class<?> getFiledType() {
        return mFiledType;
    }

    public void setFiledType(Class<?> filedType) {
        this.mFiledType = filedType;
    }

    public ColumnType.Type getColumnType() {
        return mColumnType;
    }

    public void setColumnType(ColumnType.Type mColumnType) {
        this.mColumnType = mColumnType;
    }

    public String getColumnName() {
        return mColumnName;
    }

    public void setColumnName(String columnName) {
        mColumnName = columnName;
    }

    public OneToManyExtra getOneToManyExtra() {
        return mOneToManyExtra;
    }

    public void setOneToManyExtra(OneToManyExtra oneToManyExtra) {
        this.mOneToManyExtra = oneToManyExtra;
    }

     public class OneToManyExtra {
        private Class<?> mParentCls;
        private Class<?> mItemCls;

        public Class<?> getItemCls() {
            return mItemCls;
        }

        public void setItemCls(Class<?> itemCls) {
            mItemCls = itemCls;
        }

        public String getOneToManyType() {
            return "";
        }

        public Class<?> getParentCls() {
            return mParentCls;
        }

        public void setParentCls(Class<?> parentCls) {
            mParentCls = parentCls;
        }
        
        public String getType() {
            return mParentCls.getName() +mItemCls.getName() + mFiledName;
        }
    }
}
