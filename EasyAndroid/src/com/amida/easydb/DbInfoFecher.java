
package com.amida.easydb;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

import android.content.Context;

import com.amida.easydb.annotaions.Column;
import com.amida.easydb.annotaions.OneToManyColumn;
import com.amida.easydb.annotaions.Table;
import com.amida.easydb.bean.ColumnInfo;
import com.amida.easydb.bean.ColumnInfo.OneToManyExtra;
import com.amida.easydb.bean.ColumnType;
import com.amida.easydb.bean.DbInfo;
import com.amida.easydb.bean.TableInfo;
import com.amida.easydb.innertable.OneToManyMap;
import com.amida.easydb.utils.ManifestUtils;

import dalvik.system.DexFile;

/**
 * 用于获取databse的信息。 在程序初始化的时候，我们需要根据manifest中配置的datbase
 * name，version和数据库表所在的包来得到数据库相关信息。
 * 
 * @author liaoqianchuan
 */
public class DbInfoFecher {
    private static final String DB_NAME = "DB_NAME";
    private static final String DB_VERSION = "DB_VERSION";
    private static final String DB_TABLSE_PACKAGES = "DB_TABLSE_PACKAGES";
    private DbInfo mDbInfo;

    private static DbInfoFecher mDbInfoFecher;

    private DbInfoFecher() {
    }

    public static DbInfoFecher getInstance() {
        if (mDbInfoFecher == null) {
            mDbInfoFecher = new DbInfoFecher();
        }
        return mDbInfoFecher;
    }

    public void initDbInfo(Context context) {
        mDbInfo = new DbInfo();
        String dbName = ManifestUtils.getMetaData(context, DB_NAME);
        int dbVersion = ManifestUtils.getMetaData(context, DB_VERSION);
        String tablesPackages = ManifestUtils.getMetaData(context, DB_TABLSE_PACKAGES);
        mDbInfo.setDbName(dbName);
        mDbInfo.setDbVersion(dbVersion);
        String[] packages = tablesPackages.split(",");
        ArrayList<Class<?>> clses = new ArrayList<Class<?>>();
        //添加框架本身需要的表
        clses.addAll(getClsesUnderPackage(context, "com.amida.easydb.innertable"));
        clses.addAll(getClsesUnderPackage(context, "com.amida.easyhttp.cache"));
        if (packages != null) {
            for (String tablePackage: packages) {
                clses.addAll(getClsesUnderPackage(context, tablePackage));
            }
        }
        mDbInfo.setTablesMap(getTablesMap(clses));
    }
    
    private HashMap<Class<?>, TableInfo> getTablesMap(ArrayList<Class<?>> clsList) {
        HashMap<Class<?>, TableInfo> tablesMap = new HashMap<Class<?>, TableInfo>();
        for (Class<?> cls : clsList) {
            TableInfo tableInfo = new TableInfo();
            Table tableName = (Table) cls.getAnnotation(Table.class);
            if (tableName != null) {
                tableInfo.setTableName(tableName.name());
            } else {
                tableInfo.setTableName(cls.getSimpleName());
            }
            tableInfo.setCloumnInfos(getCloumnsInfo(cls, new ArrayList<ColumnInfo>()));
            
            tablesMap.put(cls, tableInfo);
        }
        return tablesMap;
    }

    private ArrayList<ColumnInfo> getCloumnsInfo(Class<?> cls, ArrayList<ColumnInfo> cloumnsInfo) {
        ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(cls.getDeclaredFields()));
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setColumnName(column.name());
                columnInfo.setFiledName(field.getName());
                columnInfo.setLength(column.length());
                columnInfo.setNotNull(column.notNull());
                columnInfo.setFiledType(field.getType());
                columnInfo.setColumnType(ColumnType.getColoumnType(field.getType()));
                cloumnsInfo.add(columnInfo);
            } else if (field.isAnnotationPresent(OneToManyColumn.class)) {
                OneToManyColumn oneToManyColumn = field.getAnnotation(OneToManyColumn.class);
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setColumnName(DbTable.COLUMN_NAME_ONETOMANY);
                columnInfo.setFiledName(field.getName());
                columnInfo.setFiledType(field.getType());
                columnInfo.setColumnType(ColumnType.getColoumnType(field.getType()));
                OneToManyExtra oneToManyExtra = columnInfo.new OneToManyExtra();
                oneToManyExtra.setItemCls(oneToManyColumn.itemClass());
                oneToManyExtra.setParentCls(cls);
                columnInfo.setOneToManyExtra(oneToManyExtra);
                cloumnsInfo.add(columnInfo);
            }
        }
        
        Class<?> superCls = cls.getSuperclass();
        if (superCls != null) {
            getCloumnsInfo(superCls, cloumnsInfo);
        }
        return cloumnsInfo;
    }
    
    private ArrayList<Class<?>> getClsesUnderPackage(Context context, String packageName) {
        ArrayList<Class<?>> clsList = new ArrayList<Class<?>>();
        try {
            String sourcePath = context.getApplicationInfo().sourceDir;

            if (sourcePath != null) {
                DexFile sourceDir;
                sourceDir = new DexFile(sourcePath);
                Enumeration<String> entries = sourceDir.entries();
                
                final ClassLoader classLoader = context.getClass().getClassLoader();
                while (entries.hasMoreElements()) {
                    String element = entries.nextElement();
                    if (element.contains(packageName)) {
                        Class<?> cls = Class.forName(element, false, classLoader);
                        clsList.add(cls);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return clsList;
    }

    public DbInfo getDbInfo() {
        return mDbInfo;
    }
}
