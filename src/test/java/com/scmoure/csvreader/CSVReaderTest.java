package com.scmoure.csvreader;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.testutils.DummyClass;

public class CSVReaderTest {

	@Test
	public void readTest() {
		URI filePath = null;
		try {
			filePath = this.getClass().getResource("/csvreadertest.csv").toURI();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CSVReader reader = new CSVReader.CSVReaderBuilder(filePath, DummyClass.class).build();

		List<DummyClass> results = (List<DummyClass>) reader.read();

		Assert.assertNotNull("No lines read", results);
		Assert.assertEquals("3 lines should be read", 3, results.size());
	}
}
