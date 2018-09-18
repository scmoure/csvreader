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
import com.scmoure.csvreader.mapper.exception.MapperException;

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
		} catch (InvocationTargetException e) {
			throw new MapperException(
					"A problem occured when invoking setter " + setter.getName()
							+ " for class "
							+ mappedObject.getClass().getName()
							+ " with value "
							+ fieldValue,
					e.getCause());
		} catch (IllegalAccessException e) {
			throw new MapperException(
					"Unaccessible method while mapping value : " + setter.getName()
							+ ". Is it a public method?",
					e.getCause());
		}
	}

	private Object getInstance() {
		Object targetObject = null;
		try {
			targetObject = this.constructor.newInstance();
		} catch (InstantiationException | InvocationTargetException e) {
			throw new MapperException(
					"A problem occured when invoking default constructor " + this.constructor.getName()
							+ " for class "
							+ this.constructor.getDeclaringClass().getName(),
					e.getCause());
		} catch (IllegalAccessException e) {
			throw new MapperException(
					"Unaccessible constructor : " + this.constructor.getName() + ". Is it a public method?",
					e.getCause());
		}

		return targetObject;
	}

	public static class ComplexObjectMapperBuilder {
		private static final String SETTER_PREFIX = "set";

		private List<LineMapper> objectMappers;
		private List<Method> fieldSetters;
		private Constructor<?> constructor;

		ComplexObjectMapperBuilder(Class<?> targetClass) {
			this.constructor = this.getDefaultConstructor(targetClass);
			this.objectMappers = new ArrayList<>();
			this.fieldSetters = new ArrayList<>();
			for (Field field : targetClass.getDeclaredFields()) {
				if (field.isAnnotationPresent(CSVColumn.class)) {
					fieldSetters.add(this.findSetter(field));
					objectMappers.add(LineMapperFactory.getInstance(field));
				}
			}
		}

		private Constructor<?> getDefaultConstructor(Class<?> targetClass) {
			Constructor<?> defaultConstructor = null;

			try {
				defaultConstructor = targetClass.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new MapperException(
						"Could not obtain a default constructor for class " + targetClass.getName(),
						e.getCause());
			}

			return defaultConstructor;
		}

		private Method findSetter(Field field) {
			Method setter = null;

			Class<?> argumentType = field.getType();
			String firstLetter = field.getName().substring(0, 1).toUpperCase();
			String capitalizedFieldName = firstLetter.concat(field.getName().substring(1));
			String setterName = SETTER_PREFIX.concat(capitalizedFieldName);
			try {
				setter = field.getDeclaringClass().getMethod(setterName, argumentType);
			} catch (NoSuchMethodException e) {
				throw new MapperException(
						"Could not find a setter method " + setterName
								+ "("
								+ argumentType.getName()
								+ " arg) for class "
								+ field.getDeclaringClass().getName(),
						e.getCause());
			}

			return setter;
		}

		ComplexObjectMapper build() {
			return new ComplexObjectMapper(this);
		}
	}
}
