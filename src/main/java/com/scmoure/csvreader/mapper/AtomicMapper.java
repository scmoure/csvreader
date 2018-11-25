package com.scmoure.csvreader.mapper;

import java.util.function.Function;

public interface AtomicMapper extends Function<String, Object> {

	@Override
	Object apply(String value);
}
