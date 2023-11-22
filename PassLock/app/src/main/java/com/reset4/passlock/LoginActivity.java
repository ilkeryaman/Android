package com.reset4.passlock;

import android.os.Bundle;
import android.view.View;

import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.library.enums.EntityStatus;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.manifest.PassLockApp;
import com.reset4.passlock.security.Encryption;
import com.reset4.passlock.security.MasterPassword;
import com.reset4.passlock.ui.PLDialog;
import com.reset4.passlock.ui.PLEditText;
import com.reset4.passlockpro.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends PassLockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onBackPressed() {
        closeApplication();
    }

    private void closeApplication(){
        PassLockApp app = (PassLockApp)getApplication();
        app.showCloseApplicationWarning(this);
    }

    public void openDatabase(View v){
        if(isValidEntry())
        {
            setMasterPassword();
            redirectToPasswordList();
        }
    }

    private boolean isValidEntry(){
        boolean isValidEntry = true;
        PLEditText masterPasswordEditText = (PLEditText) findViewById(R.id.masterPasswordEditText);
        String masterPassword = masterPasswordEditText.getText().toString();
        if(masterPassword.length() < 6) {
            isValidEntry = false;
            PLDialog dialog = new PLDialog(this, R.string.invalid_password, R.string.error);
            dialog.show();
        }else {
            Map<String, Object> filters = new HashMap<>();
            filters.put("AccountName", MasterPassword.MasterPasswordAccountName);
            filters.put("Passwrd", Encryption.encrypt(masterPassword, masterPassword).getValue());
            try {
                PasswordInfoBO masterPasswordInfoBO = (PasswordInfoBO) getFourContext().loadByMultipleValue(PasswordInfoBO.tableName,
                        filters);
                if (masterPasswordInfoBO == null || !masterPasswordInfoBO.getEntity().getEntityStatus().equals(EntityStatus.Loaded)) {
                    isValidEntry = false;
                    PLDialog dialog = new PLDialog(this, R.string.invalid_password, R.string.error);
                    dialog.show();
                    masterPasswordEditText.setText("");
                }
            } catch (FourException e) {
                e.printStackTrace();
                isValidEntry = false;
                PLDialog dialog = new PLDialog(this, R.string.invalid_password, R.string.error);
                dialog.show();
            }
        }

        return isValidEntry;
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
