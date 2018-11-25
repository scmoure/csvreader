package com.scmoure.csvreader.mapper.column;

import com.scmoure.csvreader.mapper.LineMapper;

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
