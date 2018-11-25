package com.scmoure.csvreader.mapper.line;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.mapper.line.SingleColumnLineMapper;

public class SingleColumnLineMapperTest {

	@Test
	public void mapTest() {

		SingleColumnLineMapper mapper = new SingleColumnLineMapper(Integer.class, 2);

		String[] values = { "1", "2", "3" };

		Integer result = (Integer) mapper.apply(values);

		Assert.assertEquals("Not the expected result. Expected 3", (Integer) 3, result);
	}

	@Test
	public void mapPrimitiveTest() {

		SingleColumnLineMapper mapper = new SingleColumnLineMapper(int.class, 2);

		String[] values = { "1", "2", "3" };

		int result = (int) mapper.apply(values);

		Assert.assertEquals("Not the expected result. Expected 3", 3, result);
	}
}
