package com.amida.easyhttp.methodhandler.cache;

import com.amida.easyhttp.ResponseIntercepter;

public interface CacheHandler extends ResponseIntercepter {
    boolean readCache();
}
