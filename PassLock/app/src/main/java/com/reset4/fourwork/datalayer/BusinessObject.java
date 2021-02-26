package com.reset4.fourwork.datalayer;

import com.reset4.fourwork.constants.Messages;
import com.reset4.fourwork.constants.MetadataConstants;
import com.reset4.fourwork.data.DataColumn;
import com.reset4.fourwork.data.DataRow;
import com.reset4.fourwork.data.DataTable;
import com.reset4.fourwork.engine.general.FourActivator;
import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.entity.Entity;
import com.reset4.fourwork.entity.EntityCommunicator;
import com.reset4.fourwork.entity.ForeignKey;
import com.reset4.fourwork.entity.UniqueID;
import com.reset4.fourwork.library.enums.ColumnDataType;
import com.reset4.fourwork.library.enums.EntityStatus;
import com.reset4.fourwork.library.interfaces.IBusinessObject;
import com.reset4.fourwork.querybuilder.Query;
import com.reset4.fourwork.repository.MetaData;
import com.reset4.fourwork.repository.Table;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eilkyam on 11.06.2016.
 */
public class BusinessObject implements IBusinessObject {
    //region local variables
    public Entity entity;
    private static EntityCommunicator entityCommunicator;
    private DALBase dalBase;
    private FourContext fourContext;
    private Table table;
    //endregion local variables

    //region constructors
    public BusinessObject(FourContext fourContext, Entity entity){
        setFourContext(fourContext);
        setEntity(entity);
    }

    public BusinessObject(FourContext fourContext, Table table) throws FourException {
        setFourContext(fourContext);
        setTable(table);
        Entity entity = fourContext.createEntity(table.getName());
        setEntity(entity);
    }
    //endregion constructors

    //region properties
    public Entity getEntity(){
        return entity;
    }

    private void setEntity(Entity entity){
        this.entity = entity;
    }

    private static EntityCommunicator getEntityCommunicator(){
        if(entityCommunicator == null){
            entityCommunicator = new EntityCommunicator();
        }
        return entityCommunicator;
    }

    private DALBase getDalBase(){
        if(dalBase == null){
            dalBase = new DALBase();
        }
        return dalBase;
    }

    public Table getTable(){
        return table;
    }

    private void setTable(Table table){
        this.table = table;
    }

    public FourContext getFourContext()
    {
        return fourContext;
    }

    private void setFourContext(FourContext fourContext){
        this.fourContext = fourContext;
    }
    //endregion properties

    //region methods

    //region get methods
    public static BusinessObject getBusinessObject(Entity entity, FourContext fourContext) throws FourException {
        return getBusinessObject(entity.getEntityName(), entity, fourContext);
    }

    public static BusinessObject getBusinessObject(String entityName, Entity entity, FourContext fourContext) throws FourException {
        BusinessObject businessObject;
        if (fourContext == null)
        {
            throw new FourException(Messages.FourContextCanNotBeNull);
        }
        Table table = fourContext.getMetaData().getTable(entityName);
        String tableName = table.getName();
        MetaData metaData = fourContext.getMetaData();
        businessObject = (BusinessObject) FourActivator.createInstance(metaData.getBusinessObjects().get(tableName), fourContext, entity);
        return businessObject;
    }

    public static BusinessObject getBusinessObject(String entityName, FourContext fourContext) throws FourException{
        BusinessObject businessObject;
        if (fourContext == null)
        {
            throw new FourException(Messages.FourContextCanNotBeNull);
        }
        Table table = fourContext.getMetaData().getTable(entityName);
        String tableName = table.getName();
        MetaData metaData = fourContext.getMetaData();
        businessObject = (BusinessObject) FourActivator.createInstance(metaData.getBusinessObjects().get(tableName), fourContext, table);
        return businessObject;
    }

    public static BusinessObject getBusinessObject(UniqueID id, String entityName, FourContext fourContext) throws FourException {
        Entity entity = getEntity(id, entityName, fourContext);
        return getBusinessObject(entity, fourContext);
    }

    protected static Entity getEntity(UniqueID id, String entityName, FourContext fourContext) throws FourException {
        Map<String, Object> filters = new HashMap<>();
        filters.put(MetadataConstants.PrimaryKeyFieldName, id);
        Query query = new Query(entityName, fourContext);
        query.setFilters(filters);
        DataTable dataTable = getDataTable(query, fourContext);
        Entity entity = null;
        if(dataTable.getRows().size() > 0){
            entity = getEntityFromDataRow(fourContext, query, dataTable.getRows().get(0));
        }else{
            entity = fourContext.getMetaData().createNewEntity(entityName);
            getEntityCommunicator().setEntityStatus(entity, EntityStatus.NewRecord);
        }
        return entity;
    }

