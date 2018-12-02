package com.scmoure.csvreader.mapper.line;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.testutils.TestClass;

public class ObjectLineMapperTest {

	@Test
	public void mapTest() {
		ObjectLineMapper mapper = new ObjectLineMapper.ObjectLineMapperBuilder(TestClass.class).build();

		List<String> values = Arrays.asList("outer object name", "10", "inner object name", "45");
		TestClass mappedObject = (TestClass) mapper.apply(values);

		Assert.assertNotNull("Result object is null", mappedObject);
		Assert.assertNotNull("Null field : name", mappedObject.getName());
		Assert.assertNotNull("Null field : age", mappedObject.getAge());
		Assert.assertNotNull("Null field : innerObject", mappedObject.getInnerObject());
		Assert.assertNotNull("Null field : innerObject.innerName",
				mappedObject.getInnerObject().getInnerName());
		Assert.assertNotNull("Null field : innerObject.innerAge",
				mappedObject.getInnerObject().getInnerAge());
	}

	@Test
	public void buildMapperTest() {
		ObjectLineMapper mapper = new ObjectLineMapper.ObjectLineMapperBuilder(TestClass.class).build();

		Assert.assertNotNull(mapper);
	}
}
