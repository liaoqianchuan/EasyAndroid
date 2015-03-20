package com.amida.easyhttp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;


public abstract  class ResponseListener<T> {
    private ArrayList<ResponseIntercepter> mResponseIntercepters = new ArrayList<ResponseIntercepter>();
    public Class<T> getTClass() {

        Type genType = getClass().getGenericSuperclass();

        if (!(genType instanceof ParameterizedType))
        {
            return (Class<T>) Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (!(params[0] instanceof Class)) {
            return (Class<T>) Object.class;
        }
        return (Class<T>) params[0];
    }
    
    public void onSuccess(T response) {
        for (ResponseIntercepter responseIntercepter : mResponseIntercepters) {
            responseIntercepter.onSuccess(response);
        }
        
    }
    abstract public void onFailure(int errorCode, String errorMessage);
    abstract public void onProgress(int bytesWritten, int totalSize);
    
    public void addResponseIntercepter(ResponseIntercepter responseIntercepter) {
        mResponseIntercepters.add(responseIntercepter);
    }
}
