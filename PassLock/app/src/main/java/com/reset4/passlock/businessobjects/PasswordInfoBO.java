package com.reset4.passlock.businessobjects;

import com.reset4.fourwork.datalayer.BusinessObject;
import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.repository.Table;
import com.reset4.passlock.entities.PasswordInfoEntity;

/**
 * Created by eilkyam on 17.12.2016.
 */
public class PasswordInfoBO extends BusinessObject {
    //region local variables
    public static String tableName = "PasswordInfo";
    //endregion local variables

    //region constructors
    public PasswordInfoBO(FourContext fourContext, PasswordInfoEntity entity) {
        super(fourContext, entity);
    }

    public PasswordInfoBO(FourContext fourContext, Table table) throws FourException {
        super(fourContext, table);
    }
    //endregion constructors

    //region properties
    public PasswordInfoEntity getEntity() {
        return (PasswordInfoEntity) entity;
    }
    //endregion properties
}
