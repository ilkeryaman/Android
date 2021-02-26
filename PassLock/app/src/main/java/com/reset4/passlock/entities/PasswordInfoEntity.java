package com.reset4.passlock.entities;

import com.reset4.fourwork.entity.Entity;

/**
 * Created by eilkyam on 17.12.2016.
 */
public class PasswordInfoEntity extends Entity {
    private String COL_AccountName = "AccountName";
    private String COL_UserId = "UserId";
    private String COL_Passwrd = "Passwrd";
    private String COL_Url = "Url";
    private String COL_Notes = "Notes";

    public String getAccountName(){
        return (String)getField(COL_AccountName).getValue();
    }

    public void setAccountName(String accountName){
        setFieldValue(COL_AccountName, accountName);
    }

    public String getUserId(){
        return (String)getField(COL_UserId).getValue();
    }

    public void setUserId(String userId){
        setFieldValue(COL_UserId, userId);
    }

    public String getPassword(){
        return (String)getField(COL_Passwrd).getValue();
    }

    public void setPassword(String password){
        setFieldValue(COL_Passwrd, password);
    }

    public String getUrl(){
        return (String)getField(COL_Url).getValue();
    }

    public void setUrl(String url){
        setFieldValue(COL_Url, url);
    }

    public String getNotes(){
        return (String)getField(COL_Notes).getValue();
    }

    public void setNotes(String notes){
        setFieldValue(COL_Notes, notes);
    }

}