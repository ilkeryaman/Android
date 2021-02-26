package com.reset4.fourwork.repository;

import com.reset4.fourwork.library.enums.SaveSetting;
import com.reset4.fourwork.library.interfaces.ITable;

import java.util.Map;

/**
 * Created by eilkyam on 11.06.2016.
 */
public class Table implements ITable {
    //region local variables
    private String name;
    private Class entityType;
    private Class businessObjectType;
    private SaveSetting saveSetting;
    private Map<String, Column> columns;
    //endregion local variables

    //region properties
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Class getEntityType() {
        return entityType;
    }

    public void setEntityType(Class entityType) {
        this.entityType = entityType;
    }

    public Class getBusinessObjectType() {
        return businessObjectType;
    }

    public void setBusinessObjectType(Class businessObjectType) {
        this.businessObjectType = businessObjectType;
    }

    public SaveSetting getSaveSetting() {
        return saveSetting;
    }

    public void setSaveSetting(SaveSetting saveSetting) {
        this.saveSetting = saveSetting;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }
    //endregion properties
}
