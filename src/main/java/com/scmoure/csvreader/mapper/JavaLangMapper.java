package com.scmoure.csvreader.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.scmoure.csvreader.AtomicMapper;

class JavaLangMapper implements AtomicMapper {
	private static final String INSTANTIATOR_METHOD_NAME = "valueOf";

	private Class<?> targetType;
	private Method instantiator;

	JavaLangMapper(Class<?> targetType) {
		this.targetType = targetType;
		this.instantiator = this.getInstantiator();
	}

	private Method getInstantiator() {
		Method instantiator = null;

		try {
			instantiator = this.targetType.getDeclaredMethod(INSTANTIATOR_METHOD_NAME, String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return instantiator;
	}

	@Override
	public Object apply(String rawValue) {
		Object value = null;

		try {
			value = this.instantiator.invoke(null, rawValue);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;
	}

}
