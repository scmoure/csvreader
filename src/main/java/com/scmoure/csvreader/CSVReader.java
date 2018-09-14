package com.scmoure.csvreader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class CSVReader {

	private Class<?> targetType;

	private String filePath;

	private Stream<String> fileStream;

	private Iterator<String> iterator;

	private String csvDelimiter = ";";

	private List<CSVFieldSetter> fieldSetters;

	public CSVReader(String filePath, Class<?> targetType)
			throws NoSuchFieldException, NoSuchMethodException, SecurityException, IOException {
		this.targetType = targetType;
		this.filePath = filePath;
		this.fileStream = Files.lines(Paths.get(this.filePath));
		this.iterator = fileStream.iterator();
		this.fieldSetters = this.buildFieldSetters();
	}

	public CSVReader(URL filePath, Class<?> targetType)
			throws NoSuchFieldException, NoSuchMethodException, SecurityException, IOException {
		this(filePath.getFile(), targetType);
	}

	private List<CSVFieldSetter> buildFieldSetters()
			throws NoSuchFieldException, NoSuchMethodException, SecurityException, IOException {
		List<CSVFieldSetter> csvFieldSetters = new ArrayList<>();

		List<String> columns = this.getColumns();
		for (String column : columns) {
			CSVFieldSetter csvFieldSetter = new CSVFieldSetter(column, this.targetType);
			csvFieldSetters.add(csvFieldSetter);
		}

		return csvFieldSetters;
	}

	private List<String> getColumns() throws IOException {
		String firstLine = null;
		if (iterator.hasNext()) {
			firstLine = iterator.next();
		}

		return Arrays.asList(firstLine.split(csvDelimiter));
	}

	public List<?> read() throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException,
			SecurityException, InstantiationException, IllegalAccessException, IOException {

		List<Object> output = new ArrayList<>();
		while (this.iterator.hasNext()) {
			Object mappedObject = this.mapObject(iterator.next());
			output.add(mappedObject);
		}

		this.fileStream.close();

		return output;
	}

	public Object readRow() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Object mappedObject = null;
		if (this.iterator.hasNext()) {
			mappedObject = this.mapObject(iterator.next());
		} else {
			this.fileStream.close();
		}

		return mappedObject;
	}

	private Object mapObject(String inputString) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		Object targetObject = targetType.getConstructor().newInstance();

		String[] values = inputString.split(csvDelimiter);
		CSVFieldSetter fieldSetter = null;
		String value = null;
		for (int i = 0; i < values.length; i++) {
			value = values[i];
			fieldSetter = this.fieldSetters.get(i);
			fieldSetter.setValue(value, targetObject);
		}

		return targetObject;
	}

}
