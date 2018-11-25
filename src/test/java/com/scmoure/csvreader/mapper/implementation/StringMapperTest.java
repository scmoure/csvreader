package com.scmoure.csvreader.mapper.implementation;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.mapper.column.StringColumnMapper;

public class StringMapperTest {

	@Test
	public void mapTest() {
		StringColumnMapper mapper = new StringColumnMapper();

		String result = mapper.apply("test");

		Assert.assertEquals("Not the expected value. Expected \"test\"", "test", result);
	}
}
