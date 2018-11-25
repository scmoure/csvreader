package com.scmoure.csvreader.mapper.line;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.mapper.line.ListLineMapper;

public class ListLineMapperTest {

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
