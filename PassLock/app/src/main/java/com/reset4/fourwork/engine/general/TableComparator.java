package com.reset4.fourwork.engine.general;

import java.util.Comparator;

/**
 * Created by eilkyam on 12.06.2016.
 */
class TableComparator implements Comparator<String> {

    @Override // java.util.Comparator.compare
    public int compare(String tableName1,
                       String tableName2) {
        return tableName1.compareTo(tableName2);
    }
}
