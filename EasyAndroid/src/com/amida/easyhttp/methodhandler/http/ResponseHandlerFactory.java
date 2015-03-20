
package com.amida.easyhttp.methodhandler.http;

import java.io.File;

import com.amida.easyhttp.ResponseListener;
import com.amida.easyhttp.annotations.EasyHttp.ApiType;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 提供几种基本的参数生产方式，例如，1.将对象中的每个属性及其值设置到params中 2.将对象转换成json，参数为params=json
 * 
 * @author liaoqianchuan
 */
public final class ResponseHandlerFactory {
    private ResponseHandlerFactory() {

    }

    public static AsyncHttpResponseHandler create(ApiType apiType, Object[] args) {
        switch (apiType) {
            case DOWNLOADFILE:
                
                return new DownloadResponseHandler((ResponseListener)args[1], (File)args[2]);
            default:
                return new JsonResponseHandler((ResponseListener)args[1]);
        }
    }
}
