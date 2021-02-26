package com.reset4.fourwork.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eilkyam on 19.06.2016.
 */
public class DataRow {
    //region local variables
    private Map<String, DataColumn> columns;
    //endregion local variables

    //region properties
    public Map<String, DataColumn> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, DataColumn> columns) {
        this.columns = columns;
    }
    //endregion properties

    //region methods
    public void addColumn(DataColumn dataColumn){
        if(getColumns() == null){
            setColumns(new HashMap<String, DataColumn>());
        }
        getColumns().put(dataColumn.getName(), dataColumn);
    }
    //endregion methods
}
