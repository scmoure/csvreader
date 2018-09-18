package com.scmoure.csvreader.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.scmoure.csvreader.CSVColumn;
import com.scmoure.csvreader.LineMapper;

class ComplexObjectMapper implements LineMapper {

	private List<LineMapper> objectMappers;
	private List<Method> fieldSetters;
	private Constructor<?> constructor;

	private ComplexObjectMapper(ComplexObjectMapperBuilder builder) {
		this.objectMappers = builder.objectMappers;
		this.fieldSetters = builder.fieldSetters;
		this.constructor = builder.constructor;
	}

	@Override
	public Object apply(String[] values) {
		Object mappedObject = this.getInstance();
		IntStream.range(0, fieldSetters.size()).forEach(i -> {
			Object fieldValue = this.objectMappers.get(i).apply(values);
			this.invokeSetter(fieldSetters.get(i), mappedObject, fieldValue);
		});
		return mappedObject;
	}

	private void invokeSetter(Method setter, Object mappedObject, Object fieldValue) {
		try {
			setter.invoke(mappedObject, fieldValue);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Object getInstance() {
		Object targetObject = null;
		try {
			targetObject = this.constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return targetObject;
	}

	public static class ComplexObjectMapperBuilder {
		private static final String SETTER_PREFIX = "set";

		private List<LineMapper> objectMappers;
		private List<Method> fieldSetters;
		private Constructor<?> constructor;

		ComplexObjectMapperBuilder(Class<?> targetType) {
			try {
				this.constructor = targetType.getConstructor();
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.objectMappers = new ArrayList<>();
			this.fieldSetters = new ArrayList<>();
			for (Field field : targetType.getDeclaredFields()) {
				if (field.isAnnotationPresent(CSVColumn.class)) {
					fieldSetters.add(this.findSetter(field));
					objectMappers.add(LineMapperFactory.getInstance(field));
				}
			}
		}

		private Method findSetter(Field field) {
			Method setter = null;

			Class<?> argumentType = field.getType();
			String firstLetter = field.getName().substring(0, 1).toUpperCase();
			String capitalizedFieldName = firstLetter.concat(field.getName().substring(1));
			String setterName = SETTER_PREFIX.concat(capitalizedFieldName);
			try {
				setter = field.getDeclaringClass().getMethod(setterName, argumentType);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return setter;
		}

		ComplexObjectMapper build() {
			return new ComplexObjectMapper(this);
		}
	}
}
