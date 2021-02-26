package com.reset4.fourwork.library.interfaces;

import com.reset4.fourwork.library.enums.ColumnDataType;

/**
 * Created by eilkyam on 15.06.2016.
 */
public interface IColumn {
    String name = null;
    ITable columnTable = null;
    ITable foreignKeyTable = null;
    boolean primaryKey = false;
    ColumnDataType dataType = null;
}
