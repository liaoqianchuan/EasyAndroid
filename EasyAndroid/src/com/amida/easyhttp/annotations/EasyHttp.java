
package com.amida.easyhttp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明request的相关信息，如URL,传参数方式等
 * 
 * @author liaoqianchuan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyHttp {
    public ApiType apiType() default ApiType.DEFAULT;

    public enum ApiType {
        DEFAULT,
        DOWNLOADFILE
    }
    
    /**
     * 访问的服务器地址
     * @return
     */
    public String url();

    public ParamsType paramsType() default ParamsType.DEFAULT;

    /**
     * 几种生产参数的方式
     * 
     * @author liaoqianchuan
     */
    public enum ParamsType {
        /**
         * 例如: APIADDRESS?userId=dd&userAge=13
         */
        DEFAULT,
        /**
         * 将所有参数转换成json，然后设置给params字段 例如: params={userid=dd,userage=13}
         */
        JSON
    }

    /**
     * 当为普通的网络请求时，假数据可设置成需要放回的字符串。当为下载请求时，假数据设置为assets文件夹中的文件名
     * 
     * @return
     */
    public String dummyData() default "";
    
}
