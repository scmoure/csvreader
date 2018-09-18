package com.scmoure.csvreader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.testutils.DummyClass;

public class CSVReaderTest {

	@Test
	public void readTest() throws URISyntaxException, IOException {
		URI filePath = null;
		filePath = this.getClass().getResource("/csvreadertest.csv").toURI();
		CSVReader reader = new CSVReader.CSVReaderBuilder(filePath, DummyClass.class).build();

		List<DummyClass> results = null;
		results = (List<DummyClass>) reader.read();

		Assert.assertNotNull("No lines read", results);
		Assert.assertEquals("3 lines should be read", 3, results.size());
	}
}
