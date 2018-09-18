package com.scmoure.csvreader.mapper;

import org.junit.Assert;
import org.junit.Test;

public class SimpleObjectMapperTest {

	@Test
	public void mapTest() {

		SimpleObjectMapper mapper = new SimpleObjectMapper(Integer.class, 2);

		String[] values = { "1", "2", "3" };

		Integer result = (Integer) mapper.apply(values);

		Assert.assertEquals("Not the expected result. Expected 3", (Integer) 3, result);
	}

	@Test
	public void mapPrimitiveTest() {

		SimpleObjectMapper mapper = new SimpleObjectMapper(int.class, 2);

		String[] values = { "1", "2", "3" };

		int result = (int) mapper.apply(values);

		Assert.assertEquals("Not the expected result. Expected 3", 3, result);
	}
}
