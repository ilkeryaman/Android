package com.reset4.fourwork.querybuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.reset4.fourwork.constants.FourConstants;
import com.reset4.fourwork.constants.ProjectConstants;
import com.reset4.fourwork.constants.Punctuation;
import com.reset4.fourwork.constants.Scripts;
import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.entity.Entity;
import com.reset4.fourwork.entity.Field;
import com.reset4.fourwork.library.enums.ColumnDataType;
import com.reset4.fourwork.library.enums.DBColumnDataType;
import com.reset4.fourwork.manifest.FourApp;
import com.reset4.fourwork.repository.Column;
import com.reset4.fourwork.repository.MetaData;
import com.reset4.fourwork.repository.Table;

import java.util.Map;
import java.util.SortedMap;

/**
 * Created by eilkyam on 16.06.2016.
 */
public class QueryHelperSQL extends SQLiteOpenHelper {
    //region constructors
    public QueryHelperSQL(Context context){
        super(context, ProjectConstants.DatabaseName, null, ProjectConstants.DatabaseVersion);
    }
    //endregion constructors

    //region methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        setupDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        setupDatabase(db);
    }

    private void setupDatabase(SQLiteDatabase db){
        FourContext fourContext = FourApp.getFourContext();
        MetaData metaData = fourContext.getMetaData();
        SortedMap<String, Table> tables = metaData.getTables();
        createPhysicalTables(db, tables);
        insertParameterValues(db, tables);

    }

    private void createPhysicalTables(SQLiteDatabase db, SortedMap<String, Table> tables){
        for(Table table : tables.values()){
            String tableName = table.getName();
            Map<String, Column> columns = table.getColumns();

            String columnsSql = FourConstants.EmptyString;
            for(Column column : columns.values()){
                String columnName = column.getName();
                String dataType = getDataType(column.getDataType());
                columnsSql +=  Punctuation.Comma + String.format(Scripts.CreateColumnTemplate, columnName, dataType);
                if(column.isPrimaryKey()){
                    columnsSql += FourConstants.Space + Scripts.PrimaryKey;
                }
            }
            columnsSql = columnsSql.replaceFirst(Punctuation.Comma, FourConstants.EmptyString);

            String sql = String.format(Scripts.CreateTableTemplate, tableName, columnsSql);
            db.execSQL(sql);
        }
    }

    private void insertParameterValues(SQLiteDatabase db, SortedMap<String, Table> tables){
        // Sadece burada değil genel olarak yapılacaklar
        // TODO: ForeignKey kolonların metadatadan okunurken foreign key table alması
        // TODO: ForeignID değişkenine getIdCode ve setIdCode methodu yazılması
        // TODO: Parametre tablolarının cachelenmesi
        // TODO: Parametre değerlerinin db'ye insert edilmesi
    }

    private String getDataType(ColumnDataType columnDataType){
        String dataType = null;
        switch (columnDataType){
            case UUID:
            case ForeignID:
                dataType = DBColumnDataType.Guid.name();
                break;
            case Integer:
                dataType = DBColumnDataType.Integer.name();
                break;
            case String:
                dataType = DBColumnDataType.Text.name();
                break;
            case Boolean:
                dataType = DBColumnDataType.Boolean.name();
                break;
        }
        return dataType;
    }

    public void insert(Entity entity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Map<String, Field> fields = entity.getFields();
        for(Field field : fields.values()){
            String fieldName = field.getFieldColumn().getName();
            switch (field.getFieldColumn().getDataType()){
                case UUID:
                case ForeignID:
                    contentValues.put(fieldName, field.getValue() == null ? null : field.getValue().toString());
                    break;
                case String:
                    contentValues.put(fieldName, (String)field.getValue());
                    break;
                case Integer:
                    contentValues.put(fieldName, (Integer)field.getValue());
                    break;
                case Boolean:
                    contentValues.put(fieldName, (Boolean)field.getValue());
                    break;
            }
        }
        db.insert(entity.getEntityName(), null, contentValues);
    }

    public void update(Entity entity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Map<String, Field> fields = entity.getFields();
        for(Field field : fields.values()){
            String fieldName = field.getFieldColumn().getName();
            switch (field.getFieldColumn().getDataType()){
                case UUID:
                case ForeignID:
                    contentValues.put(fieldName, field.getValue() == null ? null : field.getValue().toString());
                    break;
                case String:
                    contentValues.put(fieldName, (String)field.getValue());
                    break;
                case Integer:
                    contentValues.put(fieldName, (Integer)field.getValue());
                    break;
                case Boolean:
                    contentValues.put(fieldName, (Boolean)field.getValue());
                    break;
            }
        }

        String primaryKeyColumn = entity.getPrimaryKeyField().getFieldColumn().getName();
        String primaryKeyColumnValue = entity.getPrimaryKeyField().getValue().toString();
        db.update(entity.getEntityName(), contentValues, primaryKeyColumn + " = ? ", new String[]{primaryKeyColumnValue});
    }

    public void delete(Entity entity){
        SQLiteDatabase db = this.getWritableDatabase();
        String primaryKeyColumn = entity.getPrimaryKeyField().getFieldColumn().getName();
        String primaryKeyColumnValue = entity.getPrimaryKeyField().getValue().toString();
        db.delete(entity.getEntityName(), primaryKeyColumn + Scripts.EqualityConstant, new String[] {primaryKeyColumnValue});
    }

    public Cursor executeRawQuery(String sql){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null );
    }
    //endregion methods
}
