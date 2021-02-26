package com.reset4.fourwork.manifest;

import android.app.Application;
import android.content.Context;

import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.engine.general.Initializer;

/**
 * Created by eilkyam on 16.06.2016.
 */
public class FourApp extends Application {
    //region local variables
    private static FourContext fourContext;
    //endregion local variables

    //region properties
    public static FourContext getFourContext() {
        return fourContext;
    }

    private void setFourContext(FourContext fourContext) {
        this.fourContext = fourContext;
    }
    //endregion properties

    //region methods
    @Override
    public void onCreate() {
        super.onCreate();
        setFourContext();
    }

    private void setFourContext(){
        try {
            Context context = getApplicationContext();
            setFourContext(Initializer.initialize(context));
        } catch (FourException e) {
            e.printStackTrace();
        }
    }

    //endregion methods
}
