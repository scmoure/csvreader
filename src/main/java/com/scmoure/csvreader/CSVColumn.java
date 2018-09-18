package com.scmoure.csvreader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVColumn {
	public String name() default "";

	public int startingColumn() default 0;

	public int endingColumn() default 0;
}
