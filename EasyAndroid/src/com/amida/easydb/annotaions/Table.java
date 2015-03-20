
package com.amida.easydb.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 申明这数据库表的表名，不设置表名则自动取得类名作为表名。 e.g. @Table(name="students")
 * 
 * @author liaoqianchuan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    public String name();
}
