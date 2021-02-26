package com.reset4.passlock;

import android.app.Activity;
import android.os.Bundle;

import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.library.enums.EntityStatus;
import com.reset4.fourwork.manifest.FourApp;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.manifest.PassLockApp;
import com.reset4.passlock.security.MasterPassword;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redirect();
    }

    private void redirect(){
        Class componentClass;
        if(isFirstUsage()) {
            componentClass = WelcomeActivity.class;
        }else {
            componentClass = LoginActivity.class;
        }

        PassLockApp app = (PassLockApp) getApplication();
        app.redirect(this, componentClass);
        finish();
    }

    private boolean isFirstUsage(){
        boolean isFirstUsage = true;
        FourContext fourContext = FourApp.getFourContext();
        try {
            PasswordInfoBO masterPasswordInfoBO = (PasswordInfoBO)
                    fourContext.getBusinessObject(PasswordInfoBO.tableName, "AccountName", MasterPassword.MasterPasswordAccountName);
            if(masterPasswordInfoBO != null && masterPasswordInfoBO.getEntity().getEntityStatus().equals(EntityStatus.Loaded)) {
                isFirstUsage = false;
            }
        } catch (FourException e) {
            e.printStackTrace();
        }

        return isFirstUsage;
    }
}
