package com.reset4.passlock;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.passlock.manifest.PassLockApp;

/**
 * Created by ilkery on 31.12.2016.
 */
public class PassLockActivity extends AppCompatActivity {
    private FourContext fourContext;
    private static String masterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFourContext();
    }

    public FourContext getFourContext(){
        return fourContext;
    }

    public static String getMasterPassword(){
        return masterPassword;
    }

    public static void setMasterPassword(String masterPass){
        masterPassword = masterPass;
    }

    private void setFourContext(){
        PassLockApp app = (PassLockApp) getApplication();
        fourContext = app.getFourContext();
    }
}
