package com.scmoure.csvreader.mapper.implementation;

import com.scmoure.csvreader.mapper.AtomicMapper;

class StringMapper implements AtomicMapper {

	@Override
	public String apply(String rawValue) {
		return rawValue;
	}

}
