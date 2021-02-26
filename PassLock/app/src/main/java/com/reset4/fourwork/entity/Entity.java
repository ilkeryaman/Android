package com.reset4.fourwork.entity;

import com.reset4.fourwork.constants.MetadataConstants;
import com.reset4.fourwork.library.enums.EntityStatus;
import com.reset4.fourwork.library.interfaces.IEntity;

import java.util.Map;

/**
 * Created by eilkyam on 11.06.2016.
 */
public class Entity implements IEntity {
    //region local variables
    private EntityStatus entityStatus;
    private String entityName;
    private Map<String, Field> fields;
    private boolean saveToFirebase;
    private boolean saveToDatabase;
    //endregion local variables

    //region properties
    public EntityStatus getEntityStatus()
    {
        if(entityStatus == null){
            entityStatus = EntityStatus.NewRecord;
        }
        return entityStatus;
    }

    void setEntityStatus(EntityStatus entityStatus){
        this.entityStatus = entityStatus;
    }

    public String getEntityName(){
        return entityName;
    }

    void setEntityName(String entityName){
        this.entityName = entityName;
    }

    public Map<String, Field> getFields(){
        return fields;
    }

    void setFields(Map<String, Field> fields) {
        this.fields = fields;
    }

    boolean isSaveToFirebase() {
        return saveToFirebase;
    }

    void setSaveToFirebase(boolean saveToFirebase) {
        this.saveToFirebase = saveToFirebase;
    }

    boolean isSaveToDatabase() {
        return saveToDatabase;
    }

    void setSaveToDatabase(boolean saveToDatabase) {
        this.saveToDatabase = saveToDatabase;
    }
    //endregion properties

    //region methods
    public Field getPrimaryKeyField() {
        return getField(MetadataConstants.PrimaryKeyFieldName);
    }

    public Field getField(String key){
        return getFields().get(key);
    }

    public void setFieldValue(String key, Object value){
        getField(key).setValue(value);
    }
    //endregion methods
}
