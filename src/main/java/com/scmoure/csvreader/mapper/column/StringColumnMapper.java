package com.scmoure.csvreader.mapper.column;

/**
 * Implementation ColumnMapper for the String class
 * 
 * @author scmoure
 */
class StringColumnMapper implements ColumnMapper
{

    @Override
    public String apply(String rawValue)
    {
        return rawValue;
    }

}
