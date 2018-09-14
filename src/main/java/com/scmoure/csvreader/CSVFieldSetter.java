package com.scmoure.csvreader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class CSVFieldSetter {

	private Class<?> targetClass;

	private String name;

	private Field targetField;

	private Class<?> targetFieldType;

	private Method setter;

	private Constructor<?> setterArgumentConstructor;

	CSVFieldSetter(String name, Class<?> targetClass)
			throws NoSuchFieldException, NoSuchMethodException, SecurityException {
		this.name = name;
		this.targetClass = targetClass;
		this.targetField = this.findTargetField();
		this.targetFieldType = this.findTargetFieldType();
		this.setter = this.findSetter();
		this.setterArgumentConstructor = this.findSetterArgumentConstructor();
	}

	private Field findTargetField() throws NoSuchFieldException {
		Field[] fields = targetClass.getDeclaredFields();
		Field field = null;
		boolean found = false;
		int i = 0;
		while (!found && (i < fields.length)) {
			field = fields[i];
			if (field.isAnnotationPresent(CSVColumn.class)) {
				String annotationNameValue = field.getAnnotation(CSVColumn.class).name();
				found = this.name.equals(annotationNameValue);
			}
			i++;
		}
		if (!found) {
			throw new NoSuchFieldException("No candidate field found for column " + this.name);
		}

		return field;
	}

	private Class<?> findTargetFieldType() {
		Class<?> targetFieldType = this.targetField.getType();
		if (targetFieldType.isPrimitive()) {

			if (boolean.class.equals(targetFieldType)) {
				return Boolean.class;
			} else if (int.class.equals(targetFieldType)) {
				return Integer.class;
			} else if (long.class.equals(targetFieldType)) {
				return Long.class;
			} else if (float.class.equals(targetFieldType)) {
				return Float.class;
			} else if (double.class.equals(targetFieldType)) {
				return Double.class;
			} else if (byte.class.equals(targetFieldType)) {
				return Byte.class;
			} else if (short.class.equals(targetFieldType)) {
				return Short.class;
			} else if (char.class.equals(targetFieldType)) {
				return Character.class;
			}
		}

		return targetFieldType;
	}

	private Method findSetter() throws NoSuchMethodException, SecurityException {
		Class<?> argumentType = this.targetField.getType();
		String firstLetter = this.targetField.getName().substring(0, 1).toUpperCase();
		String capitalizedFieldName = firstLetter.concat(this.targetField.getName().substring(1));
		String setterName = "set".concat(capitalizedFieldName);
		return this.targetClass.getMethod(setterName, argumentType);
	}

	private Constructor<?> findSetterArgumentConstructor() throws NoSuchMethodException, SecurityException {

		return this.targetFieldType.getConstructor(String.class);
	}

	void setValue(String value, Object targetObject) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		Object argument = this.setterArgumentConstructor.newInstance(value);
		this.setter.invoke(targetObject, argument);
	}
}
