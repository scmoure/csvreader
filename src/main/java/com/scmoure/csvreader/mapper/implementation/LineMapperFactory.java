package com.scmoure.csvreader.mapper.implementation;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.scmoure.csvreader.annotations.CSVObject;
import com.scmoure.csvreader.mapper.LineMapper;

public class LineMapperFactory {

	public static LineMapper getInstance(Class<?> targetClass) {
		return new ComplexObjectMapper.ComplexObjectMapperBuilder(targetClass).build();
	}

	static LineMapper getInstance(Field field) {
		LineMapper instance = null;

		int startingColumn = field.getAnnotation(CSVObject.class).startingColumn();
		int endingColumn = field.getAnnotation(CSVObject.class).endingColumn();
		if (endingColumn < startingColumn) {
			endingColumn = startingColumn;
		}
		List<Integer> columns =
				IntStream.rangeClosed(startingColumn, endingColumn).boxed().collect(Collectors.toList());

		Class<?> fieldClass = field.getType();

		if (List.class.equals(fieldClass)) {
			ParameterizedType paremterizedType = (ParameterizedType) field.getGenericType();
			Class<?> elementType = (Class<?>) paremterizedType.getActualTypeArguments()[0];
			instance = new ListMapper.MapperBuilder(columns, elementType).build();
//		} else if (Array.class.equals(targetType)) {
//			instance = new ArrayMapper(targetType, columns);
		} else if (fieldClass.isPrimitive() || isSimpleType(fieldClass)) {
			instance = new SingleColumnMapper(fieldClass, columns.get(0));
		} else {
			instance = getInstance(fieldClass);
		}

		return instance;
	}

	private static boolean isSimpleType(Class<?> targetClass) {
		return Boolean.class.equals(targetClass) || Integer.class.equals(targetClass)
				|| Long.class.equals(targetClass)
				|| Float.class.equals(targetClass)
				|| Double.class.equals(targetClass)
				|| Byte.class.equals(targetClass)
				|| Short.class.equals(targetClass)
				|| Character.class.equals(targetClass)
				|| String.class.equals(targetClass);
	}
}
