package com.scmoure.csvreader.mapper;

import java.util.function.Function;

public interface LineMapper extends Function<String[], Object> {

	@Override
	Object apply(String[] values);
}
