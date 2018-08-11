package com.uchain.concurrencydemo.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 用于标记不推荐的写法
 */
@Target(ElementType.TYPE)//指定注解标识的是类还是方法
@Retention(RetentionPolicy.SOURCE)//指定注解存在的范围
//RetentionPolicy.SOURCE,表示只在编码范围起作用,在编译时会被忽略
//RetentionPolicy.RUNTIME,参与项目的编译过程
public @interface NotRecommend {
    String value() default "";
}
