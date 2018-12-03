package com.scmoure.csvreader.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that the annotated field of a class is mapped with a single column of a CSV file
 * 
 * @author scmoure
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVColumn
{

    /**
     * The column position (starting at 0) in the CSV file that maps the target field
     * 
     * @return
     */
    public int column() default 0;

}
