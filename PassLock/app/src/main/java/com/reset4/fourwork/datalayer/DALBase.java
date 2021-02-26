package com.reset4.fourwork.datalayer;

import com.reset4.fourwork.constants.Messages;
import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.entity.Entity;
import com.reset4.fourwork.entity.EntityCommunicator;
import com.reset4.fourwork.library.enums.EntityStatus;
import com.reset4.fourwork.manifest.FourApp;
import com.reset4.fourwork.querybuilder.QueryHelperSQL;

/**
 * Created by eilkyam on 11.06.2016.
 */
public class DALBase {
    //region local variables
    private EntityCommunicator entityCommunicator;
    //endregion local variables

    //region properties
    private EntityCommunicator getEntityCommunicator(){
        if(entityCommunicator == null){
            entityCommunicator = new EntityCommunicator();
        }
        return entityCommunicator;
    }
    //endregion properties

    //region methods

    //region save methods
    void save(Entity entity) throws FourException{
        EntityStatus entityStatus = entity.getEntityStatus();
        switch (entityStatus){
            case Deleted:
                throw new FourException(Messages.SaveDeletedRecordError);
            case Loaded:
                update(entity);
                break;
            case NewRecord:
                insert(entity);
                break;
        }
    }

    private void update(Entity entity){
        //TODO
        if(getEntityCommunicator().isSaveToFirebase(entity)){

        }

        if(getEntityCommunicator().isSaveToDatabase(entity)){
            updateDatabase(entity);
        }
    }

    private void insert(Entity entity){
        //TODO
        if(getEntityCommunicator().isSaveToFirebase(entity)){

        }

        if(getEntityCommunicator().isSaveToDatabase(entity)){
            insertToDatabase(entity);
        }
    }

    private void updateDatabase(Entity entity){
        FourContext fourContext = FourApp.getFourContext();
        QueryHelperSQL queryHelperSQL = new QueryHelperSQL(fourContext.getApplicationContext());
        queryHelperSQL.update(entity);
    }

    private void insertToDatabase(Entity entity){
        FourContext fourContext = FourApp.getFourContext();
        QueryHelperSQL queryHelperSQL = new QueryHelperSQL(fourContext.getApplicationContext());
        queryHelperSQL.insert(entity);
    }
    //endregion save methods

    //region delete methods
    void delete(Entity entity) throws FourException{
        EntityStatus entityStatus = entity.getEntityStatus();
        switch (entityStatus){
            case Deleted:
                throw new FourException(Messages.DeleteDeletedRecordError);
            case Loaded:
                deleteInternal(entity);
                break;
            case NewRecord:
                throw new FourException(Messages.DeleteNewRecordError);
        }
    }

    void deleteInternal(Entity entity){
        //TODO
        if(getEntityCommunicator().isSaveToFirebase(entity)){

        }

        if(getEntityCommunicator().isSaveToDatabase(entity)){
            deleteFromDatabase(entity);
        }
    }

    private void deleteFromDatabase(Entity entity){
        FourContext fourContext = FourApp.getFourContext();
        QueryHelperSQL queryHelperSQL = new QueryHelperSQL(fourContext.getApplicationContext());
        queryHelperSQL.delete(entity);
    }
    //endregion delete methods

    //endregion methods
}
