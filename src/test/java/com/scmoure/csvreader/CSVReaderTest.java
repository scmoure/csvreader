package com.scmoure.csvreader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CSVReaderTest {

	@Test
	public void testRead()
			throws NoSuchMethodException, SecurityException, IOException, IllegalArgumentException,
			InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
		URL filePath = this.getClass().getResource("/csvreadertest.csv");
		CSVReader reader = new CSVReader(filePath, CSVReaderTestTarget.class);

		List<CSVReaderTestTarget> results = (List<CSVReaderTestTarget>) reader.read();

		Assert.assertNotNull("No lines read", results);
		Assert.assertEquals("2 lines should be read", 2, results.size());
	}

	@Test
	public void testReadLine()
			throws NoSuchMethodException, SecurityException, IOException, IllegalArgumentException,
			InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
		URL filePath = this.getClass().getResource("/csvreadertest.csv");
		CSVReader reader = new CSVReader(filePath, CSVReaderTestTarget.class);

		CSVReaderTestTarget row = (CSVReaderTestTarget) reader.readRow();

		Assert.assertNotNull("No line read", row);
		Assert.assertEquals("Expected line not read", 1, row.getColumn1());

		row = (CSVReaderTestTarget) reader.readRow();
		Assert.assertNotNull("No line read", row);
		Assert.assertEquals("Expected line not read", 2, row.getColumn1());
	}
}
