package com.scmoure.csvreader.mapper;

import com.scmoure.csvreader.AtomicMapper;

public class StringMapper implements AtomicMapper {

	@Override
	public String apply(String rawValue) {
		return rawValue;
	}

}
