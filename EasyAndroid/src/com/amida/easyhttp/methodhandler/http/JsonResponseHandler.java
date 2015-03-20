
package com.amida.easyhttp.methodhandler.http;

import org.apache.http.Header;

import com.amida.easyhttp.ResponseListener;
import com.amida.easylog.EasyLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class JsonResponseHandler extends AsyncHttpResponseHandler  {
    private ResponseListener mResponseListener;

    public JsonResponseHandler(ResponseListener responseListener) {
        mResponseListener = responseListener;
    }

    @Override
    public void onSuccess(int statusCode, Header[] header, byte[] responsebyte) {
        
        String responseStr = new String(responsebyte);
        EasyLog.d("response: " + responseStr);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Object responseObj = gson.fromJson(responseStr, mResponseListener.getTClass());

        mResponseListener.onSuccess(responseObj);
    }

    @Override
    public void onFailure(int errorCode, Header[] header, byte[] arg2, Throwable throwable) {
        EasyLog.d("failure: " + throwable != null ? throwable.getMessage() : "");
        mResponseListener.onFailure(errorCode, throwable != null ? throwable.getMessage() : "");
    }
//
//    @Override
//    public void onDummy(String dummyData) {
//        onSuccess(200, null, dummyData.getBytes());
//    }
//
//   

}
