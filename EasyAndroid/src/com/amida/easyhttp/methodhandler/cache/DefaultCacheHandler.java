package com.amida.easyhttp.methodhandler.cache;

import java.util.ArrayList;
import java.util.Date;

import android.text.TextUtils;

import com.amida.easydb.objectsql.Delete;
import com.amida.easydb.objectsql.Select;
import com.amida.easyhttp.ResponseListener;
import com.amida.easyhttp.cache.ResponseCache;
import com.amida.easylog.EasyLog;
import com.google.gson.Gson;

class DefaultCacheHandler implements CacheHandler {
    

    private String mCacheIdentity;
    private ResponseListener mResponseListener;

    public DefaultCacheHandler(String cacheIdentity,ResponseListener responseListener) {
        mCacheIdentity = cacheIdentity;
        mResponseListener = responseListener;
    }

    @Override
    public boolean  readCache() {
        ArrayList<ResponseCache> caches = new Select().from(ResponseCache.class)
                .where(ResponseCache.COLUMN_TYPE + "='" + mCacheIdentity + "'").execute();
        if (caches != null && caches.size() > 0) {
            EasyLog.d("find cache for api:" + mCacheIdentity);
            ResponseCache cache = caches.get(0);
            mResponseListener.onSuccess(new Gson().fromJson(cache.getResponse(), mResponseListener.getTClass()));
            return true;
        } else {
            return false;
        }
        
    }
    
    @Override
    public void onSuccess(Object response) {
        if (TextUtils.isEmpty(mCacheIdentity)) {
            return;
        }

        EasyLog.d("cache " + mCacheIdentity);

        new Delete().from(ResponseCache.class).where(ResponseCache.COLUMN_TYPE + "='" + mCacheIdentity + "'").execute();

        ResponseCache cache = new ResponseCache();
        cache.setResponse(new Gson().toJson(response));
        cache.setType(mCacheIdentity);
        cache.setUpdateDate(new Date().getTime());
        cache.save();
    }

    
}