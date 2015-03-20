package com.amida.easyhttp.methodhandler.dumy;

import java.io.File;
import java.io.IOException;

import android.content.Context;

import com.amida.easyfile.EasyFile;
import com.amida.easyhttp.EasyHttpStatusCode;
import com.amida.easyhttp.methodhandler.MethodInfo;
import com.amida.easyhttp.methodhandler.http.DownloadResponseHandler;

class FileDummyHandler implements DummyHandler {
    private String mDummyData;
    public FileDummyHandler(String dummyData) {
        mDummyData = dummyData;
    }
    @Override
    public void onLoadDummyData(Context context, MethodInfo methodInfo, Object[] args) {
        DownloadResponseHandler handler = (DownloadResponseHandler) methodInfo.getResponseHandler();
        try {
            EasyFile.copyFromAssert(context, mDummyData, (File)args[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.onSuccess(EasyHttpStatusCode.STATUS_SUCCESS, null, (File)args[2]);
    }

}
