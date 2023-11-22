package com.reset4.passlock;

import android.os.Bundle;
import android.view.View;

import com.reset4.fourwork.datalayer.BusinessObject;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.manifest.PassLockApp;
import com.reset4.passlock.security.Encryption;
import com.reset4.passlock.security.MasterPassword;
import com.reset4.passlock.ui.PLDialog;
import com.reset4.passlock.ui.PLEditText;
import com.reset4.passlockpro.R;

public class CreateDatabaseActivity extends PassLockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_database);
    }

    @Override
    public void onBackPressed() {
        redirectToWelcomeScreen();
    }

    public void redirectToWelcomeScreen(){
        PassLockApp app = (PassLockApp)getApplication();
        app.redirect(this, WelcomeActivity.class);
        finish();
    }

    public void createNewDatabase(View v){
         if(isValidEntry()){
             createMasterPassword();
             setMasterPassword();
             redirectToPasswordList();
         }
    }

    private boolean isValidEntry(){
        boolean isValidEntry = true;
        PLEditText masterPasswordEditText = (PLEditText) findViewById(R.id.masterPasswordEditText);
        PLEditText masterPasswordAgainEditText = (PLEditText) findViewById(R.id.masterPasswordAgainEditText);
        String masterPassword = masterPasswordEditText.getText().toString();
        String masterPasswordAgain = masterPasswordAgainEditText.getText().toString();
        if(masterPassword.length() < 6){
            isValidEntry = false;
            PLDialog dialog = new PLDialog(this, R.string.master_password_length, R.string.error);
            dialog.show();
        }
        else if(!masterPassword.equals(masterPasswordAgain)){
            isValidEntry = false;
            PLDialog dialog = new PLDialog(this, R.string.master_password_match, R.string.error);
            dialog.show();
        }

        return isValidEntry;
    }

    private void createMasterPassword(){
        try {
            PLEditText masterPasswordEditText = (PLEditText) findViewById(R.id.masterPasswordEditText);
            String masterPassword = masterPasswordEditText.getText().toString();
            PasswordInfoBO masterPasswordInfoBO = (PasswordInfoBO) BusinessObject.getBusinessObject(PasswordInfoBO.tableName,
                    getFourContext());
            masterPasswordInfoBO.getEntity().setAccountName(MasterPassword.MasterPasswordAccountName);
            masterPasswordInfoBO.getEntity().setPassword(Encryption.encrypt(masterPassword, masterPassword).getValue());
            masterPasswordInfoBO.save();
        } catch (FourException e) {
            e.printStackTrace();
        }
    }

    private void setMasterPassword(){
        PLEditText masterPasswordEditText = (PLEditText) findViewById(R.id.masterPasswordEditText);
        String masterPassword = masterPasswordEditText.getText().toString();
        super.setMasterPassword(masterPassword);
    }

    private void redirectToPasswordList(){
        PassLockApp app = (PassLockApp) getApplication();
        app.redirect(this, PasswordListActivity.class);
        finish();
    }
}
