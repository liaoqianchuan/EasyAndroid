package com.amida.easyhttp.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericUtils {
    public static Type getTClass(Class genericCls) {

        Type genType = genericCls.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType))
        {
            return  Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (!(params[0] instanceof Class)) {
            return  Object.class;
        }
        return  params[0];
    }
}
