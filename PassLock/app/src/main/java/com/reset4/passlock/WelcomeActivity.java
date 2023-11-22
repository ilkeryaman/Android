package com.reset4.passlock;

import android.os.Bundle;
import android.view.View;

import com.reset4.passlock.backup.BackupManager;
import com.reset4.passlock.backup.OperationType;
import com.reset4.passlock.manifest.PassLockApp;
import com.reset4.passlock.manifest.Permission;
import com.reset4.passlockpro.R;

import java.util.HashMap;

public class WelcomeActivity extends PassLockActivity {

    BackupManager backupManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void onBackPressed() {
        closeApplication();
    }

    private void closeApplication(){
        PassLockApp app = (PassLockApp)getApplication();
        app.showCloseApplicationWarning(this);
    }

    public void redirectToCreateNewDatabase(View v){
        PassLockApp app = (PassLockApp)getApplication();
        app.redirect(this, CreateDatabaseActivity.class);
        finish();
    }

    public void redirectToRestoreDatabaseActivity(View v){
        backupManager = new BackupManager(getFourContext(), super.getMasterPassword(), OperationType.RESTORE);
        boolean isVerificationNeeded = Permission.verifyStoragePermissions(this);
        if(!isVerificationNeeded) {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("ComesFrom", this.getLocalClassName());
            PassLockApp app = (PassLockApp) getApplication();
            app.redirect(this, RestoreDatabaseActivity.class, parameters);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Permission.onRequestPermissionResult(this, grantResults, backupManager);
    }
}
