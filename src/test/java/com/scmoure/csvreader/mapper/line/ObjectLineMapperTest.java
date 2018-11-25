package com.scmoure.csvreader.mapper.line;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.testutils.DummyClass;

public class ObjectLineMapperTest {

	@Test
	public void mapTest() {
		ObjectLineMapper mapper = new ObjectLineMapper.ObjectLineMapperBuilder(DummyClass.class).build();

		String[] values = { "3", "45", "John Doe", "1", "2", "3", "4", "5" };
		DummyClass result = (DummyClass) mapper.apply(values);

		Assert.assertNotNull("Result object is null", result);
		Assert.assertNotNull("Null field : name", result.getName());
		Assert.assertNotNull("Null field : age", result.getAge());
		Assert.assertNotNull("Null field : sons", result.getSons());
		Assert.assertNotNull("Null field : failures", result.getFailures());
	}

	@Test
	public void buildMapperTest() {
		ObjectLineMapper mapper = new ObjectLineMapper.ObjectLineMapperBuilder(DummyClass.class).build();

		Assert.assertNotNull(mapper);
	}
}
