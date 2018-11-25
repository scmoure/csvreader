package com.scmoure.csvreader.mapper.implementation;

import com.scmoure.csvreader.mapper.AtomicMapper;
import com.scmoure.csvreader.mapper.LineMapper;

class SingleColumnMapper implements LineMapper {

	private AtomicMapper mapper;
	private Integer columnIndex;

	SingleColumnMapper(Class<?> targetClass, Integer columnIndex) {
		this.mapper = AtomicMapperFactory.getInstance(targetClass);
		this.columnIndex = columnIndex;
	}

	@Override
	public Object apply(String[] rawValues) {
		return this.mapper.apply(rawValues[this.columnIndex]);
	}
}
