package com.reset4.fourwork.repository;

import com.reset4.fourwork.library.enums.ColumnDataType;
import com.reset4.fourwork.library.interfaces.IColumn;

/**
 * Created by eilkyam on 15.06.2016.
 */
public class Column implements IColumn {
    //region local variables
    String name = null;
    Table columnTable = null;
    Table foreignKeyTable = null;
    boolean primaryKey = false;
    ColumnDataType dataType = null;
    //endregion local variables

    //region properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Table getColumnTable() {
        return columnTable;
    }

    public void setColumnTable(Table columnTable) {
        this.columnTable = columnTable;
    }

    public Table getForeignKeyTable() {
        return foreignKeyTable;
    }

    public void setForeignKeyTable(Table foreignKeyTable) {
        this.foreignKeyTable = foreignKeyTable;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public ColumnDataType getDataType() {
        return dataType;
    }

    public void setDataType(ColumnDataType dataType) {
        this.dataType = dataType;
    }
    //endregion properties
}
