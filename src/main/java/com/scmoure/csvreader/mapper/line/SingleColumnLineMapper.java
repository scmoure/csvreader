package com.scmoure.csvreader.mapper.line;

import java.util.List;

import com.scmoure.csvreader.mapper.column.ColumnMapper;
import com.scmoure.csvreader.mapper.column.ColumnMapperFactory;

class SingleColumnLineMapper implements LineMapper {

	private ColumnMapper mapper;
	private Integer columnIndex;

	SingleColumnLineMapper(Class<?> targetClass) {
		this.mapper = ColumnMapperFactory.getInstance(targetClass);
		this.columnIndex = 0;
	}

	SingleColumnLineMapper(Class<?> targetClass, Integer columnIndex) {
		this.mapper = ColumnMapperFactory.getInstance(targetClass);
		this.columnIndex = columnIndex;
	}

	@Override
	public Object apply(List<String> rawValues) {
		return this.mapper.apply(rawValues.get(this.columnIndex));
	}
}
