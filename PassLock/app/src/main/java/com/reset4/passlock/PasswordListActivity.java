package com.reset4.passlock;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.reset4.fourwork.datalayer.BOCollection;
import com.reset4.fourwork.datalayer.BusinessObject;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.passlock.ad.AdManager;
import com.reset4.passlock.adapter.PasswordListAdapter;
import com.reset4.passlock.backup.BackupManager;
import com.reset4.passlock.backup.OperationType;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.comparator.PasswordInfoComparator;
import com.reset4.passlock.manifest.PassLockApp;
import com.reset4.passlock.manifest.Permission;
import com.reset4.passlock.security.MasterPassword;
import com.reset4.passlock.ui.PLDialog;
import com.reset4.passlock.ui.PLTextView;
import com.reset4.passlock.ui.UIHelper;
import com.reset4.passlockpro.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PasswordListActivity extends PassLockActivity {

    BackupManager backupManager;
    Map<String, Integer> mapIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);
        prepareListView();
        manageAd();
    }

    @Override
    public void onBackPressed() {
        closeApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(AdManager.isProfessionalEdition()){
            new MenuInflater(this).inflate(R.menu.menu_passwordlist_pro, menu);
        }else {
            new MenuInflater(this).inflate(R.menu.menu_passwordlist, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addPassword){
            redirectToAddPassword();
            return true;
        } else if(item.getItemId() == R.id.buyPro){
            openPassLockProWebPage();
            return true;
        } else if(item.getItemId() == R.id.more){
            openMoreMenu();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void prepareListView(){
        List<PasswordInfoBO> passwordInfoBOList = new ArrayList<>();
        try {
            Map<String, Object> filters = new HashMap<>();
            filters.put("1", 1);
            BOCollection passwordInfoBOColl = BOCollection.loadByMultipleValue(getFourContext(),
                    PasswordInfoBO.tableName, filters);
            Collections.sort(passwordInfoBOColl.getItems(), new PasswordInfoComparator());
            for(BusinessObject bo : passwordInfoBOColl.getItems())
            {
                PasswordInfoBO passwordInfoBO = (PasswordInfoBO) bo;
                if(passwordInfoBO.getEntity().getAccountName().equals(MasterPassword.MasterPasswordAccountName)){
                    continue;
                }
                passwordInfoBOList.add(passwordInfoBO);
            }
        } catch (FourException e) {
            e.printStackTrace();
        }

        PasswordListAdapter adapter = new PasswordListAdapter(this, passwordInfoBOList);
        ListView listView = (ListView) findViewById(R.id.password_listview);
        PLTextView emptyTextView = (PLTextView) findViewById(R.id.emptyTextView);
        listView.setEmptyView(emptyTextView);
        //listView.setDivider(DeprecatedMethod.getDrawable(activity, UIOperations.getDividerStyle(activity)));
        listView.setAdapter(adapter);
        listView.setSelection(UIHelper.ScrollPosition);
        prepareIndexList(passwordInfoBOList, listView);

    }

    private void prepareIndexList(List<PasswordInfoBO> passwordInfoBOList, ListView listView){
        getIndexList(passwordInfoBOList);
        displayIndex(listView);
    }

    private void getIndexList(List<PasswordInfoBO> passwordInfoBOList) {
        mapIndex = new LinkedHashMap<>();

        for (int i = 0; i < passwordInfoBOList.size(); i++) {
            PasswordInfoBO passwordInfoBO = passwordInfoBOList.get(i);
            String index = passwordInfoBO.getEntity().getAccountName().substring(0, 1).toLowerCase();

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private void displayIndex(final ListView listView) {
        LinearLayout indexLayout = findViewById(R.id.side_index);
        indexLayout.removeAllViews();

        TextView textView;
        List<String> indexList = new ArrayList<>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    TextView selectedIndex = (TextView) v;
                    listView.setSelection(mapIndex.get(selectedIndex.getText()));
                }
            });
            indexLayout.addView(textView);
        }
    }

    private void closeApplication(){
        PassLockApp app = (PassLockApp)getApplication();
        app.showCloseApplicationWarning(this);
    }

    private void redirectToAddPassword(){
        PassLockApp app = (PassLockApp) getApplication();
        app.redirect(this, PasswordActivity.class);
    }

    private void openPassLockProWebPage(){
        AdManager adManager = new AdManager(this);
        adManager.openPassLockProWebPage();
    }

    private void openMoreMenu(){
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.dummyTextView), Gravity.RIGHT);
        popup.getMenuInflater().inflate(R.menu.menu_additional, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.changeMasterPassword){
                    redirectToChangeMasterPassword();
                    return true;
                } else if(item.getItemId() == R.id.restoreDatabase){
                    restoreDatabase();
                    return true;
                } else if(item.getItemId() == R.id.backupDatabase){
                    backupDatabase();
                    return true;
                } else if(item.getItemId() == R.id.deleteDatabase){
                    deleteDatabase();
                    return true;
                } else {
                    return true;
                }
            }
        });
    }

    private void redirectToChangeMasterPassword(){
        PassLockApp app = (PassLockApp) getApplication();
        app.redirect(this, ChangePasswordActivity.class);
    }

    private void restoreDatabase(){
        backupManager = new BackupManager(getFourContext(), super.getMasterPassword(), OperationType.RESTORE);
        boolean isVerificationNeeded = Permission.verifyStoragePermissions(this);
        if(!isVerificationNeeded) {
            backupManager.restoreDatabase(this);
        }
    }

    private void backupDatabase(){
        backupManager = new BackupManager(getFourContext(), super.getMasterPassword(), OperationType.BACKUP);
        boolean isVerificationNeeded = Permission.verifyStoragePermissions(this);
        if(!isVerificationNeeded) {
            backupManager.backupDatabase(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Permission.onRequestPermissionResult(this, grantResults, backupManager);
    }

    private void deleteDatabase(){
        PLDialog dialog = new PLDialog(this, R.string.delete_database_warning_message, R.string.delete_database_warning_title);
        dialog.setNegativeButton(R.string.button_no, null);
        dialog.setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteAllPasswords();
                PasswordListActivity.this.setMasterPassword(null);
                redirectToWelcomeActivity();
            }
        });
        dialog.show();
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

    private void redirectToWelcomeActivity(){
        PassLockApp app = (PassLockApp)getApplication();
        app.redirect(this, WelcomeActivity.class);
        finish();
    }

    private void manageAd(){
        AdManager adManager = new AdManager(this);
        adManager.prepareBanner();
    }
}
