package com.scmoure.csvreader.mapper.line;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListLineMapper implements LineMapper
{

    private LineMapper elementMapper;

    private List<Integer> columnIndexes;

    private Function<List<String>, List<String>> prepareValues;

    private ListLineMapper(ListLineMapperBuilder builder)
    {
        this.columnIndexes = builder.columnIndexes;
        this.prepareValues = builder.prepareValues;
        this.elementMapper = builder.elementMapper;
    }

    @Override
    public List<?> apply(List<String> rawValues)
    {
        final List<String> values = prepareValues.apply(rawValues);
        return columnIndexes.stream().map(i -> values.subList(i, values.size())).map(elementMapper)
            .collect(Collectors.toList());
    }

    public static class ListLineMapperBuilder
    {
        private List<String> columns;

        private List<Integer> columnIndexes;

        private Function<List<String>, List<String>> prepareValues;

        private Function<String, String> rawValueMapper;

        private LineMapper elementMapper;

        public ListLineMapperBuilder(Integer startingColumn, Integer endingColumn, Integer cycle, Class<?> targetType)
        {
            this.columnIndexes = new ArrayList<>();
            int columnIndex = 0;
            while (columnIndex <= endingColumn) {
                this.columnIndexes.add(columnIndex);
                columnIndex += cycle;
            }

            this.prepareValues = Function.identity();
            this.rawValueMapper = Function.identity();
            this.elementMapper = LineMapperFactory.getInstance(targetType);
        }

        public ListLineMapperBuilder withColumnFilter(Predicate<String> columnFilter)
        {
            this.columnIndexes = this.columnIndexes.stream().filter(i -> columnFilter.test(this.columns.get(i)))
                .collect(Collectors.toList());
            return this;
        }

        public ListLineMapperBuilder withIndexFilter(Predicate<Integer> indexFilter)
        {
            this.columnIndexes = this.columnIndexes.stream().filter(indexFilter).collect(Collectors.toList());
            return this;
        }

        public ListLineMapperBuilder withIndexesToMap(List<Integer> columnIndexes)
        {
            this.columnIndexes = columnIndexes;
            return this;
        }

        public ListLineMapperBuilder withPrepareValuesFunction(Function<List<String>, List<String>> prepareValues)
        {
            this.prepareValues = prepareValues;
            return this;
        }

        public ListLineMapperBuilder withValueMapper(Function<String, String> rawValueMapper)
        {
            this.rawValueMapper = rawValueMapper;
            return this;
        }

        public ListLineMapper build()
        {
            return new ListLineMapper(this);
        }
    }
}
