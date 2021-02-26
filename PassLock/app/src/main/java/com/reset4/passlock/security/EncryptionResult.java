package com.reset4.passlock.security;

/**
 * Created by ilkery on 7.01.2017.
 */
public class EncryptionResult {
    private String value;
    private Class exceptionClass;
    private String exceptionMessage;

    public EncryptionResult(String value){
        this.value = value;
    }

    public EncryptionResult(String value, Class exceptionClass, String exceptionMessage){
        this.value = value;
        this.exceptionClass = exceptionClass;
        this.exceptionMessage = exceptionMessage;
    }

    public String getValue() {
        return value;
    }

    public Class getExceptionClass() {
        return exceptionClass;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setExceptionClass(Class exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
