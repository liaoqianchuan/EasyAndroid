
package com.amida.easyhttp.methodhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.content.Context;

import com.amida.easyhttp.ResponseListener;

public class DummyInvocationHandler implements InvocationHandler {
    private Context mContext;
    public DummyInvocationHandler(Context context){
        mContext = context;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        validateService(args);

        MethodInfo methodInfo = MethodInfoCache.getInstance().getMethodInfo(proxy.getClass(), method);
        if (methodInfo == null) {
            methodInfo = MethodInfo.parseMethod(method, args);
            MethodInfoCache.getInstance().addMethodInfo(proxy.getClass(), method, methodInfo);
        }
        methodInfo.getDummyHandler().onLoadDummyData(mContext, methodInfo, args);

        return null;
    }

    private void validateService(Object[] args) {
        // 判断args的合法性，比如是否有request和responseHandler参数
        if (args.length < 2) {
            throw new IllegalArgumentException("params must be more than 1");
        }
        if (!(args[1] instanceof ResponseListener)) {
            throw new IllegalArgumentException("the second param must be type of ResponseListener");
        }
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

}
