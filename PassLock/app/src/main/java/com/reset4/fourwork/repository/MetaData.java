package com.reset4.fourwork.repository;

import com.reset4.fourwork.constants.Messages;
import com.reset4.fourwork.engine.general.FourActivator;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.entity.Entity;
import com.reset4.fourwork.entity.EntityCommunicator;
import com.reset4.fourwork.entity.Field;
import com.reset4.fourwork.entity.UniqueID;
import com.reset4.fourwork.library.enums.ColumnDataType;
import com.reset4.fourwork.library.enums.SaveSetting;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by eilkyam on 11.06.2016.
 */
public class MetaData {
    //region local variables
    private EntityCommunicator entityCommunicator;
    private SortedMap<String, Table> tables;
    private Map<String, Class> entities;
    private Map<String, Class> businessObjects;
    //endregion local variables

    //region properties
    public EntityCommunicator getEntityCommunicator(){
        if(entityCommunicator == null){
            entityCommunicator = new EntityCommunicator();
        }
        return entityCommunicator;
    }

    public SortedMap<String, Table> getTables(){
        return tables;
    }

    public void setTables(SortedMap<String, Table> tables){
        this.tables = tables;
    }

    public Map<String, Class> getEntities(){
        return entities;
    }

    public void setEntities(Map<String, Class> entities) {
        this.entities = entities;
    }

    public Map<String, Class> getBusinessObjects(){
        return businessObjects;
    }

    public void setBusinessObjects(Map<String, Class> businessObjects) {
        this.businessObjects = businessObjects;
    }
    //endregion properties

    //region methods
    public Entity createNewEntity(String entityName) throws FourException {
        Table table = getTable(entityName);
        return createNewEntity(table);
    }

    public Entity createNewEntity(Table table) throws FourException {
        Entity entity = getNewEntity(table);
        getEntityCommunicator().setEntityName(entity, table.getName());
        if (entity.getPrimaryKeyField().getValue() == null) {
            entity.getPrimaryKeyField().setValue(new UniqueID());
        }
        return entity;
    }

    public Table getTable(String tableName) throws FourException
    {
        if (tableExists(tableName))
        {
            return getTables().get(tableName);
        }

        throw new FourException(Messages.TableDoesNotExistInMetadata + tableName);
    }

    public boolean tableExists(String tableName)
    {
        SortedMap<String, Table> tables = getTables();
        return tables.containsKey(tableName);
    }

    private Entity getNewEntity(Table table) throws FourException {
        Entity entity;

        if (table == null)
        {
            throw new FourException(Messages.TableCanNotBeNull);
        }

        String entityName = table.getName();
        entity = (Entity) FourActivator.createInstance(getEntities().get(entityName));
        createEntityFields(entity, table);
        setSaveSetting(entity, table.getSaveSetting());
        return entity;
    }

    private void createEntityFields(Entity entity, Table table){
        Map<String, Field> fields = new HashMap<>();
        for(Map.Entry<String, Column> columnEntry : table.getColumns().entrySet()){
            Column column = columnEntry.getValue();
            String columnName = column.getName();
            Field field = new Field();
            field.setFieldColumn(column);
            field.setFieldTable(table);
            field.setValue(null);
            fields.put(columnName, field);
        }
        getEntityCommunicator().setFields(entity, fields);
    }

    public static ColumnDataType getColumnDataType(String type){
        ColumnDataType columnDataType = null;

        switch (type){
            case "String":
                columnDataType = ColumnDataType.String;
                break;
            case "Integer":
                columnDataType = ColumnDataType.Integer;
                break;
            case "UUID":
                columnDataType = ColumnDataType.UUID;
                break;
            case "ForeignID":
                columnDataType = ColumnDataType.ForeignID;
                break;
            case "Boolean":
                columnDataType = ColumnDataType.Boolean;
                break;
        }

        return columnDataType;
    }

    public static SaveSetting getSaveSetting(String _saveSetting){
        SaveSetting saveSetting = null;

        switch (_saveSetting){
            case "SaveToFirebase":
                saveSetting = SaveSetting.SaveToFirebase;
                break;
            case "SaveToDatabase":
                saveSetting = SaveSetting.SaveToDatabase;
                break;
            case "Both":
                saveSetting = SaveSetting.Both;
                break;
        }

        return saveSetting;
    }

    private void setSaveSetting(Entity entity, SaveSetting saveSetting){
        EntityCommunicator entityCommunicator = getEntityCommunicator();
        switch (saveSetting){
            case SaveToFirebase:
                entityCommunicator.setSaveToFirebase(entity, true);
                entityCommunicator.setSaveToDatabase(entity, false);
                break;
            case SaveToDatabase:
                entityCommunicator.setSaveToFirebase(entity, false);
                entityCommunicator.setSaveToDatabase(entity, true);
                break;
            case Both:
                entityCommunicator.setSaveToFirebase(entity, true);
                entityCommunicator.setSaveToDatabase(entity, true);
                break;
        }
    }
    //endregion methods
}
