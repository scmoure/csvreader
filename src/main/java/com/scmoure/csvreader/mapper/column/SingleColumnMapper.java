package com.scmoure.csvreader.mapper.column;

import com.scmoure.csvreader.mapper.ColumnMapper;
import com.scmoure.csvreader.mapper.LineMapper;
import com.scmoure.csvreader.mapper.implementation.AtomicMapperFactory;

class SingleColumnMapper implements LineMapper {

	private ColumnMapper mapper;
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
