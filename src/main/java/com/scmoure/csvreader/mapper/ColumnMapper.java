package com.scmoure.csvreader.mapper;

import java.util.function.Function;

import com.scmoure.csvreader.annotations.CSVColumn;

/**
 * Provides functionality to the {@link CSVColumn} annotation
 * 
 * @author scmoure
 *
 */
public interface ColumnMapper extends Function<String, Object> {

	/**
	 * Maps a single column string value to an instance of the target object
	 */
	@Override
	Object apply(String value);
}
