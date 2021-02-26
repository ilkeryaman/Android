package com.reset4.fourwork.engine.general;

import com.reset4.fourwork.constants.FourConstants;
import com.reset4.fourwork.constants.Punctuation;
import com.reset4.fourwork.entity.Entity;
import com.reset4.fourwork.repository.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by eilkyam on 11.06.2016.
 */
public class FourActivator {
    public static Object createInstance(Class _class) throws FourException {
        Object instance;
        try {
            instance = _class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new FourException(e.getClass().getName() + Punctuation.Colon + FourConstants.Space + e.getMessage());
        }
        return instance;
    }

    public static Object createInstance(Class _class, FourContext fourContext, Table table) throws FourException {
        Constructor constructor;
        try {
            constructor = _class.getConstructor(FourContext.class, Table.class);
        } catch (NoSuchMethodException e) {
            throw new FourException(e.getClass().getName() + Punctuation.Colon + FourConstants.Space + e.getMessage());
        }
        Object instance;
        try {
            instance = constructor.newInstance(fourContext, table);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException  e) {
            throw new FourException(e.getClass().getName() + Punctuation.Colon + FourConstants.Space + e.getMessage());
        }
        return instance;
    }

    public static Object createInstance(Class _class, FourContext fourContext, Entity entity) throws FourException {
        Constructor constructor;
        try {
            constructor = _class.getConstructor(FourContext.class, entity.getClass());
        } catch (NoSuchMethodException e) {
            throw new FourException(e.getClass().getName() + Punctuation.Colon + FourConstants.Space + e.getMessage());
        }
        Object instance;
        try {
            instance = constructor.newInstance(fourContext, entity);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException  e) {
            throw new FourException(e.getClass().getName() + Punctuation.Colon + FourConstants.Space + e.getMessage());
        }
        return instance;
    }
}
