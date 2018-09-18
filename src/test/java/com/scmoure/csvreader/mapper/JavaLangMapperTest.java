package com.scmoure.csvreader.mapper;

import org.junit.Assert;
import org.junit.Test;

public class JavaLangMapperTest {

	@Test
	public void wrapperTest() {
		JavaLangMapper mapper = new JavaLangMapper(Integer.class);

		Integer result = (Integer) mapper.apply("3");

		Assert.assertEquals("Not the expected value", Integer.valueOf(3), result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void exceptionTest() {
		JavaLangMapper mapper = new JavaLangMapper(Integer.class);

		mapper.apply("a");

		Assert.fail("A NumberFormatException should be thrown");
	}
}
