package com.scmoure.csvreader.mapper.column;

import com.scmoure.csvreader.mapper.ColumnMapper;

class StringColumnMapper implements ColumnMapper {

	@Override
	public String apply(String rawValue) {
		return rawValue;
	}

}
