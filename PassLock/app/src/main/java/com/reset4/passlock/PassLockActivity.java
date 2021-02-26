package com.reset4.passlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.library.enums.EntityStatus;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.manifest.PassLockApp;
import com.reset4.passlock.security.Encryption;
import com.reset4.passlock.security.MasterPassword;

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
