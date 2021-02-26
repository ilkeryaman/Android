package com.reset4.fourwork.constants;

/**
 * Created by eilkyam on 18.06.2016.
 */
public class Scripts {
    public final static String PrimaryKey = "primary key";
    public final static String And = "AND";
    public final static String EqualityConstant = " = ? ";
    public final static String Guid = "guid";
    public final static String Integer = "integer";
    public final static String Text = "text";
    public final static String Boolean = "boolean";

    public final static String CreateTableTemplate = "CREATE TABLE IF NOT EXISTS %s (%s)";
    public final static String CreateColumnTemplate = "%s %s";
    public final static String GetRecordTemplate = "SELECT * FROM %s WHERE %s";
}
