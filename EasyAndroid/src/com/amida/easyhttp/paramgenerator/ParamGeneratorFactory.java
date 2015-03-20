
package com.amida.easyhttp.paramgenerator;

import com.amida.easyhttp.annotations.EasyHttp.ParamsType;

/**
 * 提供几种基本的参数生产方式，例如，1.将对象中的每个属性及其值设置到params中 2.将对象转换成json，参数为params=json
 * 
 * @author liaoqianchuan
 */
public final class ParamGeneratorFactory {
    private ParamGeneratorFactory() {

    }

    public static ParamGenerator create(ParamsType paramsType) {
        switch (paramsType) {
            case JSON:
                return new JsonParamGenerator();
            default:
                return new DefaultParamGenerator();
        }
    }
}
