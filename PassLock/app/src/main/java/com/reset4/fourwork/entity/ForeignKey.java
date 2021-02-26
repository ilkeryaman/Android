package com.reset4.fourwork.entity;

import java.util.UUID;

/**
 * Created by eilkyam on 19.06.2016.
 */
public class ForeignKey {
    //region local variables
    private UniqueID uniqueID;
    //endregion local variables

    //region constructors
    public ForeignKey(String id){
        setUniqueID(new UniqueID(id));
    }

    public ForeignKey(UUID id){
        setUniqueID(new UniqueID(id));
    }

    public ForeignKey(UniqueID uniqueID){
        setUniqueID(uniqueID);
    }
    //endregion constructors

    //region properties
    public UniqueID getUniqueID() {
        return uniqueID;
    }

    private void setUniqueID(UniqueID uniqueID) {
        this.uniqueID = uniqueID;
    }
    //endregion properties

    //region methods
    @Override
    public String toString() {
        return getUniqueID().toString();
    }
    //endregion methods
}
