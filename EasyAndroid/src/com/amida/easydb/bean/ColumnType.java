
package com.amida.easydb.bean;

import java.util.ArrayList;
import java.util.HashMap;

import com.amida.easydb.DbTable;
import com.amida.easyreflection.EasyReflection;

/**
 * 定义了Sqllite的type 根据http://www.sqlite.org/datatype3.html
 * 
 * @author liaoqianchuan
 */
public class ColumnType {
    public enum Type {
        INTEGER, REAL, TEXT, BLOB
    }

    private static final HashMap<Class<?>, Type> TYPE_MAP = new HashMap<Class<?>, Type>() {
        private static final long serialVersionUID = 5856478569606481996L;

        {
            put(byte.class, Type.INTEGER);
            put(short.class, Type.INTEGER);
            put(int.class, Type.INTEGER);
            put(long.class, Type.INTEGER);
            put(boolean.class, Type.INTEGER);
            put(Byte.class, Type.INTEGER);
            put(Short.class, Type.INTEGER);
            put(Integer.class, Type.INTEGER);
            put(Long.class, Type.INTEGER);
            put(Boolean.class, Type.INTEGER);
            put(float.class, Type.REAL);
            put(double.class, Type.REAL);
            put(Float.class, Type.REAL);
            put(Double.class, Type.REAL);
            put(char.class, Type.TEXT);
            put(Character.class, Type.TEXT);
            put(String.class, Type.TEXT);
            put(byte[].class, Type.BLOB);
            put(Byte[].class, Type.BLOB);
        }
    };

    public static Type getColoumnType(Class<?> propertyType) {
        Type type = TYPE_MAP.get(propertyType);
        if (type == null) {
            if (EasyReflection.isSubclassOf(propertyType, DbTable.class)) {
                return Type.INTEGER;
            } else if (EasyReflection.isSubclassOf(propertyType, ArrayList.class)) {
                return Type.TEXT;
            } else if (EasyReflection.isSubclassOf(propertyType, Enum.class)) {
                return Type.TEXT;
            }
        }
        return type;
    }
}
