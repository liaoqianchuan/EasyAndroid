package com.amida.easyhttp.methodhandler;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用于存放已经访问过的Service接口方法信息，从而加快下次的访问速度。
 * @author liaoqianchuan
 *
 */
public class MethodInfoCache {
    private Map<Class<?>, Map<Method, MethodInfo>> mCache = new LinkedHashMap<Class<?>, Map<Method, MethodInfo>>();
    
    private static MethodInfoCache mMethodInfoCache;
    private MethodInfoCache() {
    }

    public static MethodInfoCache getInstance() {
        if (mMethodInfoCache == null) {
            mMethodInfoCache = new MethodInfoCache();
        }
        return mMethodInfoCache;
    }
    
    public MethodInfo getMethodInfo(Class<?> serviceCls, Method method) {
        Map<Method, MethodInfo> serviceMethods = mCache.get(serviceCls);
        if (serviceMethods == null) {
            return null;
        }
        
        return serviceMethods.get(method);
    }
    
    public void addMethodInfo(Class<?> serviceCls, Method method, MethodInfo methodInfo) {
        Map<Method, MethodInfo> serviceMethods = mCache.get(serviceCls);
        if (serviceMethods == null) {
            serviceMethods = new LinkedHashMap<Method, MethodInfo>();
        } 
        serviceMethods.put(method, methodInfo);
        
        mCache.put(serviceCls, serviceMethods);
    }
}
