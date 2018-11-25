package com.scmoure.csvreader;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.scmoure.csvreader.mapper.LineMapper;
import com.scmoure.csvreader.mapper.implementation.LineMapperFactory;

public class CSVReader {

	private URI filePath;

	private LineMapper lineMapper;

	private Stream<String> fileStream;

	private Iterator<String> iterator;

	private String columnDelimiter;

	private CSVReader(CSVReaderBuilder builder) throws IOException {
		this.filePath = builder.filePath;
		this.openFileStream();
		this.columnDelimiter = builder.columnDelimiter;
		this.lineMapper = builder.lineMapper;
	}

	private void openFileStream() throws IOException {
		this.fileStream = Files.lines(Paths.get(this.filePath)).skip(1); // We skip the column headers'
	}

	public List<?> read() throws IOException {
		this.openFileStream();
		List<Object> output = fileStream.map(line -> line.split(this.columnDelimiter))
				.map(this.lineMapper)
				.collect(Collectors.toList());

		this.fileStream.close();

		return output;
	}

	public Object readRow() {
		Object mappedObject = null;
		if (this.iterator.hasNext()) {
			mappedObject = this.lineMapper.apply(iterator.next().split(columnDelimiter));
		} else {
			this.fileStream.close();
		}

		return mappedObject;
	}

	public static class CSVReaderBuilder {
		private static final String DEFAULT_COLUMN_DELIMITER = ";";

		private URI filePath;

		private LineMapper lineMapper;

		private String columnDelimiter;

		public CSVReaderBuilder(URI filePath, Class<?> targetType) {
			this.filePath = filePath;
			this.columnDelimiter = DEFAULT_COLUMN_DELIMITER;
			this.lineMapper = LineMapperFactory.getInstance(targetType);
		}

		public CSVReaderBuilder withColumnDelimiter(String columnDelimiter) {
			this.columnDelimiter = columnDelimiter;
			return this;
		}

		public CSVReaderBuilder withMapper(LineMapper mapper) {
			this.lineMapper = mapper;
			return this;
		}

		public CSVReader build() throws IOException {
			return new CSVReader(this);
		}
	}

}
