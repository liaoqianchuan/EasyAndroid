
package com.amida.easyhttp.methodhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.amida.easyhttp.ResponseListener;
import com.amida.easylog.EasyLog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 解析service中定义的接口的annotation, 然后根据annotation中的Url等来访问网络。
 * @author liaoqianchuan
 *
 */
public class HttpInvocationHandler implements InvocationHandler {

    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        validateService(args);

        Object request = args[0];

        MethodInfo methodInfo = MethodInfoCache.getInstance().getMethodInfo(proxy.getClass(), method);
        if (methodInfo == null) {
            EasyLog.d("did not find methodinfo in cache");
            methodInfo = MethodInfo.parseMethod(method, args);
            MethodInfoCache.getInstance().addMethodInfo(proxy.getClass(), method, methodInfo);
        } else {
            EasyLog.d("find methodinfo in cache");
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(methodInfo.getUrl(), methodInfo.getParamGenerator().generate(request), methodInfo.getResponseHandler());

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

}
