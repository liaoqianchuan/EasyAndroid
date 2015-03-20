
package com.amida.easyhttp.methodhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.amida.easydb.objectsql.Select;
import com.amida.easyhttp.EasyHttpStatusCode;
import com.amida.easyhttp.IService;
import com.amida.easyhttp.ResponseListener;
import com.amida.easyhttp.cache.ResponseCache;
import com.amida.easylog.EasyLog;
import com.amida.easyreflection.EasyReflection;

/**
 * 解析service中定义的接口的annotation, 然后根据annotation中的Url等来访问网络。
 * 
 * @author liaoqianchuan
 */
public class CacheInvocationHandler implements InvocationHandler {
    private IService mNextService;

    @Override
    public Object invoke(Object proxy, final Method method, Object[] args) throws Throwable {
        if (method.getName().equals("setNextService")) {
            mNextService = (IService) args[0];
            return null;
        } else if (method.getName().equals("getNextService")) {
            return mNextService;
        }

        validateService(proxy, args);

        IService service = (IService) proxy;
        final ResponseListener responseListener = (ResponseListener) args[1];

        Object request = args[0];

        MethodInfo methodInfo = MethodInfoCache.getInstance().getMethodInfo(proxy.getClass(), method);
        if (methodInfo == null) {
            methodInfo = MethodInfo.parseMethod(method, args);
            MethodInfoCache.getInstance().addMethodInfo(proxy.getClass(), method, methodInfo);
        }

        if (!methodInfo.getCachehandler().readCache()) {
            IService nextService = service.getNextService();
            if (nextService != null) {
                responseListener.addResponseIntercepter(methodInfo.getCachehandler());
                EasyReflection.invokeMethod(nextService, method.getName(), method.getParameterTypes(), args);
            } else {
                responseListener.onFailure(EasyHttpStatusCode.STATUS_NOCACHE, "no cache found");
            }

        }

        return null;
    }

    private void validateService(Object proxy, Object[] args) {
        // 判断args的合法性，比如是否有request和responseHandler参数
        if (!(proxy instanceof IService)) {
            throw new IllegalArgumentException("class not extends from IService");
        }

        if (args.length < 2) {
            throw new IllegalArgumentException("params must be more than 1");
        }
        if (!(args[1] instanceof ResponseListener)) {
            throw new IllegalArgumentException("the second param must be type of ResponseListener");
        }

    }

   
}
