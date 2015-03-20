package com.amida.easyhttp.paramgenerator;

import com.loopj.android.http.RequestParams;

public interface ParamGenerator {
    RequestParams generate(Object request);
}
