package com.scmoure.csvreader.mapper.implementation;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.mapper.implementation.StringMapper;

public class StringMapperTest {

	@Test
	public void mapTest() {
		StringMapper mapper = new StringMapper();

		String result = mapper.apply("test");

		Assert.assertEquals("Not the expected value. Expected \"test\"", "test", result);
	}
}
