package com.scmoure.csvreader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.testutils.TestClass;

public class CSVReaderTest
{

    @Test
    public void readTest() throws URISyntaxException, IOException
    {
        URI filePath = null;
        filePath = this.getClass().getResource("/csvreadertest.csv").toURI();
        CSVReader reader = new CSVReader.CSVReaderBuilder(filePath, TestClass.class).build();

        List<TestClass> results = null;
        results = (List<TestClass>) reader.read();

        Assert.assertNotNull("No lines read", results);
        Assert.assertEquals("3 lines should be read", 3, results.size());
    }

}
