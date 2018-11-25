package com.scmoure.csvreader.mapper.column;

import org.junit.Assert;
import org.junit.Test;

public class StringColumnMapperTest {

	private StringColumnMapper mapper = new StringColumnMapper();

	@Test
	public void mapTest() {

		String result = mapper.apply("test");

		Assert.assertEquals("Not the expected value. Expected \"test\"", "test", result);
	}
}
