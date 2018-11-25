package com.scmoure.csvreader.mapper.column;

class StringColumnMapper implements ColumnMapper {

	@Override
	public String apply(String rawValue) {
		return rawValue;
	}

}
