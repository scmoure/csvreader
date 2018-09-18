package com.scmoure.csvreader.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.scmoure.csvreader.AtomicMapper;
import com.scmoure.csvreader.mapper.exception.MapperException;

class JavaLangMapper implements AtomicMapper {
	private static final String INSTANTIATOR_METHOD_NAME = "valueOf";

	private Class<?> targetClass;
	private Method instantiator;

	JavaLangMapper(Class<?> targetClass) {
		this.targetClass = targetClass;
		this.instantiator = this.getInstantiator();
	}

	private Method getInstantiator() {
		Method instantiator = null;

		try {
			instantiator = this.targetClass.getMethod(INSTANTIATOR_METHOD_NAME, String.class);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(
					"Target class must have a " + INSTANTIATOR_METHOD_NAME
							+ "(String s) method, like Integer."
							+ INSTANTIATOR_METHOD_NAME
							+ "(String s)",
					e);
		}

		return instantiator;
	}

	@Override
	public Object apply(String rawValue) {
		Object value = null;

		try {
			value = this.instantiator.invoke(null, rawValue);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e.toString());
		} catch (IllegalAccessException e) {
			throw new MapperException(
					"Unaccessible method while mapping value : " + this.instantiator.getName()
							+ ". Is it a public method?",
					e.getCause());
		}

		return value;
	}

}
