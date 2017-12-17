package com.junit.customrunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Repeatable(TestOrder.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

	String[] params() default {};

	int value() default 0;

}
