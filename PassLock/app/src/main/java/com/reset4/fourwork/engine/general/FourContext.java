package com.reset4.fourwork.engine.general;

import android.content.Context;

import com.reset4.fourwork.datalayer.BOCollection;
import com.reset4.fourwork.datalayer.BusinessObject;
import com.reset4.fourwork.entity.Entity;
import com.reset4.fourwork.library.interfaces.IFourContext;
import com.reset4.fourwork.repository.MetaData;

import java.util.Map;

/**
 * Created by eilkyam on 11.06.2016.
 */
public class FourContext implements IFourContext{
    //region local variables
    private MetaData metaData;
    private Context applicationContext;
    //endregion local variables

    //region constructors
    public FourContext(){

    }

    public FourContext(MetaData metaData, Context context){
        setMetaData(metaData);
        setApplicationContext(context);
    }
    //endregion constructors

    //region properties
    public MetaData getMetaData(){
        return metaData;
    }

    private void setMetaData(MetaData metaData){
        this.metaData = metaData;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    private void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
    //endregion properties

    //region methods
    public Entity createEntity(String entityName) throws FourException {
        return getMetaData().createNewEntity(entityName);
    }

    public BusinessObject loadByValue(String tableName, String columnName, Object searchedValue) throws FourException {
        BusinessObject businessObject = this.getBusinessObject(tableName, columnName, searchedValue);
        return businessObject;
    }

    public BusinessObject loadByMultipleValue(String tableName, Map<String, Object> filters) throws FourException {
        BOCollection collection = BOCollection.loadByMultipleValue(this, tableName, filters);
        if (collection.getCount() > 0) {
            return collection.getItems().get(0);
        }
        return BusinessObject.getBusinessObject(tableName, this);
    }

    public BusinessObject getBusinessObject(String tableName, String filterColumn, Object filterValue) throws FourException {
        BOCollection collection = BOCollection.load(this, tableName, filterColumn, filterValue);
        if (collection.getCount() > 0)
        {
            return collection.getItems().get(0);
        }
        return BusinessObject.getBusinessObject(tableName, this);
    }
    //endregion methods
}
