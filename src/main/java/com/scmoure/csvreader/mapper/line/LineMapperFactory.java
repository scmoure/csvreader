package com.scmoure.csvreader.mapper.line;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.scmoure.csvreader.annotations.CSVColumn;
import com.scmoure.csvreader.annotations.CSVObject;
import com.scmoure.csvreader.annotations.CSVObjectList;

public class LineMapperFactory {

	public static LineMapper getInstance(Class<?> targetClass) {
		return new ObjectLineMapper.ObjectLineMapperBuilder(targetClass).build();
	}

	static LineMapper getInstance(Field field) {
		LineMapper instance = null;

		if (field.isAnnotationPresent(CSVColumn.class)) {
			instance = new SingleColumnLineMapper(field.getType(),
					field.getAnnotation(CSVColumn.class).column());
		} else if (field.isAnnotationPresent(CSVObject.class)) {
			int columnOffset = field.getAnnotation(CSVObject.class).startingColumn();
			instance =
					new ObjectLineMapper.ObjectLineMapperBuilder(field.getType()).columnOffset(columnOffset)
							.build();
		} else if (field.isAnnotationPresent(CSVObjectList.class)) {
			ParameterizedType paremterizedType = (ParameterizedType) field.getGenericType();
			Class<?> elementType = (Class<?>) paremterizedType.getActualTypeArguments()[0];
			List<Integer> columns = IntStream
					.rangeClosed(field.getAnnotation(CSVObjectList.class).startingColumn(),
							field.getAnnotation(CSVObjectList.class).endingColumn())
					.boxed()
					.collect(Collectors.toList());
			instance = new ListLineMapper.MapperBuilder(columns, elementType).build();
		}

		return instance;
	}

}
