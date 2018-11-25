package com.scmoure.csvreader.mapper.line;

import java.util.function.Function;

/**
 * Provides functionality to map a set of columns to an object
 * 
 * @author scmoure
 *
 */
public interface LineMapper extends Function<String[], Object> {

	@Override
	Object apply(String[] values);
}
