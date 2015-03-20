
package com.amida.easyhttp;

import java.lang.reflect.Proxy;

import android.content.Context;

import com.amida.easyhttp.methodhandler.CacheInvocationHandler;
import com.amida.easyhttp.methodhandler.DummyInvocationHandler;
import com.amida.easyhttp.methodhandler.HttpInvocationHandler;

public class ServiceFactory {
    public enum ServiceType {
        NET, DUMMY, CACHE
    }

    public static <T> T create(Context context, ServiceType serviceType, Class<T> iService) {
        switch (serviceType) {
            case CACHE:
                IService cacheService = (IService) Proxy.newProxyInstance(iService.getClassLoader(), 
                        new Class<?>[] {iService}, new CacheInvocationHandler());
                IService netService = (IService) ServiceFactory.create(context, ServiceType.NET, iService);
                
                cacheService.setNextService(netService);
                return (T) cacheService;
            case NET:
                return (T) Proxy.newProxyInstance(iService.getClassLoader(), 
                        new Class<?>[] {iService}, new HttpInvocationHandler());
            default:
                return (T) Proxy.newProxyInstance(iService.getClassLoader(), 
                        new Class<?>[] {iService}, new DummyInvocationHandler(context));
        }

    }
}
