package com.reset4.fourwork.data;

import com.reset4.fourwork.library.enums.ColumnDataType;

/**
 * Created by eilkyam on 19.06.2016.
 */
public class DataColumn {
    //region local variables
    private String name;
    private ColumnDataType dataType;
    private Object value;
    //endregion local variables

    //region properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnDataType getDataType() {
        return dataType;
    }

    public void setDataType(ColumnDataType dataType) {
        this.dataType = dataType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    //endregion properties
}
