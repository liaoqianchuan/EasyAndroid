
package com.amida.easyhttp.paramgenerator;

import com.amida.easylog.EasyLog;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class JsonParamGenerator implements ParamGenerator {

    @Override
    public RequestParams generate(Object request) {
        RequestParams params = new RequestParams();

        String jsonParams = new Gson().toJson(request);
        params.put("params", jsonParams);
        EasyLog.d("params="+ jsonParams);
        return params;
    }

}
