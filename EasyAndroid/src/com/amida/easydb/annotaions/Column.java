
package com.amida.easydb.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 申明这是数据库表中的某列，可以设置列名，不设置列名则自动取得属性名字名作为列名。 e.g.
 * 
 * @Column(name="address",length=30,notNull=true)
 * @author liaoqianchuan
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    public String name();

    public int length() default -1;

    public boolean notNull() default false;

}
