package com.amida.easyhttp.methodhandler.dumy;

import android.content.Context;

import com.amida.easyhttp.methodhandler.MethodInfo;

public interface DummyHandler {
    void onLoadDummyData(Context context, MethodInfo methodInfo, Object[] args);
}
