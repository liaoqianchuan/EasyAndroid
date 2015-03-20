package com.amida.easyhttp.methodhandler.cache;

import com.amida.easyfile.EasyFile;
import com.amida.easyhttp.ResponseListener;
import com.amida.easyhttp.annotations.EasyHttp.ApiType;
import com.amida.easylog.EasyLog;

public final class CacheHandlerFactory {
    private CacheHandlerFactory() {

    }

    public static CacheHandler create(ApiType apiType, String cacheIdentity,ResponseListener responseListener) {
        switch (apiType) {
            case DOWNLOADFILE:
                EasyLog.d("new FileCacheHandler" );
                return new FileCacheHandler(cacheIdentity, responseListener);
            default:
                EasyLog.d("new DefaultCacheHandler" );
                return new DefaultCacheHandler(cacheIdentity, responseListener);
        }
    }
}
