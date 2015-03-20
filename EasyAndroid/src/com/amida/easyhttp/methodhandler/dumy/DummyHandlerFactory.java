package com.amida.easyhttp.methodhandler.dumy;

import com.amida.easyhttp.annotations.EasyHttp.ApiType;
import com.amida.easylog.EasyLog;

public final class DummyHandlerFactory {
    private DummyHandlerFactory() {

    }

    public static DummyHandler create(ApiType apiType, String dummyData) {
        switch (apiType) {
            case DOWNLOADFILE:
                EasyLog.d("new FileDummyHandler" );
                return new FileDummyHandler(dummyData);
            default:
                EasyLog.d("new DefaultDummyHandler" );
                return new DefaultDummyHandler(dummyData);
        }
    }
}
