package com.reset4.fourwork.entity;

import java.util.UUID;

/**
 * Created by eilkyam on 11.06.2016.
 */
public class UniqueID {
    //region local variables
    private UUID id;
    private boolean isNew;
    //endregion local variables

    //region constructors
    public UniqueID(){
        setDefaultValue();
    }

    public UniqueID(String id){
        setId(UUID.fromString(id));
        setNew(false);
    }

    public UniqueID(UUID id){
        setId(id);
        setNew(false);
    }
    //endregion constructors

    //region properties
    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public boolean isNew() {
        return isNew;
    }

    private void setNew(boolean isNew) {
        this.isNew = isNew;
    }
    //endregion properties

    //region methods
    @Override
    public String toString() {
        return getId().toString();
    }

    private void setDefaultValue(){
        setId(UUID.randomUUID());
        setNew(true);
    }
    //endregion methods
}
