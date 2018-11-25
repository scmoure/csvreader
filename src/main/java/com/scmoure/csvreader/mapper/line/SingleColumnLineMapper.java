package com.scmoure.csvreader.mapper.line;

import com.scmoure.csvreader.mapper.column.ColumnMapper;
import com.scmoure.csvreader.mapper.column.ColumnMapperFactory;

class SingleColumnLineMapper implements LineMapper {

	private ColumnMapper mapper;
	private Integer columnIndex;

	SingleColumnLineMapper(Class<?> targetClass, Integer columnIndex) {
		this.mapper = ColumnMapperFactory.getInstance(targetClass);
		this.columnIndex = columnIndex;
	}

	@Override
	public Object apply(String[] rawValues) {
		return this.mapper.apply(rawValues[this.columnIndex]);
	}
}
