package com.scmoure.csvreader.mapper.line;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import com.scmoure.csvreader.mapper.MapperException;

public class ObjectLineMapper implements LineMapper {

  private Map<Method, LineMapper> fieldSetterToMapper;

  private Function<List<String>, List<String>> prepare;

  private Constructor<?> constructor;

  private int columnOffset;

  private ObjectLineMapper(ObjectLineMapperBuilder builder) {
    this.fieldSetterToMapper = builder.fieldSetterToMapper;
    this.constructor = builder.constructor;
    this.columnOffset = builder.columnOffset;
    this.prepare = builder.prepare;
  }

  @Override
  public Object apply(List<String> rawValues) {
    Object mappedObject = this.getInstance();

    List<String> values = this.prepare.apply(rawValues);

    for (Method fieldSetter : fieldSetterToMapper.keySet()) {
      Object fieldValue = this.fieldSetterToMapper.get(fieldSetter)
          .apply(values.subList(columnOffset, values.size()));
      this.invokeSetter(fieldSetter, mappedObject, fieldValue);
    }

    return mappedObject;
  }

  private void invokeSetter(Method setter, Object mappedObject, Object fieldValue) {
    try {
      setter.invoke(mappedObject, fieldValue);
    } catch (InvocationTargetException e) {
      throw new MapperException("A problem occured when invoking setter " + setter.getName()
          + " for class " + mappedObject.getClass().getName() + " with value " + fieldValue,
          e.getCause());
    } catch (IllegalAccessException e) {
      throw new MapperException("Unaccessible method while mapping value : " + setter.getName()
          + ". Is it a public method?", e.getCause());
    }
  }

  private Object getInstance() {
    Object targetObject = null;
    try {
      targetObject = this.constructor.newInstance();
    } catch (InstantiationException | InvocationTargetException e) {
      throw new MapperException(
          "A problem occured when invoking default constructor " + this.constructor.getName()
              + " for class " + this.constructor.getDeclaringClass().getName(),
          e.getCause());
    } catch (IllegalAccessException e) {
      throw new MapperException(
          "Unaccessible constructor : " + this.constructor.getName() + ". Is it a public method?",
          e.getCause());
    }

    return targetObject;
  }

  public static class ObjectLineMapperBuilder {
    private static final String SETTER_PREFIX = "set";

    private Map<Method, LineMapper> fieldSetterToMapper;

    private Function<List<String>, List<String>> prepare;

    private Constructor<?> constructor;

    private int columnOffset;

    public ObjectLineMapperBuilder(Class<?> targetClass) {
      this.columnOffset = 0;
      this.constructor = this.getDefaultConstructor(targetClass);
      this.prepare = Function.identity();
      this.fieldSetterToMapper = new HashMap<>();
      for (Field field : targetClass.getDeclaredFields()) {
        LineMapper mapper = LineMapperFactory.getInstance(field);
        if (mapper != null) {
          this.fieldSetterToMapper.put(this.findSetter(field), mapper);
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
        throw new MapperException("Could not find a setter method " + setterName + "("
            + argumentType.getName() + " arg) for class " + field.getDeclaringClass().getName(),
            e.getCause());
      }

      return setter;
    }

    public ObjectLineMapperBuilder columnOffset(int offset) {
      this.columnOffset = offset;
      return this;
    }

    public ObjectLineMapperBuilder prepare(Function<List<String>, List<String>> prepareFunction) {
      this.prepare = prepareFunction;
      return this;
    }

    public ObjectLineMapperBuilder withFieldMapper(String fieldName, LineMapper mapper) {
      // this.objectMappers.put(fieldName, mapper);
      return this;
    }

    public ObjectLineMapper build() {
      return new ObjectLineMapper(this);
    }
  }
}
