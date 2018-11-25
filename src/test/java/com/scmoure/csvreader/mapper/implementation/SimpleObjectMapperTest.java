package com.scmoure.csvreader.mapper.implementation;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.mapper.column.SingleColumnMapper;

public class SimpleObjectMapperTest {

	@Test
	public void mapTest() {

		SingleColumnMapper mapper = new SingleColumnMapper(Integer.class, 2);

		String[] values = { "1", "2", "3" };

		Integer result = (Integer) mapper.apply(values);

		Assert.assertEquals("Not the expected result. Expected 3", (Integer) 3, result);
	}

	@Test
	public void mapPrimitiveTest() {

		SingleColumnMapper mapper = new SingleColumnMapper(int.class, 2);

		String[] values = { "1", "2", "3" };

		int result = (int) mapper.apply(values);

		Assert.assertEquals("Not the expected result. Expected 3", 3, result);
	}
}
