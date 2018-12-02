package com.scmoure.csvreader.mapper.line;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.scmoure.csvreader.mapper.column.ColumnMapper;
import com.scmoure.csvreader.mapper.column.ColumnMapperFactory;

public class ListLineMapper implements LineMapper {

	private ColumnMapper elementMapper;

	private List<Integer> columnIndexes;

	private Function<List<String>, List<String>> prepareValues;

	private Function<String, String> rawValueMapper;

	private ListLineMapper(MapperBuilder builder) {
		this.columnIndexes = builder.columnIndexes;
		this.prepareValues = builder.prepareValues;
		this.rawValueMapper = builder.rawValueMapper;
		this.elementMapper = builder.elementMapper;
	}

	@Override
	public List<?> apply(List<String> rawValues) {
		final List<String> values = prepareValues.apply(rawValues);
		return columnIndexes.stream()
				.map(i -> values.get(i))
				.map(rawValueMapper)
				.map(v -> elementMapper.apply(v))
				.collect(Collectors.toList());
	}

	public static class MapperBuilder {
		private List<String> columns;
		private List<Integer> columnIndexes;
		private Function<List<String>, List<String>> prepareValues;
		private Function<String, String> rawValueMapper;
		private ColumnMapper elementMapper;

		public MapperBuilder(List<Integer> columnIndexes, Class<?> targetType) {
			this.columnIndexes = columnIndexes;
			this.prepareValues = Function.identity();
			this.rawValueMapper = Function.identity();
			this.elementMapper = ColumnMapperFactory.getInstance(targetType);
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

		public MapperBuilder withPrepareValuesFunction(Function<List<String>, List<String>> prepareValues) {
			this.prepareValues = prepareValues;
			return this;
		}

		public MapperBuilder withValueMapper(Function<String, String> rawValueMapper) {
			this.rawValueMapper = rawValueMapper;
			return this;
		}

		public ListLineMapper build() {
			return new ListLineMapper(this);
		}
	}
}
