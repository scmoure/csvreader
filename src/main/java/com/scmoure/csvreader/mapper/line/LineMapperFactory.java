package com.scmoure.csvreader.mapper.line;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import com.scmoure.csvreader.annotations.CSVColumn;
import com.scmoure.csvreader.annotations.CSVObject;
import com.scmoure.csvreader.annotations.CSVObjectList;

public class LineMapperFactory
{

    public static LineMapper getInstance(Class<?> targetClass)
    {
        LineMapper instance = null;
        if (isSimpleType(targetClass)) {
            instance = new SingleColumnLineMapper(targetClass);
        } else {
            instance = new ObjectLineMapper.ObjectLineMapperBuilder(targetClass).build();
        }
        return instance;
    }

    private static boolean isSimpleType(Class<?> targetClass)
    {
        return Boolean.class.equals(targetClass) || Integer.class.equals(targetClass) || Long.class.equals(targetClass)
            || Float.class.equals(targetClass) || Double.class.equals(targetClass) || Byte.class.equals(targetClass)
            || Short.class.equals(targetClass) || Character.class.equals(targetClass)
            || String.class.equals(targetClass);
    }

    static LineMapper getInstance(Field field)
    {
        LineMapper instance = null;

        if (field.isAnnotationPresent(CSVColumn.class)) {
            instance = new SingleColumnLineMapper(field.getType(), field.getAnnotation(CSVColumn.class).column());
        } else if (field.isAnnotationPresent(CSVObject.class)) {
            int columnOffset = field.getAnnotation(CSVObject.class).startingColumn();
            instance = new ObjectLineMapper.ObjectLineMapperBuilder(field.getType()).columnOffset(columnOffset).build();
        } else if (field.isAnnotationPresent(CSVObjectList.class)) {
            ParameterizedType paremterizedType = (ParameterizedType) field.getGenericType();
            Class<?> elementType = (Class<?>) paremterizedType.getActualTypeArguments()[0];
            int startingColumn = field.getAnnotation(CSVObjectList.class).startingColumn();
            int endingColumn = field.getAnnotation(CSVObjectList.class).startingColumn();
            int cycle = field.getAnnotation(CSVObjectList.class).cycle();
            instance =
                new ListLineMapper.ListLineMapperBuilder(startingColumn, endingColumn, cycle, elementType).build();
        }

        return instance;
    }

}
