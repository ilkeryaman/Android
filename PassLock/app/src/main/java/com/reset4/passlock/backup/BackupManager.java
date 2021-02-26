package com.reset4.passlock.backup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Path;
import android.os.Environment;
import android.support.v7.view.menu.ActionMenuItem;
import android.widget.TextView;

import com.reset4.fourwork.datalayer.BOCollection;
import com.reset4.fourwork.datalayer.BusinessObject;
import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.passlock.R;
import com.reset4.passlock.RestoreDatabaseActivity;
import com.reset4.passlock.WelcomeActivity;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.manifest.PassLockApp;
import com.reset4.passlock.ui.PLDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ilkery on 7.01.2017.
 */
public class BackupManager {

    private FourContext fourContext;
    private String masterPassword;
    private String path;
    public final static String backupDbFileName = "/r4passlock.xml";
    private OperationType operationType;

    public BackupManager(FourContext fourContext, String masterPassword, OperationType operationType){
        this.fourContext = fourContext;
        this.masterPassword = masterPassword;
        this.operationType = operationType;
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + backupDbFileName;
    }

    public void restoreDatabase(final Activity activity){
        PLDialog dialog = new PLDialog(activity, R.string.restore_database_warning, R.string.warning);
        dialog.setNegativeButton(R.string.button_cancel, null);
        dialog.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                redirectToRestoreDatabaseActivity(activity);
            }
        });
        dialog.show();
    }

    private void redirectToRestoreDatabaseActivity(Activity activity){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("ComesFrom", activity.getLocalClassName());
        PassLockApp app = (PassLockApp) activity.getApplication();
        app.redirect(activity, RestoreDatabaseActivity.class, parameters);
        activity.finish();
    }

    public void backupDatabase(final Activity activity){
        if(isRestoreFileExist()){
            PLDialog dialog = new PLDialog(activity, R.string.backup_database_warning, R.string.warning);
            dialog.setNegativeButton(R.string.button_no, null);
            dialog.setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    writeAccountsToXml();
                    PLDialog dialog = new PLDialog(activity, R.string.backup_database_successful, R.string.info);
                    dialog.show();
                }
            });
            dialog.show();
        }else {
            writeAccountsToXml();
            PLDialog dialog = new PLDialog(activity, R.string.backup_database_successful, R.string.info);
            dialog.show();
        }
    }

    private void writeAccountsToXml(){
        XmlFileOperator operator = new XmlFileOperator(fourContext, masterPassword);
        operator.writeXml(path, getPasswordInfoBOList());
    }

    public static boolean isRestoreFileExist(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + BackupManager.backupDbFileName;
        File file = new File(path);
        return file.exists();
    }

    private List<PasswordInfoBO> getPasswordInfoBOList(){
        List<PasswordInfoBO> passwordInfoBOList = new ArrayList<>();
        try {
            Map<String, Object> filters = new HashMap<>();
            filters.put("1", 1);
            BOCollection passwordInfoBOColl = BOCollection.loadByMultipleValue(fourContext,
                    PasswordInfoBO.tableName, filters);
            for(BusinessObject bo : passwordInfoBOColl.getItems())
            {
                PasswordInfoBO passwordInfoBO = (PasswordInfoBO) bo;
                passwordInfoBOList.add(passwordInfoBO);
            }
        } catch (FourException e) {
            e.printStackTrace();
        }

        return passwordInfoBOList;
    }

    public OperationType getOperationType(){
        return operationType;
    }

}
