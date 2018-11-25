package com.scmoure.csvreader.mapper.implementation;

import com.scmoure.csvreader.mapper.AtomicMapper;

class AtomicMapperFactory {

	static AtomicMapper getInstance(Class<?> targetClass) {
		AtomicMapper instance = null;

		if (targetClass.isPrimitive()) {
			Class<?> primitiveWrapper = getPrimitiveWrapper(targetClass);
			instance = new JavaLangMapper(primitiveWrapper);
		} else if (Boolean.class.equals(targetClass)) {
			instance = new JavaLangMapper(targetClass);
		} else if (Integer.class.equals(targetClass)) {
			instance = new JavaLangMapper(targetClass);
		} else if (Long.class.equals(targetClass)) {
			instance = new JavaLangMapper(targetClass);
		} else if (Float.class.equals(targetClass)) {
			instance = new JavaLangMapper(targetClass);
		} else if (Double.class.equals(targetClass)) {
			instance = new JavaLangMapper(targetClass);
		} else if (Byte.class.equals(targetClass)) {
			instance = new JavaLangMapper(targetClass);
		} else if (Short.class.equals(targetClass)) {
			instance = new JavaLangMapper(targetClass);
		} else if (Character.class.equals(targetClass)) {
			instance = new JavaLangMapper(targetClass);
		} else if (String.class.equals(targetClass)) {
			instance = new StringMapper();
		}

		return instance;
	}

	private static Class<?> getPrimitiveWrapper(Class<?> primitiveType) {
		Class<?> primitiveWrapper = null;

		if (Boolean.TYPE.equals(primitiveType)) {
			primitiveWrapper = Boolean.class;
		} else if (Integer.TYPE.equals(primitiveType)) {
			primitiveWrapper = Integer.class;
		} else if (Long.TYPE.equals(primitiveType)) {
			primitiveWrapper = Long.class;
		} else if (Float.TYPE.equals(primitiveType)) {
			primitiveWrapper = Float.class;
		} else if (Double.TYPE.equals(primitiveType)) {
			primitiveWrapper = Double.class;
		} else if (Byte.TYPE.equals(primitiveType)) {
			primitiveWrapper = Byte.class;
		} else if (Short.TYPE.equals(primitiveType)) {
			primitiveWrapper = Short.class;
		} else if (Character.TYPE.equals(primitiveType)) {
			primitiveWrapper = Character.class;
		}

		return primitiveWrapper;
	}
}
