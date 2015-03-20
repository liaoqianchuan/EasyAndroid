
package com.amida.easydb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sqlcipher.database.SQLiteDatabase;
import android.content.ContentValues;

import com.amida.easydb.annotaions.Column;
import com.amida.easydb.bean.ColumnInfo;
import com.amida.easydb.bean.TableInfo;
import com.amida.easydb.innertable.OneToManyMap;
import com.amida.easydb.objectsql.Select;
import com.amida.easylog.EasyLog;
import com.amida.easyreflection.EasyReflection;

/**
 * 实现了save函数，用户可以调用该函数来保存或者更新数据库，所有的数据库表的数据结构都必须继承自该类
 * 
 * @author liaoqianchuan
 */
public class DbTable {
    public static final String COLUMN_NAME_ID = "easyColumnId";
    public static final String COLUMN_NAME_ONETOMANY = "oneToMany";
    @Column(name = COLUMN_NAME_ID)
    private Long easyColumnId = null;

    /**
     * 保存设置的信息到数据库中
     */
    public void save() {
        SQLiteDatabase db = EasyDb.getInstance().openDb();
        ContentValues values = new ContentValues();
        TableInfo tableInfo = DbInfoFecher.getInstance().getDbInfo().getTableInfo(getClass());
        ArrayList<ColumnInfo> columnsInfo = tableInfo.getCloumnInfos();
        HashMap<String, ArrayList<Long>> idsMap = new HashMap<String, ArrayList<Long>>();
        if (columnsInfo != null) {
            for (ColumnInfo columnInfo : columnsInfo) {
                String columnName = columnInfo.getColumnName();
                Class<?> fieldType = columnInfo.getFiledType();
                Object value = EasyReflection.getFieldValue(this, columnInfo.getFiledName());

                if (value == null) {
                    values.putNull(columnName);
                } else if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
                    values.put(columnName, (Byte) value);
                } else if (fieldType.equals(Short.class) || fieldType.equals(short.class)) {
                    values.put(columnName, (Short) value);
                } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                    values.put(columnName, (Integer) value);
                } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                    values.put(columnName, (Long) value);
                } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
                    values.put(columnName, (Float) value);
                } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                    values.put(columnName, (Double) value);
                } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
                    values.put(columnName, (Boolean) value);
                } else if (fieldType.equals(Character.class) || fieldType.equals(char.class)) {
                    values.put(columnName, value.toString());
                } else if (fieldType.equals(String.class)) {
                    values.put(columnName, value.toString());
                } else if (fieldType.equals(Byte[].class) || fieldType.equals(byte[].class)) {
                    values.put(columnName, (byte[]) value);
                } else if (EasyReflection.isSubclassOf(fieldType, DbTable.class)) {
                    Long columnId = ((DbTable) value).getEasyColumnId();
                    if (columnId == null) {
                        ((DbTable) value).save();
                        columnId = ((DbTable) value).getEasyColumnId();
                    }
                    values.put(columnName, columnId);
                } else if (EasyReflection.isSubclassOf(fieldType, Enum.class)) {
                    values.put(columnName, ((Enum<?>) value).name());
                } else if (fieldType.equals(ArrayList.class)) {
                    ArrayList list = (ArrayList) value;
                    ArrayList<Long> ids = new ArrayList<Long>();
                    for (Object item : list) {
                        DbTable tableItem = (DbTable) item;
                        tableItem.save();
                        ids.add(tableItem.getEasyColumnId());
                    }
                    if (ids.size() > 0) {
                        idsMap.put(columnInfo.getOneToManyExtra().getType(), ids);
                    }
                    
                    values.put(columnName, columnInfo.getOneToManyExtra().getType());
                }
            }
        }

        if (getEasyColumnId() == null) {
            long columnId = db.insert(tableInfo.getTableName(), null, values);
            setEasyColumnId(columnId);
            if (getEasyColumnId() != -1) {
                EasyLog.d("insert to success, id is: " + getEasyColumnId());
            } else {
                EasyLog.d("insert to db failed");
            }

            // 保存一对多的list中数据的id到OneToManyMap表中
            if (idsMap.size() > 0) {
                Iterator iter = idsMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    ArrayList<Long> ids = (ArrayList<Long>) entry.getValue();
                    
                    for (Long id: ids) {
                        OneToManyMap itemColumn = new OneToManyMap();
                        itemColumn.setType((String) key);
                        itemColumn.setItemColumnId(id);
                        itemColumn.setParentColumnId(columnId);
                        itemColumn.save();
                    }
                    
                }
            }
        } else {
            db.update(tableInfo.getTableName(), values, COLUMN_NAME_ID + "=" + getEasyColumnId(), null);
        }

    }

    public static <T extends DbTable> ArrayList<T> load(Class<? extends DbTable> type) {
        return new Select().from(type).execute();
    }

    /**
     * 根据id字段删除该列
     */
    public void delete() {
        TableInfo tableInfo = DbInfoFecher.getInstance().getDbInfo().getTableInfo(getClass());
        SQLiteDatabase db = EasyDb.getInstance().openDb();
        if (getEasyColumnId() != null) {
            db.delete(tableInfo.getTableName(), COLUMN_NAME_ID + "=" + getEasyColumnId(), null);
        }
    }

    public Long getEasyColumnId() {
        return easyColumnId;
    }

    public void setEasyColumnId(Long easyColumnId) {
        this.easyColumnId = easyColumnId;
    }

}
