package com.scmoure.csvreader.mapper.column;

/**
 * Provides functionality to obtain a ColumnMapper implementation
 * 
 * @author scmoure
 *
 */
public class ColumnMapperFactory {

	/**
	 * Retrives the proper ColumnMapper implementation for the given target class
	 * 
	 * @param targetClass
	 * @return
	 */
	public static ColumnMapper getInstance(Class<?> targetClass) {
		ColumnMapper instance = null;

		if (targetClass.isPrimitive()) {
			Class<?> primitiveWrapper = getPrimitiveWrapper(targetClass);
			instance = new JavaLangColumnMapper(primitiveWrapper);
		} else if (Boolean.class.equals(targetClass)) {
			instance = new JavaLangColumnMapper(targetClass);
		} else if (Integer.class.equals(targetClass)) {
			instance = new JavaLangColumnMapper(targetClass);
		} else if (Long.class.equals(targetClass)) {
			instance = new JavaLangColumnMapper(targetClass);
		} else if (Float.class.equals(targetClass)) {
			instance = new JavaLangColumnMapper(targetClass);
		} else if (Double.class.equals(targetClass)) {
			instance = new JavaLangColumnMapper(targetClass);
		} else if (Byte.class.equals(targetClass)) {
			instance = new JavaLangColumnMapper(targetClass);
		} else if (Short.class.equals(targetClass)) {
			instance = new JavaLangColumnMapper(targetClass);
		} else if (Character.class.equals(targetClass)) {
			instance = new JavaLangColumnMapper(targetClass);
		} else if (String.class.equals(targetClass)) {
			instance = new StringColumnMapper();
		}

		return instance;
	}

	/**
	 * For each primitive type, retrieves the wrapper java.lang class
	 * 
	 * @param primitiveType
	 * @return
	 */
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
