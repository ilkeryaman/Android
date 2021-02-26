package com.reset4.fourwork.library.interfaces;

import com.reset4.fourwork.library.enums.SaveSetting;

import java.util.Map;

/**
 * Created by eilkyam on 11.06.2016.
 */
public interface ITable {
    String name = null;
    Class entityType = null;
    Class businessObjectType = null;
    SaveSetting saveSetting = null;
    Map<String, IColumn> columns = null;
}
