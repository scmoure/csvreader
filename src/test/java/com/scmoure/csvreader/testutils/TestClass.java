package com.scmoure.csvreader.testutils;

import com.scmoure.csvreader.annotations.CSVColumn;
import com.scmoure.csvreader.annotations.CSVObject;

public class TestClass
{

    @CSVColumn(column = 0)
    String name;

    @CSVColumn(column = 3)
    Integer age;

    @CSVObject(startingColumn = 1, endingColumn = 2)
    InnerTestClass innerObject;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public InnerTestClass getInnerObject()
    {
        return innerObject;
    }

    public void setInnerObject(InnerTestClass innerObject)
    {
        this.innerObject = innerObject;
    }

}
