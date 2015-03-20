
package com.amida.easyhttp.paramgenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import com.amida.easyreflection.EasyReflection;
import com.loopj.android.http.RequestParams;

public class DefaultParamGenerator implements ParamGenerator {

    @Override
    public RequestParams generate(Object request) {
        RequestParams params = new RequestParams();

        ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(request.getClass().getDeclaredFields()));
        if (fields != null) {
            for (Field field:fields) {
                String fieldName = field.getName();
                Object value = EasyReflection.getFieldValue(request, fieldName);
                params.put(fieldName, value);
            }
        }
        
        return params;
    }

}
