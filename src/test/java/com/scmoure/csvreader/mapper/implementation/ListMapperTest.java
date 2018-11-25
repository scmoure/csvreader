package com.scmoure.csvreader.mapper.implementation;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.mapper.implementation.ListLineMapper;

public class ListMapperTest {

	@Test
	public void mapTest() {
		String[] values = { "1", "2", "3" };
		List<Integer> indexes = Arrays.asList(0, 1, 2);
		ListLineMapper mapper = new ListLineMapper.MapperBuilder(indexes, Integer.class).build();

		List<Integer> result = (List<Integer>) mapper.apply(values);

		List<Integer> expected = Arrays.asList(1, 2, 3);

		Assert.assertEquals("Not expected result. Expected [1, 2, 3]", expected, result);
	}
}
