package com.scmoure.csvreader.mapper.line;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.testutils.InnerTestClass;

public class ListLineMapperTest {

	@Test
	public void mapJavaLangElementListTest() {
		List<String> values = Arrays.asList("1", "2", "3");
		ListLineMapper mapper = new ListLineMapper.ListLineMapperBuilder(0, 2, 1, Integer.class).build();

		List<Integer> result = (List<Integer>) mapper.apply(values);

		List<Integer> expected = Arrays.asList(1, 2, 3);

		Assert.assertEquals("Not expected result. Expected [1, 2, 3]", expected, result);
	}

	@Test
	public void mapObjectElementListTest() {
		List<String> values = Arrays.asList("10", "object 1", "20", "object 2", "30", "object 3");

		ListLineMapper mapper =
				new ListLineMapper.ListLineMapperBuilder(0, 5, 2, InnerTestClass.class).build();

		List<InnerTestClass> result = (List<InnerTestClass>) mapper.apply(values);

		Assert.assertNotNull("Result list should not be null", result);
		Assert.assertFalse("Result list should not be empty", result.isEmpty());
		Assert.assertEquals("Result list should have three objects", 3, result.size());
		Assert.assertEquals("Not expected name value for object 1", "object 1", result.get(0).getInnerName());
		Assert.assertEquals("Not expected age value for object 1",
				Long.valueOf(10),
				result.get(0).getInnerAge());
		Assert.assertEquals("Not expected name value for object 2", "object 2", result.get(1).getInnerName());
		Assert.assertEquals("Not expected age value for object 2",
				Long.valueOf(20),
				result.get(1).getInnerAge());
		Assert.assertEquals("Not expected name value for object 3", "object 3", result.get(2).getInnerName());
		Assert.assertEquals("Not expected age value for object 3",
				Long.valueOf(30),
				result.get(2).getInnerAge());
	}
}
