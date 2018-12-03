package com.scmoure.csvreader.testutils;

import com.scmoure.csvreader.annotations.CSVColumn;

public class InnerTestClass
{

    @CSVColumn(column = 1)
    private String innerName;

    @CSVColumn(column = 0)
    private Long innerAge;

    public String getInnerName()
    {
        return innerName;
    }

    public void setInnerName(String innerName)
    {
        this.innerName = innerName;
    }

    public Long getInnerAge()
    {
        return innerAge;
    }

    public void setInnerAge(Long innerAge)
    {
        this.innerAge = innerAge;
    }

}
