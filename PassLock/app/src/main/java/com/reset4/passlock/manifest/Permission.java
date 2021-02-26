package com.reset4.passlock.manifest;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.reset4.passlock.R;
import com.reset4.passlock.backup.BackupManager;
import com.reset4.passlock.backup.OperationType;
import com.reset4.passlock.ui.PLDialog;

public class Permission {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean isVerificationNeeded = permission != PackageManager.PERMISSION_GRANTED;

        if (isVerificationNeeded) {
            requestPermission(activity);
        }

        return isVerificationNeeded;
    }

    public static void requestPermission(Activity activity){
        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
        );
    }

    public static void onRequestPermissionResult(Activity activity, int[] grantResults, BackupManager backupManager){
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay! Do the
            if(backupManager.getOperationType().equals(OperationType.BACKUP)){
                backupManager.backupDatabase(activity);
                PLDialog dialog = new PLDialog(activity, R.string.backup_database_successful, R.string.info);
                dialog.show();
            }else if(backupManager.getOperationType().equals(OperationType.RESTORE)){
                backupManager.restoreDatabase(activity);
            }
        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            PLDialog dialog = new PLDialog(activity, R.string.permission_message_error, R.string.error);
            dialog.show();
        }
        return;
    }
}
