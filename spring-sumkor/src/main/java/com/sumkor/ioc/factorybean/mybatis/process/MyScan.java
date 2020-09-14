package com.sumkor.ioc.factorybean.mybatis.process;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Sumkor
 * @since 2020/9/15
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyScan {

	String value() default "";
}
