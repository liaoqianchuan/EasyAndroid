
package com.amida.easydb.parser;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.database.Cursor;

import com.amida.easydb.DbInfoFecher;
import com.amida.easydb.DbTable;
import com.amida.easydb.bean.ColumnInfo;
import com.amida.easydb.bean.ColumnInfo.OneToManyExtra;
import com.amida.easydb.bean.TableInfo;
import com.amida.easydb.innertable.OneToManyMap;
import com.amida.easydb.objectsql.Select;
import com.amida.easylog.EasyLog;
import com.amida.easyreflection.EasyReflection;

public class CursorParser {

    public <T extends DbTable> ArrayList<T> parse(Class<? extends DbTable> type, Cursor cursor) {

        try {
            ArrayList<T> entities = new ArrayList<T>();
            List<String> columnsOrdered = new ArrayList<String>(Arrays.asList(cursor.getColumnNames()));
            TableInfo tableInfo = DbInfoFecher.getInstance().getDbInfo().getTableInfo(type);
            ArrayList<ColumnInfo> columns = tableInfo.getCloumnInfos();
            if (cursor.moveToFirst()) {
                do {
                    T data = null;
                    data = (T) type.getConstructor().newInstance();
                    for (ColumnInfo column : columns) {
                        Class<?> fieldType = column.getFiledType();

                        final int columnIndex = columnsOrdered.indexOf(column.getColumnName());
                        if (columnIndex < 0) {
                            continue;
                        }
                        Object value = null;
                        if (cursor.isNull(columnIndex)) {
                        } else if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
                            value = cursor.getInt(columnIndex);
                        } else if (fieldType.equals(Short.class) || fieldType.equals(short.class)) {
                            value = cursor.getInt(columnIndex);
                        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                            value = cursor.getInt(columnIndex);
                        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                            value = cursor.getLong(columnIndex);
                        } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
                            value = cursor.getFloat(columnIndex);
                        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                            value = cursor.getDouble(columnIndex);
                        } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
                            value = cursor.getInt(columnIndex) != 0;
                        } else if (fieldType.equals(Character.class) || fieldType.equals(char.class)) {
                            value = cursor.getString(columnIndex).charAt(0);
                        } else if (fieldType.equals(String.class)) {
                            value = cursor.getString(columnIndex);
                        } else if (fieldType.equals(Byte[].class) || fieldType.equals(byte[].class)) {
                            value = cursor.getBlob(columnIndex);
                        } else if (EasyReflection.isSubclassOf(fieldType, DbTable.class)) {
                            final long easyColumnId = cursor.getLong(columnIndex);
                            DbTable entity = new Select().from((Class<? extends DbTable>) fieldType)
                                    .where(DbTable.COLUMN_NAME_ID + "=" + easyColumnId).executeSingle();
                            EasyLog.d("Sql: " + new Select().from((Class<? extends DbTable>) fieldType)
                                    .where(DbTable.COLUMN_NAME_ID + "=" + easyColumnId).toSql());
                            value = entity;
                        } else if (EasyReflection.isSubclassOf(fieldType, Enum.class)) {
                            final Class<? extends Enum> enumType = (Class<? extends Enum>) fieldType;
                            value = Enum.valueOf(enumType, cursor.getString(columnIndex));
                        } else if (fieldType.equals(ArrayList.class)) {
                            OneToManyExtra oneToManyExtra = column.getOneToManyExtra();
                            //查询id
                            long easyColumnId =  cursor.getLong(cursor.getColumnIndex(DbTable.COLUMN_NAME_ID));
                            ArrayList<OneToManyMap> idColumns = new Select().from(OneToManyMap.class).where(OneToManyMap.COLUMN_TYPE + "='" +oneToManyExtra.getType()+ "' AND " +OneToManyMap.COLUMN_PARENTCOLUMNID + "=" + easyColumnId).execute();
//                            ArrayList<OneToManyMap> idColumns = new Select().from(OneToManyMap.class).execute();
                            //从数据库中取出id对于的行，添加到list中
                            StringBuilder where = new StringBuilder();
                            for (int i = 0; i < idColumns.size(); i++) {
                                where.append(DbTable.COLUMN_NAME_ID + "=").append(idColumns.get(i).getItemColumnId());
                                if (i != idColumns.size()-1) {
                                    where.append(" OR "); 
                                }
                            }
                            ArrayList results = new Select().from((Class<? extends DbTable>) oneToManyExtra.getItemCls()).where(where.toString()).execute();
                            value = results;
                        }

                        if (value != null) {
                            EasyReflection.setFieldValue(data, column.getFiledName(), value);
                        }
                    }
                    entities.add(data);
                } while (cursor.moveToNext());

            }
            return entities;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;

    }
}