    private static DataTable getDataTable(Query query, FourContext fourContext){
        return query.execute();
    }

    static Entity getEntityFromDataRow(FourContext fourContext, Query query, DataRow dataRow) throws FourException {
        Entity entity = fourContext.getMetaData().createNewEntity(query.getTable());
        setEntityFields(entity, dataRow);
        getEntityCommunicator().setEntityStatus(entity, EntityStatus.Loaded);
        return entity;
    }

    private static void setEntityFields(Entity entity, DataRow dataRow){
        Map<String, DataColumn> dataColumns = dataRow.getColumns();
        for(DataColumn dataColumn : dataColumns.values()){
            entity.setFieldValue(dataColumn.getName(), getValue(dataColumn));
        }
    }

    private static Object getValue(DataColumn dataColumn){
        ColumnDataType dataType = dataColumn.getDataType();
        Object value = null;
        switch (dataType){
            case UUID:
                value = new UniqueID((String)dataColumn.getValue());
                break;
            case ForeignID:
                value = new ForeignKey((String)dataColumn.getValue());
                break;
            case String:
                value = (String) dataColumn.getValue();
                break;
            case Integer:
                value = (int) dataColumn.getValue();
                break;
            case Boolean:
                value = (boolean) dataColumn.getValue();
                break;
        }

        return value;
    }
    //endregion get methods

    //region save methods
    public final void save() throws FourException {
        beforeSave();
        if(validateSave()){
            try{
                onSaving();
                saveEntity();
                afterSave();
                setEntityStatus(EntityStatus.Loaded);
            }catch(Exception exception) {
                onExceptionInSave();
                throw exception;
            }
        }
    }

    /**
     * Sequence in Save method
     * 1- beforeSave
     * 2- validateSave
     * 3- onSaving
     * 4- afterSave
     * 5- onExceptionInSave
     */
    public void beforeSave(){

    }

    /**
     * Sequence in Save method
     * 1- beforeSave
     * 2- validateSave
     * 3- onSaving
     * 4- afterSave
     * 5- onExceptionInSave
     */
    public boolean validateSave(){
        return true;
    }

    /**
     * Sequence in Save method
     * 1- beforeSave
     * 2- validateSave
     * 3- onSaving
     * 4- afterSave
     * 5- onExceptionInSave
     */
    public void onSaving(){

    }

    private void saveEntity() throws FourException {
        getDalBase().save(getEntity());
    }

    /**
     * Sequence in Save method
     * 1- beforeSave
     * 2- validateSave
     * 3- onSaving
     * 4- afterSave
     * 5- onExceptionInSave
     */
    public void afterSave(){

    }

    /**
     * Sequence in Save method
     * 1- beforeSave
     * 2- validateSave
     * 3- onSaving
     * 4- afterSave
     * 5- onExceptionInSave
     */
    public void onExceptionInSave(){

    }
    //endregion save methods

    //region delete methods
    public final void delete() throws FourException{
        beforeDelete();
        if(validateDelete()){
            try {
                onDeleting();
                deleteEntity();
                afterDelete();
                setEntityStatus(EntityStatus.Deleted);
            }catch(Exception exception) {
                onExceptionInDelete();
                throw exception;
            }
        }
    }

    /**
     * Sequence in Delete method
     * 1- beforeDelete
     * 2- validateDelete
     * 3- onDeleting
     * 4- afterDelete
     * 5- onExceptionInDelete
     */
    public void beforeDelete(){

    }

    /**
     * Sequence in Delete method
     * 1- beforeDelete
     * 2- validateDelete
     * 3- onDeleting
     * 4- afterDelete
     * 5- onExceptionInDelete
     */
    public boolean validateDelete(){
        return true;
    }

    /**
     * Sequence in Delete method
     * 1- beforeDelete
     * 2- validateDelete
     * 3- onDeleting
     * 4- afterDelete
     * 5- onExceptionInDelete
     */
    public void onDeleting(){

    }

    private void deleteEntity() throws FourException {
        getDalBase().delete(getEntity());
    }

    /**
     * Sequence in Delete method
     * 1- beforeDelete
     * 2- validateDelete
     * 3- onDeleting
     * 4- afterDelete
     * 5- onExceptionInDelete
     */
    public void afterDelete(){

    }

    /**
     * Sequence in Delete method
     * 1- beforeDelete
     * 2- validateDelete
     * 3- onDeleting
     * 4- afterDelete
     * 5- onExceptionInDelete
     */
    public void onExceptionInDelete(){

    }
    //endregion delete methods

    public void afterLoad(){

    }

    private void setEntityStatus(EntityStatus entityStatus){
        getEntityCommunicator().setEntityStatus (getEntity(), entityStatus);
    }
    //endregion methods
}
