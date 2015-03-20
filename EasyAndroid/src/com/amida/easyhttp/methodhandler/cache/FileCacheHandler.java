package com.amida.easyhttp.methodhandler.cache;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

import com.amida.easyfile.EasyFile;
import com.amida.easyhttp.ResponseListener;
import com.amida.easylog.EasyLog;

class FileCacheHandler implements CacheHandler {

    private String mCacheIdentity;
    private ResponseListener mResponseListener;

    public FileCacheHandler(String cacheIdentity,ResponseListener responseListener) {
        mCacheIdentity = cacheIdentity;
        mCacheIdentity = cacheIdentity;
        mResponseListener = responseListener;
    }
    
    @Override
    public boolean readCache() {
        File responseFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +mCacheIdentity);
        if (responseFile.exists()) {
            EasyLog.d("find cache for api " + mCacheIdentity);
            mResponseListener.onSuccess(responseFile);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSuccess(Object response) {
        File file = (File) response;

        EasyLog.d("cache " + mCacheIdentity);

        try {
            EasyFile.copy(file, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +mCacheIdentity));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    

}
