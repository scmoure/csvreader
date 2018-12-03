package com.scmoure.csvreader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.scmoure.csvreader.mapper.line.LineMapper;
import com.scmoure.csvreader.mapper.line.ListLineMapper;
import com.scmoure.csvreader.mapper.line.ObjectLineMapper;
import com.scmoure.csvreader.testutils.ComplexCSVObject;
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

    @Test
    public void customMapperTest() throws URISyntaxException, IOException
    {
        URI filePath = null;
        filePath = this.getClass().getResource("/datos17.csv").toURI();

        List<Integer> columnIndexes = IntStream.rangeClosed(7, 61).boxed().collect(Collectors.toList());
        LineMapper failuresListMapper =
            new ListLineMapper.MapperBuilder(columnIndexes, Float.class).withIndexFilter(i -> i % 2 == 1).build();
        LineMapper mapper = new ObjectLineMapper.ObjectLineMapperBuilder(ComplexCSVObject.class)
            .withFieldMapper("dailyValues", failuresListMapper).build();
        CSVReader reader = new CSVReader.CSVReaderBuilder(filePath, ComplexCSVObject.class).withMapper(mapper).build();

        List<ComplexCSVObject> results = null;
        results = (List<ComplexCSVObject>) reader.read();

        Assert.assertNotNull("No lines read", results);
        Assert.assertEquals("1812 lines should be read", 1812, results.size());
    }

    @Test
    public void complexListMapperTest() throws URISyntaxException, IOException
    {
        URI filePath = null;
        filePath = this.getClass().getResource("/datos17.csv").toURI();

        List<Integer> columnIndexes =
            IntStream.rangeClosed(7, 67).boxed().filter(i -> i % 2 == 1).collect(Collectors.toList());
        Function<List<String>, List<String>> prepare = rawValues -> {
            List<String> values = new ArrayList<>(rawValues);
            for (Integer i : columnIndexes) {
                String rawValue = null;
                String control = null;
                try {
                    rawValue = rawValues.get(i);
                    control = rawValues.get(i + 1);
                    if (!"V".equals(control)) {
                        rawValue = null;
                    }
                } catch (IndexOutOfBoundsException ex) {
                    rawValue = null;
                    values.add(null); // The value that should exist
                    values.add(null); // The control that should exist
                } finally {
                    values.set(i, rawValue);
                }
            }
            return values;
        };
        LineMapper failuresListMapper = new ListLineMapper.MapperBuilder(columnIndexes, Float.class)
            .withIndexFilter(i -> i % 2 == 1).withPrepareValuesFunction(prepare).build();
        LineMapper mapper = new ObjectLineMapper.ObjectLineMapperBuilder(ComplexCSVObject.class)
            .withFieldMapper("dailyValues", failuresListMapper).build();
        CSVReader reader = new CSVReader.CSVReaderBuilder(filePath, ComplexCSVObject.class).withMapper(mapper).build();

        List<ComplexCSVObject> results = null;
        results = (List<ComplexCSVObject>) reader.read();

        Assert.assertNotNull("No lines read", results);
        Assert.assertEquals("1812 lines should be read", 1812, results.size());
    }
}
