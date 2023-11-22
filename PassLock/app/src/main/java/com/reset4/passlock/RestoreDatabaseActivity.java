package com.reset4.passlock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;

import com.reset4.fourwork.datalayer.BOCollection;
import com.reset4.fourwork.datalayer.BusinessObject;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.passlock.backup.BackupManager;
import com.reset4.passlock.backup.XmlFileOperator;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.manifest.PassLockApp;
import com.reset4.passlock.ui.PLDialog;
import com.reset4.passlock.ui.PLEditText;
import com.reset4.passlockpro.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestoreDatabaseActivity extends PassLockActivity {

    String comesFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_database);
        setComesFrom();
        checkRestoreFile();
    }

    @Override
    public void onBackPressed() {
        if(comesFrom.equals("WelcomeActivity")){
            redirectToWelcomeActivityScreen();
        }else if(comesFrom.equals("PasswordListActivity")) {
            redirectToPasswordListActivityScreen();
        }
    }

    private void checkRestoreFile(){
        if(!BackupManager.isRestoreFileExist()){
            PLDialog dialog = new PLDialog(this, R.string.restore_database_not_found, R.string.error);
            dialog.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBackPressed();
                }
            });
            dialog.show();
        }
    }

    private void redirectToWelcomeActivityScreen(){
        PassLockApp app = (PassLockApp)getApplication();
        app.redirect(this, WelcomeActivity.class);
        finish();
    }

    private void redirectToPasswordListActivityScreen(){
        PassLockApp app = (PassLockApp)getApplication();
        app.redirect(this, PasswordListActivity.class);
        finish();
    }

    private void setComesFrom(){
        Intent intent = getIntent();
        comesFrom = intent.getStringExtra("ComesFrom");
    }

    public void openDatabase(View v){
        if(isValidEntry()) {
            PLEditText masterPasswordEditText = (PLEditText) findViewById(R.id.masterPasswordEditText);
            String masterPassword = masterPasswordEditText.getText().toString();
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + BackupManager.backupDbFileName;
            XmlFileOperator operator = new XmlFileOperator(getFourContext(), masterPassword);
            List<PasswordInfoBO> passwordInfoBOList = operator.readXml(path);
            if (passwordInfoBOList.size() == 0) {
                PLDialog dialog = new PLDialog(this, R.string.invalid_password, R.string.error);
                dialog.show();
                masterPasswordEditText.setText("");
            } else {
                deleteAllPasswords();
                restoreDatabase(passwordInfoBOList);
                super.setMasterPassword(masterPassword);
                redirectToPasswordListActivityScreen();
            }
        }
    }

    private boolean isValidEntry(){
        boolean isValidEntry = true;
        PLEditText masterPasswordEditText = (PLEditText) findViewById(R.id.masterPasswordEditText);
        String masterPassword = masterPasswordEditText.getText().toString();
        if(masterPassword.length() < 6){
            isValidEntry = false;
            PLDialog dialog = new PLDialog(this, R.string.invalid_password, R.string.error);
            dialog.show();
        }
        return isValidEntry;
    }

    private void deleteAllPasswords(){
        try {
            Map<String, Object> filters = new HashMap<>();
            filters.put("1", 1);
            BOCollection passwordInfoBOColl = BOCollection.loadByMultipleValue(getFourContext(),
                    PasswordInfoBO.tableName, filters);
            for(BusinessObject bo : passwordInfoBOColl.getItems())
            {
                PasswordInfoBO passwordInfoBO = (PasswordInfoBO) bo;
                passwordInfoBO.delete();
            }
        } catch (FourException e) {
            e.printStackTrace();
        }
    }

    private void restoreDatabase(List<PasswordInfoBO> passwordInfoBOList){
        for(PasswordInfoBO passwordInfoBO : passwordInfoBOList){
            try {
                passwordInfoBO.save();
            } catch (FourException e) {
                e.printStackTrace();
            }
        }
    }
}