package com.reset4.fourwork.data;

import java.util.ArrayList;

/**
 * Created by eilkyam on 19.06.2016.
 */
public class DataTable {
    //region local variables
    ArrayList<DataRow> rows;
    //endregion local variables

    //region properties
    public ArrayList<DataRow> getRows() {
        if(rows == null){
            rows = new ArrayList<>();
        }
        return rows;
    }

    public void setRows(ArrayList<DataRow> rows) {
        this.rows = rows;
    }
    //endregion properties

    //region methods
    public void addRow(DataRow dataRow){
        getRows().add(dataRow);
    }

    public void addRows(ArrayList<DataRow> rows){
        getRows().addAll(rows);
    }
    //endregion methods
}
