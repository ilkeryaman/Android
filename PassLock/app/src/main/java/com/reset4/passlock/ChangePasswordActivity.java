package com.reset4.passlock;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.reset4.fourwork.datalayer.BOCollection;
import com.reset4.fourwork.datalayer.BusinessObject;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.library.enums.EntityStatus;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.comparator.PasswordInfoComparator;
import com.reset4.passlock.security.Encryption;
import com.reset4.passlock.security.MasterPassword;
import com.reset4.passlock.ui.PLDialog;
import com.reset4.passlock.ui.PLEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangePasswordActivity extends PassLockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }

    public void changePassword(View v){
        if(isValidEntry()) {
            changePassword();
        }
    }

    private boolean isValidEntry(){
        boolean isValidEntry = true;
        PLEditText existingMasterPasswordEditText = (PLEditText) findViewById(R.id.existingMasterPasswordEditText);
        PLEditText newMasterPasswordEditText = (PLEditText) findViewById(R.id.newMasterPasswordEditText);
        PLEditText newMasterPasswordAgainEditText = (PLEditText) findViewById(R.id.newMasterPasswordAgainEditText);
        String existingMasterPassword = existingMasterPasswordEditText.getText().toString();
        String newMasterPassword = newMasterPasswordEditText.getText().toString();
        String newMasterPasswordAgain = newMasterPasswordAgainEditText.getText().toString();
        if(!validateExistingPassword(existingMasterPassword)) {
            isValidEntry = false;
            PLDialog dialog = new PLDialog(this, R.string.invalid_password, R.string.error);
            dialog.show();
        }
        else if(newMasterPassword.length() < 6){
            isValidEntry = false;
            PLDialog dialog = new PLDialog(this, R.string.master_password_length, R.string.error);
            dialog.show();
        }
        else if(!newMasterPassword.equals(newMasterPasswordAgain)){
            isValidEntry = false;
            PLDialog dialog = new PLDialog(this, R.string.master_password_match, R.string.error);
            dialog.show();
        }

        return isValidEntry;
    }

    private boolean validateExistingPassword(String masterPassword){
        boolean isValid = false;
        Map<String, Object> filters = new HashMap<>();
        filters.put("AccountName", MasterPassword.MasterPasswordAccountName);
        filters.put("Passwrd", Encryption.encrypt(masterPassword, masterPassword).getValue());
        try {
            PasswordInfoBO masterPasswordInfoBO = (PasswordInfoBO) getFourContext().loadByMultipleValue(PasswordInfoBO.tableName,
                    filters);
            if(masterPasswordInfoBO != null && masterPasswordInfoBO.getEntity().getEntityStatus().equals(EntityStatus.Loaded)){
                isValid = true;
            }
        }catch (FourException e) {
            e.printStackTrace();
            isValid = false;
        }
        return isValid;
    }

    private void changePassword(){
        PLEditText newMasterPasswordEditText = (PLEditText) findViewById(R.id.newMasterPasswordEditText);
        String newMasterPassword = newMasterPasswordEditText.getText().toString();
        updateAllPasswords(newMasterPassword);
        super.setMasterPassword(newMasterPassword);
        showSuccessDialog();
    }

    private void updateAllPasswords(String newMasterPassword){
        try {
            Map<String, Object> filters = new HashMap<>();
            filters.put("1", 1);
            BOCollection passwordInfoBOColl = BOCollection.loadByMultipleValue(getFourContext(),
                    PasswordInfoBO.tableName, filters);
            for(BusinessObject bo : passwordInfoBOColl.getItems())
            {
                PasswordInfoBO passwordInfoBO = (PasswordInfoBO) bo;
                String currentPassword = Encryption.decrypt(super.getMasterPassword(), passwordInfoBO.getEntity().getPassword()).getValue();
                if(passwordInfoBO.getEntity().getAccountName().equals(MasterPassword.MasterPasswordAccountName)){
                    passwordInfoBO.getEntity().setPassword(Encryption.encrypt(newMasterPassword, newMasterPassword).getValue());
                }else{
                    passwordInfoBO.getEntity().setPassword(Encryption.encrypt(newMasterPassword, currentPassword).getValue());
                }
                passwordInfoBO.save();
            }
        } catch (FourException e) {
            e.printStackTrace();
        }
    }

    private void showSuccessDialog(){
        PLDialog dialog = new PLDialog(this, R.string.master_password_change_success, R.string.info);
        dialog.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBackPressed();
            }
        });
        dialog.show();
    }
}
