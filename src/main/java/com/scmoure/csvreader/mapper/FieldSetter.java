package com.scmoure.csvreader.mapper;

@FunctionalInterface
interface FieldSetter
{

    void invokeSetter(Object o, Object fieldValue);
}
