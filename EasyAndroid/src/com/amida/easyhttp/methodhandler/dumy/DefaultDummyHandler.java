package com.amida.easyhttp.methodhandler.dumy;

import android.content.Context;

import com.amida.easyhttp.EasyHttpStatusCode;
import com.amida.easyhttp.methodhandler.MethodInfo;

class DefaultDummyHandler implements DummyHandler {
    private String mDummyData;
    public DefaultDummyHandler(String dummyData) {
        mDummyData = dummyData;
    }
    @Override
    public void onLoadDummyData(Context context, MethodInfo methodInfo, Object[] args) {
        methodInfo.getResponseHandler().onSuccess(EasyHttpStatusCode.STATUS_SUCCESS, null, mDummyData.getBytes());
    }
}