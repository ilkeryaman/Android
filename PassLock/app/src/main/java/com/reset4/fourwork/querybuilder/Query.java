package com.reset4.fourwork.querybuilder;

import android.database.Cursor;

import com.reset4.fourwork.constants.FourConstants;
import com.reset4.fourwork.constants.Punctuation;
import com.reset4.fourwork.constants.Scripts;
import com.reset4.fourwork.data.DataColumn;
import com.reset4.fourwork.data.DataRow;
import com.reset4.fourwork.data.DataTable;
import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.entity.ForeignKey;
import com.reset4.fourwork.entity.UniqueID;
import com.reset4.fourwork.library.enums.ColumnDataType;
import com.reset4.fourwork.library.enums.SaveSetting;
import com.reset4.fourwork.library.interfaces.IQuery;
import com.reset4.fourwork.repository.Column;
import com.reset4.fourwork.repository.MetaData;
import com.reset4.fourwork.repository.Table;

import java.util.Map;

/**
 * Created by eilkyam on 19.06.2016.
 */
public class Query implements IQuery {
    //region local variables
    String entityName;
    Table table;
    private FourContext fourContext;
    Map<String, Object> filters;
    QueryHelperSQL queryHelperSQL;
    String generatedSql;
    //endregion local variables

    //region constructors
    public Query(String entityName, FourContext fourContext) throws FourException {
        setEntityName(entityName);
        setFourContext(fourContext);
        setTable(entityName);
    }
    //endregion constructors

    //region properties
    public String getEntityName() {
        return entityName;
    }

    private void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Table getTable() {
        return table;
    }

    private void setTable(Table table) {
        this.table = table;
    }

    public FourContext getFourContext() {
        return fourContext;
    }

    private void setFourContext(FourContext fourContext) {
        this.fourContext = fourContext;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

    private QueryHelperSQL getQueryHelperSQL() {
        if(queryHelperSQL == null){
            queryHelperSQL = new QueryHelperSQL(fourContext.getApplicationContext());
        }
        return queryHelperSQL;
    }

    private String getGeneratedSql() {
        return generatedSql;
    }

    private void setGeneratedSql(String generatedSql) {
        this.generatedSql = generatedSql;
    }
    //endregion properties

    //region methods
    private void setTable(String entityName) throws FourException {
        MetaData metadata = getFourContext().getMetaData();
        setTable(metadata.getTable(entityName));
    }

    public void generateQuery(){
        String wherePart = FourConstants.EmptyString;
        for(Map.Entry<String, Object> filterEntry : getFilters().entrySet()){
            String columnName = filterEntry.getKey();
            String filterValue = getFilterValueSql(filterEntry.getValue());
            wherePart += FourConstants.Space + Scripts.And + FourConstants.Space + columnName + FourConstants.Equal + filterValue;
        }
        wherePart = wherePart.replaceFirst(Scripts.And, FourConstants.EmptyString);

        String sql = String.format(Scripts.GetRecordTemplate, getEntityName(), wherePart);
        setGeneratedSql(sql);
    }

    private String getFilterValueSql(Object value){
        String filterValueSql;
        if(value instanceof String || value instanceof UniqueID || value instanceof ForeignKey){
            filterValueSql = Punctuation.Apostrophe + value.toString() + Punctuation.Apostrophe;
        }else{
            filterValueSql = value.toString();
        }
        return filterValueSql;
    }

    public DataTable execute(){
        DataTable dataTable = null;
        SaveSetting saveSetting = getTable().getSaveSetting();
        if(saveSetting.equals(SaveSetting.SaveToFirebase)){
            dataTable = getFromFirebase();
        }else if(saveSetting.equals(SaveSetting.SaveToDatabase)){
            dataTable = getFromDatabase();
        }else if(saveSetting.equals(SaveSetting.Both)){
            dataTable = merge();
        }
        return dataTable;
    }

    private DataTable getFromFirebase(){
        //TODO
        return new DataTable();
    }

    private DataTable getFromDatabase(){
        DataTable dataTable = new DataTable();
        generateQuery();
        Cursor cursor = getQueryHelperSQL().executeRawQuery(getGeneratedSql());
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            DataRow dataRow = new DataRow();
            for(Column column : getTable().getColumns().values()){
                DataColumn dataColumn = new DataColumn();
                dataColumn.setName(column.getName());
                dataColumn.setValue(getColumnValue(cursor, column));
                dataColumn.setDataType(column.getDataType());
                dataRow.addColumn(dataColumn);
            }
            dataTable.addRow(dataRow);
            cursor.moveToNext();
        }
        return dataTable;
    }

    private DataTable merge(){
        //TODO: Burayı çok iyi düşünmek lazım. SaveToFirebase ve SaveToDatabase dediğimiz zaman iki tarafa da atıyoruz kaydı.
        // Get dediğimiz zaman nereden alacağız? Firebase erişim sorunu olursa ne olacak? Aynı data hem db'de hem firebase'de varsa
        // da tek sefer getirilmeli.
        DataTable dataTable = getFromFirebase();
        dataTable.addRows(getFromDatabase().getRows());
        return new DataTable();
    }

    private Object getColumnValue(Cursor cursor, Column column){
        Object columnValue = null;
        ColumnDataType dataType = column.getDataType();
        switch (dataType){
            case UUID:
            case ForeignID:
                columnValue = getStringColumn(cursor, column.getName());
                break;
            case Integer:
                columnValue = getIntegerColumn(cursor, column.getName());
                break;
            case String:
                columnValue = getStringColumn(cursor, column.getName());
                break;
            case Boolean:
                columnValue = getBooleanColumnValue(cursor, column.getName());
                break;

        }
        return columnValue;
    }

    private String getStringColumn(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    private Integer getIntegerColumn(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    private Boolean getBooleanColumnValue(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName)).equals(FourConstants.Yes);
    }
    //endregion methods
}
