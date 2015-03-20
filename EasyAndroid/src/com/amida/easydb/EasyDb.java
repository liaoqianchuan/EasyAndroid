
package com.amida.easydb;


import net.sqlcipher.database.SQLiteDatabase;
import android.app.Application;

/**
 * 初始化数据库，根据配置信息找到需要生成数据库的表class，得到需要的表信息
 * 
 * @author liaoqianchuan
 */
public class EasyDb {
    private DatabaseHelper mDatabaseHelper;
    private String mKey;

    private static EasyDb mDbManager;
    private EasyDb() {
    }

    public static EasyDb getInstance() {
        if (mDbManager == null) {
            mDbManager = new EasyDb();
        }
        return mDbManager;
    }

    public void init(Application app, String key) {
        SQLiteDatabase.loadLibs(app);  
        
        // 注意此处需要先将DbInfoFecher初始化，这样在databaseHelper中才能得到正确得数据库信息。
        DbInfoFecher.getInstance().initDbInfo(app);
        mDatabaseHelper = new DatabaseHelper(app);
        
        // 对数据库加解密使用的key
        mKey = key;
        
    }
    
    /**
     * 得到一个可读写的数据库。
     * @return
     */
    public SQLiteDatabase openDb() {
        return mDatabaseHelper.getWritableDatabase(mKey);
    }
    
}
