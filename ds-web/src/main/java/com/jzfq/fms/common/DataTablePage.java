package com.jzfq.fms.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhishuo on 11/9/16.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataTablePage {
    /**
     * 用于绑定的请求参数名字
     */
    String value() default "";

    /**
     * 是否必须，默认是
     */
    boolean required() default true;
}
