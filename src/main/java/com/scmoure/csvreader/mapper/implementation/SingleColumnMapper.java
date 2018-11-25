package com.scmoure.csvreader.mapper.implementation;

import com.scmoure.csvreader.mapper.column.ColumnMapper;
import com.scmoure.csvreader.mapper.column.ColumnMapperFactory;
import com.scmoure.csvreader.mapper.line.LineMapper;

class SingleColumnMapper implements LineMapper {

	private ColumnMapper mapper;
	private Integer columnIndex;

	SingleColumnMapper(Class<?> targetClass, Integer columnIndex) {
		this.mapper = ColumnMapperFactory.getInstance(targetClass);
		this.columnIndex = columnIndex;
	}

	@Override
	public Object apply(String[] rawValues) {
		return this.mapper.apply(rawValues[this.columnIndex]);
	}
}
