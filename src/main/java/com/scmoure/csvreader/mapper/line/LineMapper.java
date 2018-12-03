package com.scmoure.csvreader.mapper.line;

import java.util.List;
import java.util.function.Function;

/**
 * Provides functionality to map a set of columns to an object
 * 
 * @author scmoure
 */
public interface LineMapper extends Function<List<String>, Object>
{

    @Override
    Object apply(List<String> values);
}
