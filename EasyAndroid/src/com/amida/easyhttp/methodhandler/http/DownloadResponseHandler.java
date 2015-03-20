
package com.amida.easyhttp.methodhandler.http;

import java.io.File;
import java.io.IOException;

import org.apache.http.Header;

import android.content.Context;

import com.amida.easyfile.EasyFile;
import com.amida.easyhttp.ResponseListener;
import com.amida.easylog.EasyLog;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

public class DownloadResponseHandler extends FileAsyncHttpResponseHandler {
    private ResponseListener mResponseListener;
    public DownloadResponseHandler(ResponseListener responseListener, File file) {
        super(file);
        mResponseListener = responseListener;
    }

    @Override
    public void onSuccess(int arg0, Header[] arg1, File file) {
        EasyLog.d("download file success: " + file.getAbsolutePath());
        mResponseListener.onSuccess(file);
    }

    @Override
    public void onFailure(int errorCode, Header[] arg1, Throwable throwable, File arg3) {
        EasyLog.d("download file failure: " + errorCode + throwable != null ? ":" + throwable.getMessage() : "");
        mResponseListener.onFailure(errorCode, throwable != null ? throwable.getMessage() : "");
    }

    @Override
    public void onProgress(int bytesWritten, int totalSize) {
        super.onProgress(bytesWritten, totalSize);
        mResponseListener.onProgress(bytesWritten, totalSize);
        int progress = (bytesWritten * 100) / totalSize;
        EasyLog.d("progress: " + progress);
    }

//    @Override
//    public void onDummy(String dummyData) {
//        try {
//            EasyFile.copyFromAssert(mContext, dummyData, getTargetFile());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        onSuccess(200, null, getTargetFile());
//    }
}
