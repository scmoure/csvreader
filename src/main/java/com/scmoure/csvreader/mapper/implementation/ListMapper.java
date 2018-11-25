package com.scmoure.csvreader.mapper.implementation;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.scmoure.csvreader.mapper.AtomicMapper;
import com.scmoure.csvreader.mapper.LineMapper;

public class ListMapper implements LineMapper {

	private AtomicMapper elementMapper;

	private List<Integer> columnIndexes;

	private Function<String[], String[]> prepareValues;

	private Function<String, String> rawValueMapper;

	private ListMapper(MapperBuilder builder) {
		this.columnIndexes = builder.columnIndexes;
		this.prepareValues = builder.prepareValues;
		this.rawValueMapper = builder.rawValueMapper;
		this.elementMapper = builder.elementMapper;
	}

	@Override
	public List<?> apply(String[] rawValues) {
		final String[] values = prepareValues.apply(rawValues);
		return columnIndexes.stream()
				.map(i -> values[i])
				.map(rawValueMapper)
				.map(v -> elementMapper.apply(v))
				.collect(Collectors.toList());
	}

	public static class MapperBuilder {
		private List<String> columns;
		private List<Integer> columnIndexes;
		private Function<String[], String[]> prepareValues;
		private Function<String, String> rawValueMapper;
		private AtomicMapper elementMapper;

		public MapperBuilder(List<Integer> columnIndexes, Class<?> targetType) {
			this.columnIndexes = columnIndexes;
			this.prepareValues = Function.identity();
			this.rawValueMapper = Function.identity();
			this.elementMapper = AtomicMapperFactory.getInstance(targetType);
		}

		public MapperBuilder withColumnFilter(Predicate<String> columnFilter) {
			this.columnIndexes = this.columnIndexes.stream().filter(i -> {
				return columnFilter.test(this.columns.get(i));
			}).collect(Collectors.toList());
			return this;
		}

		public MapperBuilder withIndexFilter(Predicate<Integer> indexFilter) {
			this.columnIndexes = this.columnIndexes.stream().filter(indexFilter).collect(Collectors.toList());
			return this;
		}

		public MapperBuilder withIndexesToMap(List<Integer> columnIndexes) {
			this.columnIndexes = columnIndexes;
			return this;
		}

		public MapperBuilder withPrepareValuesFunction(Function<String[], String[]> prepareValues) {
			this.prepareValues = prepareValues;
			return this;
		}

		public MapperBuilder withValueMapper(Function<String, String> rawValueMapper) {
			this.rawValueMapper = rawValueMapper;
			return this;
		}

		public ListMapper build() {
			return new ListMapper(this);
		}
	}
}