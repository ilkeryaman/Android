package com.reset4.fourwork.entity;

import com.reset4.fourwork.library.interfaces.IField;
import com.reset4.fourwork.repository.Column;
import com.reset4.fourwork.repository.Table;

/**
 * Created by eilkyam on 11.06.2016.
 */
public class Field implements IField {
    //region local variables
    private Object value;
    private Table fieldTable;
    private Column fieldColumn;
    //endregion local variables

    //region properties
    public Object getValue(){
        return value;
    }

    public void setValue(Object value){
        this.value = value;
    }

    public Table getFieldTable(){
        return fieldTable;
    }

    public void setFieldTable(Table fieldTable) {
        this.fieldTable = fieldTable;
    }

    public Column getFieldColumn() {
        return fieldColumn;
    }

    public void setFieldColumn(Column fieldColumn) {
        this.fieldColumn = fieldColumn;
    }
    //endregion properties
}
