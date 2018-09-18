package com.scmoure.csvreader.mapper;

import com.scmoure.csvreader.AtomicMapper;
import com.scmoure.csvreader.LineMapper;

class SimpleObjectMapper implements LineMapper {

	private AtomicMapper mapper;
	private Integer columnIndex;

	SimpleObjectMapper(Class<?> targetClass, Integer columnIndex) {
		this.mapper = AtomicMapperFactory.getInstance(targetClass);
		this.columnIndex = columnIndex;
	}

	@Override
	public Object apply(String[] rawValues) {
		return this.mapper.apply(rawValues[this.columnIndex]);
	}
}
