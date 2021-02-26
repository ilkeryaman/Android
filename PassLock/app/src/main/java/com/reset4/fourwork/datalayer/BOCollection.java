package com.reset4.fourwork.datalayer;

import com.reset4.fourwork.data.DataRow;
import com.reset4.fourwork.data.DataTable;
import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.entity.Entity;
import com.reset4.fourwork.querybuilder.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eilkyam on 19.06.2016.
 */
public class BOCollection {
    //region local variables
    private ArrayList<BusinessObject> items;
    private Query query;
    //endregion local variables

    //region constructors
    public BOCollection(){

    }
    //endregion constructors

    //region properties
    public ArrayList<BusinessObject> getItems() {
        if(items == null){
            items = new ArrayList<>();
        }
        return items;
    }

    private void setItems(ArrayList<BusinessObject> items) {
        this.items = items;
    }

    private Query getQuery() {
        return query;
    }

    private void setQuery(Query query) {
        this.query = query;
    }

    public int getCount(){
        return getItems().size();
    }
    //endregion properties

    //region methods
    private static BOCollection loadItems(String tableName, FourContext fourContext, Map<String, Object> filters) throws FourException {
        BOCollection collection = new BOCollection();
        Query query = new Query(tableName, fourContext);
        query.setFilters(filters);
        collection.setQuery(query);
        DataTable dataTable = collection.getQuery().execute();
        if(dataTable.getRows().size() > 0){
            for(DataRow dataRow : dataTable.getRows()){
                collection.loadBO(fourContext, query, dataRow);
            }
        }
        return collection;
    }

    public static BOCollection load(FourContext fourContext, String tableName, String columnName, Object filterValue) throws FourException {
        Map<String, Object> filters = new HashMap<>();
        filters.put(columnName, filterValue);
        return loadByMultipleValue(fourContext, tableName, filters);
    }

    public static BOCollection loadByMultipleValue(FourContext fourContext, String tableName, Map<String, Object> filters) throws
            FourException {
        return loadItems(tableName, fourContext, filters);
    }

    private void loadBO(FourContext fourContext, Query query, DataRow dataRow) throws FourException {
        Entity rowEntity = BusinessObject.getEntityFromDataRow(fourContext, query, dataRow);
        BusinessObject businessObject = BusinessObject.getBusinessObject(rowEntity, fourContext);
        addBO(businessObject);
    }

    private void addBO(BusinessObject businessObject){
        getItems().add(businessObject);
    }
    //endregion methods
}
