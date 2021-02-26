package com.reset4.fourwork.engine.general;

import android.content.Context;


import com.reset4.fourwork.constants.FourConstants;
import com.reset4.fourwork.constants.Messages;
import com.reset4.fourwork.constants.MetadataConstants;
import com.reset4.fourwork.constants.ProjectConstants;
import com.reset4.fourwork.constants.Punctuation;
import com.reset4.fourwork.library.enums.ColumnDataType;
import com.reset4.fourwork.library.enums.SaveSetting;
import com.reset4.fourwork.repository.Column;
import com.reset4.fourwork.repository.MetaData;
import com.reset4.fourwork.repository.Table;
import com.reset4.passlock.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by eilkyam on 12.06.2016.
 */
public class Initializer {

    public static FourContext initialize(Context context) throws FourException {
        MetaData metaData = refreshMetadata(context);
        FourContext fourContext = new FourContext(metaData, context);
        return fourContext;
    }

    public static MetaData refreshMetadata(Context context) throws FourException {
        MetaData metaData = new MetaData();
        initializeTables(context, metaData);
        initializeBusinessObjectsAndEntities(metaData);
        return metaData;
    }

    private static void initializeTables(Context context, MetaData metadata) throws FourException {
        Comparator comparator = new TableComparator();
        SortedMap<String, Table> tables = new TreeMap(comparator);

        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new InputSource(context.getResources().openRawResource(R.raw.metadata)));
            document.getDocumentElement().normalize();

            NodeList metadataTagList = document.getElementsByTagName(MetadataConstants.Tag_Matadata);
            Node metadataTag = metadataTagList.item(0);
            Node tablesTag = XmlOperations.getChildNodeOfTypeElement(metadataTag, MetadataConstants.Tag_Tables);
            NodeList tableTagList = tablesTag.getChildNodes();
            Map<String, String> foreignKeyTableMatch = new HashMap<>();
            for (int i = 0; i < tableTagList.getLength(); i++) {
                if(Node.ELEMENT_NODE != tableTagList.item(i).getNodeType()){
                    continue;
                }
                Element tableTag = (Element) tableTagList.item(i);
                readTableAttributes(tableTag, tables, foreignKeyTableMatch);
            }
            setForeignKeyTables(tables, foreignKeyTableMatch);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new FourException(e.getClass().getName() + Punctuation.Colon + FourConstants.Space + Messages.ClassNotFound);
        }

        metadata.setTables(tables);
    }

    private static void readTableAttributes(Element tableTag, SortedMap<String, Table> tables, Map<String, String> foreignKeyTableMatch) throws FourException, ClassNotFoundException {
        String tableName = tableTag.getAttribute(MetadataConstants.Attribute_Name);
        String entityName = tableTag.getAttribute(MetadataConstants.Attribute_EntityName);
        String businessObjectName = tableTag.getAttribute(MetadataConstants.Attribute_BusinessObjectName);
        String saveSetting = tableTag.getAttribute(MetadataConstants.Attribute_SaveSetting);

        if(!tableName.isEmpty() && !tables.containsKey(tableName)){
            Table table = new Table();
            table.setName(tableName);
            if(entityName.isEmpty()){
                throw new FourException(Messages.EntityNameNotFoundInMetadata + tableName);
            }else {
                String className = ProjectConstants.EntityLibraryPackageName + Punctuation.Dot + entityName;
                table.setEntityType(Class.forName(className));
            }
            if(businessObjectName.isEmpty()){
                throw new FourException(Messages.BusinessObjectNameNotFoundInMetadata + tableName);
            }else{
                String className = ProjectConstants.BusinessObjectLibraryPackageName + Punctuation.Dot + businessObjectName;
                table.setBusinessObjectType(Class.forName(className));
            }
            if(saveSetting.isEmpty()){
                table.setSaveSetting(SaveSetting.SaveToDatabase);
            }else{
                table.setSaveSetting(MetaData.getSaveSetting(saveSetting));
            }
            readColumns(tableTag, table, foreignKeyTableMatch);
            tables.put(tableName, table);
        }
    }

    private static void readColumns(Element tableTag, Table table, Map<String, String> foreignKeyTableMatch) throws FourException {
        table.setColumns(new HashMap<String, Column>());
        Node columnsTag = XmlOperations.getChildNodeOfTypeElement(tableTag, MetadataConstants.Tag_Columns);
        NodeList columnTagList = columnsTag.getChildNodes();

        for (int i = 0; i < columnTagList.getLength(); i++) {
            if(Node.ELEMENT_NODE != columnTagList.item(i).getNodeType()){
                continue;
            }
            Element columnTag = (Element) columnTagList.item(i);
            String columnName = columnTag.getAttribute(MetadataConstants.Attribute_Name);
            if(columnName.isEmpty()) {
                throw new FourException(Messages.ColumnNameNotFoundInMetadata + table.getName());
            }else {
                String columnDataType = columnTag.getAttribute(MetadataConstants.Attribute_Type);
                ColumnDataType metadataColumnDataType = MetaData.getColumnDataType(columnDataType);
                Column column = new Column();
                column.setColumnTable(table);
                column.setName(columnName);
                column.setDataType(metadataColumnDataType);
                if(columnName.equals(MetadataConstants.PrimaryKeyFieldName)){
                    column.setPrimaryKey(true);
                }
                if(metadataColumnDataType.equals(ColumnDataType.ForeignID)){
                    String foreignKeyTableName = columnTag.getAttribute(MetadataConstants.Attribute_FKTableName);
                    String keyName = table.getName() + Punctuation.Colon + column.getName();
                    foreignKeyTableMatch.put(keyName, foreignKeyTableName);
                }
                table.getColumns().put(columnName, column);
            }
        }

        if(!table.getColumns().containsKey(MetadataConstants.PrimaryKeyFieldName)){
            String columnDataType = MetadataConstants.UUID;
            String columnName = MetadataConstants.PrimaryKeyFieldName;
            Column column = new Column();
            column.setColumnTable(table);
            column.setName(columnName);
            column.setDataType(MetaData.getColumnDataType(columnDataType));
            column.setPrimaryKey(true);
            table.getColumns().put(columnName, column);
        }
    }

    private static void setForeignKeyTables(SortedMap<String, Table> tables, Map<String, String> foreignKeyTableMatch){
        for(Map.Entry<String, String> foreignKeySet : foreignKeyTableMatch.entrySet()){
            String keyName = foreignKeySet.getKey();
            String tableName = keyName.split(Punctuation.Colon)[0];
            String columnName = keyName.split(Punctuation.Colon)[1];
            String foreignKeyTableName = foreignKeySet.getValue();
            Table foreignKeyTable = tables.get(foreignKeyTableName);
            tables.get(tableName).getColumns().get(columnName).setForeignKeyTable(foreignKeyTable);
        }
    }

    private static void initializeBusinessObjectsAndEntities(MetaData metaData){
        Map<String, Class> entities = new HashMap<>();
        Map<String, Class> businessObjects = new HashMap<>();

        SortedMap<String, Table> tables = metaData.getTables();
        for(Table table : tables.values()){
            String tableName = table.getName();
            entities.put(tableName, table.getEntityType());
            businessObjects.put(tableName, table.getBusinessObjectType());
        }

        metaData.setEntities(entities);
        metaData.setBusinessObjects(businessObjects);
    }
}
