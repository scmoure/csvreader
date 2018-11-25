package com.scmoure.csvreader.mapper.column;

import org.junit.Assert;
import org.junit.Test;

public class JavaLangColumnMapperTest {

	private JavaLangColumnMapper mapper = new JavaLangColumnMapper(Integer.class);;

	@Test
	public void primitiveTest() {
		int result = (int) mapper.apply("3");

		Assert.assertEquals("Not the expected value", 3, result);
	}

	@Test
	public void wrapperTest() {
		Integer result = (Integer) mapper.apply("3");

		Assert.assertEquals("Not the expected value", Integer.valueOf(3), result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void exceptionTest() {
		mapper.apply("a");

		Assert.fail("A NumberFormatException should be thrown");
	}
}
