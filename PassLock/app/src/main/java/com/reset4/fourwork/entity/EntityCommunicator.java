package com.reset4.fourwork.entity;

import com.reset4.fourwork.library.enums.EntityStatus;

import java.util.Map;

/**
 * Created by eilkyam on 11.06.2016.
 */
public class EntityCommunicator {

    public void setEntityName(Entity entity, String entityName){
        entity.setEntityName(entityName);
    }

    public void setEntityStatus(Entity entity, EntityStatus entityStatus){
        entity.setEntityStatus(entityStatus);
    }

    public void setFields(Entity entity, Map<String, Field> fields){
        entity.setFields(fields);
    }

    public boolean isSaveToFirebase(Entity entity){
        return entity.isSaveToFirebase();
    }

    public void setSaveToFirebase(Entity entity, boolean saveToFirebase){
        entity.setSaveToFirebase(saveToFirebase);
    }

    public boolean isSaveToDatabase(Entity entity){
        return entity.isSaveToDatabase();
    }

    public void setSaveToDatabase(Entity entity, boolean saveToDatabase){
        entity.setSaveToDatabase(saveToDatabase);
    }
}
