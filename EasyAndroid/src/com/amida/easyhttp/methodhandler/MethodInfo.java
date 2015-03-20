package com.amida.easyhttp.methodhandler;

import java.lang.reflect.Method;

import com.amida.easyhttp.ResponseListener;
import com.amida.easyhttp.annotations.EasyHttp;
import com.amida.easyhttp.methodhandler.cache.CacheHandler;
import com.amida.easyhttp.methodhandler.cache.CacheHandlerFactory;
import com.amida.easyhttp.methodhandler.dumy.DummyHandler;
import com.amida.easyhttp.methodhandler.dumy.DummyHandlerFactory;
import com.amida.easyhttp.methodhandler.http.ResponseHandlerFactory;
import com.amida.easyhttp.paramgenerator.ParamGenerator;
import com.amida.easyhttp.paramgenerator.ParamGeneratorFactory;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MethodInfo {
    private String mUrl;
    private ParamGenerator ParamGenerator;
    private AsyncHttpResponseHandler mResponseHandler;
    private CacheHandler mCachehandler;
    private DummyHandler mDummyHandler;
    
    public String getUrl() {
        return mUrl;
    }
    public void setUrl(String url) {
        mUrl = url;
    }
    
    public ParamGenerator getParamGenerator() {
        return ParamGenerator;
    }
    public void setParamGenerator(ParamGenerator paramGenerator) {
        ParamGenerator = paramGenerator;
    }
    
    public static MethodInfo parseMethod(Method method, Object[] args) {
        EasyHttp easyRequestAnnotation = method.getAnnotation(EasyHttp.class);
        
        if (easyRequestAnnotation == null) {
            throw new IllegalArgumentException("request url can not be null");
        }
        
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setUrl(easyRequestAnnotation.url());
        methodInfo.setParamGenerator(ParamGeneratorFactory.create(easyRequestAnnotation.paramsType()));
        methodInfo.setResponseHandler(ResponseHandlerFactory.create(easyRequestAnnotation.apiType(), args));
        methodInfo.setCachehandler(CacheHandlerFactory.create(easyRequestAnnotation.apiType(), method.getName(), (ResponseListener)args[1]));
        methodInfo.setDummyHandler(DummyHandlerFactory.create(easyRequestAnnotation.apiType(), easyRequestAnnotation.dummyData()));
        
        return methodInfo;
    }
    public AsyncHttpResponseHandler getResponseHandler() {
        return mResponseHandler;
    }
    public void setResponseHandler(AsyncHttpResponseHandler responseHandler) {
        mResponseHandler = responseHandler;
    }
    public CacheHandler getCachehandler() {
        return mCachehandler;
    }
    public void setCachehandler(CacheHandler cachehandler) {
        mCachehandler = cachehandler;
    }
    public DummyHandler getDummyHandler() {
        return mDummyHandler;
    }
    public void setDummyHandler(DummyHandler dummyHandler) {
        mDummyHandler = dummyHandler;
    }
   
}
