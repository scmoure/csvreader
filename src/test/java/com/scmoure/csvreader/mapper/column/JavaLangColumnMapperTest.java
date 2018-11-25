package com.scmoure.csvreader.mapper.column;

import org.junit.Assert;
import org.junit.Test;

public class JavaLangColumnMapperTest {

	@Test
	public void wrapperTest() {
		JavaLangColumnMapper mapper = new JavaLangColumnMapper(Integer.class);

		Integer result = (Integer) mapper.apply("3");

		Assert.assertEquals("Not the expected value", Integer.valueOf(3), result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void exceptionTest() {
		JavaLangColumnMapper mapper = new JavaLangColumnMapper(Integer.class);

		mapper.apply("a");

		Assert.fail("A NumberFormatException should be thrown");
	}
}
