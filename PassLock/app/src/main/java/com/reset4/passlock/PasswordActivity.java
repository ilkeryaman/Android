package com.reset4.passlock;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.reset4.fourwork.datalayer.BusinessObject;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.fourwork.entity.UniqueID;
import com.reset4.fourwork.library.enums.EntityStatus;
import com.reset4.passlock.ad.AdManager;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.security.Encryption;
import com.reset4.passlock.security.MasterPassword;
import com.reset4.passlock.sharedpreference.SharedPrefenceManager;
import com.reset4.passlock.ui.PLDialog;
import com.reset4.passlock.ui.PLEditText;
import com.reset4.passlock.ui.PLTextView;
import com.reset4.passlock.ui.ScreenMode;
import com.reset4.passlock.ui.UIHelper;

public class PasswordActivity extends PassLockActivity {

    private PasswordInfoBO passwordInfoBO;
    private ScreenMode screenMode;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        pre();
        manageAd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isLoadedMode()) {
            this.menu = menu;
            new MenuInflater(this).inflate(R.menu.menu_password, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editPassword:
                screenMode = screenMode.EDITABLE;
                determineElementAttributes();
                showOverflowMenu(false);
                return true;
            case R.id.deletePassword:
                deletePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(isLoadedMode()){
            if(screenMode == ScreenMode.LOADED){
                super.onBackPressed();
            }else if(screenMode == ScreenMode.EDITABLE){
                screenMode = ScreenMode.LOADED;
                determineElementAttributes();
                setElementValues();
                showOverflowMenu(true);
            }else{
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }
    }

    private void pre(){
        loadData();
        setScreenMode();
        determineElementAttributes();
        setElementValues();
    }

    private void loadData(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("PasswordInfoBOId");
        if(id != null && id != ""){
            try {
                passwordInfoBO = (PasswordInfoBO) BusinessObject.getBusinessObject(new UniqueID(id),
                        PasswordInfoBO.tableName, getFourContext());
            } catch (FourException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isEditMode(){
        Intent intent = getIntent();
        String editMode = intent.getStringExtra("EditMode");
        return editMode != null && editMode != "" && editMode.equals("1");
    }

    private void setScreenMode(){
        if(isLoadedMode()){
            if(isEditMode()){
                screenMode = ScreenMode.EDITABLE;
            }else {
                screenMode = ScreenMode.LOADED;
            }
        }else{
            screenMode = ScreenMode.NEW;
        }
    }

    private boolean isLoadedMode(){
        boolean isLoadedMode = false;
        if(passwordInfoBO != null && passwordInfoBO.getEntity().getEntityStatus().equals(EntityStatus.Loaded)){
            isLoadedMode = true;
        }
        return isLoadedMode;
    }

    private void determineElementAttributes(){
        if(isLoadedMode()){
            LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
            PLEditText accountNameEditText = (PLEditText) findViewById(R.id.accountNameEditText);
            PLEditText userIdEditText = (PLEditText) findViewById(R.id.userIdEditText);
            PLEditText passwordEditText = (PLEditText) findViewById(R.id.passwordEditText);
            PLEditText urlEditText = (PLEditText) findViewById(R.id.urlEditText);
            PLTextView urlTextView = (PLTextView) findViewById(R.id.urlTextView);
            PLEditText notesEditText = (PLEditText) findViewById(R.id.notesEditText);

            if(screenMode == ScreenMode.LOADED) {
                buttonLayout.setVisibility(View.GONE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                accountNameEditText.setEnabled(false);
                userIdEditText.setEnabled(false);
                urlEditText.setVisibility(View.GONE);
                urlTextView.setVisibility(View.VISIBLE);
                passwordEditText.setEnabled(false);
                notesEditText.setEnabled(false);
            }else if(screenMode == ScreenMode.EDITABLE){
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                buttonLayout.setVisibility(View.VISIBLE);
                accountNameEditText.setEnabled(true);
                userIdEditText.setEnabled(true);
                urlEditText.setVisibility(View.VISIBLE);
                urlTextView.setVisibility(View.GONE);
                passwordEditText.setEnabled(true);
                notesEditText.setEnabled(true);
            }
        }else{
            PLEditText urlEditText = (PLEditText) findViewById(R.id.urlEditText);
            PLTextView urlTextView = (PLTextView) findViewById(R.id.urlTextView);
            urlEditText.setVisibility(View.VISIBLE);
            urlTextView.setVisibility(View.GONE);
        }
    }

    private void setElementValues(){
        if(isLoadedMode()) {
            PLEditText accountNameEditText = (PLEditText) findViewById(R.id.accountNameEditText);
            PLEditText userIdEditText = (PLEditText) findViewById(R.id.userIdEditText);
            PLEditText passwordEditText = (PLEditText) findViewById(R.id.passwordEditText);
            PLEditText urlEditText = (PLEditText) findViewById(R.id.urlEditText);
            PLTextView urlTextView = (PLTextView) findViewById(R.id.urlTextView);
            PLEditText notesEditText = (PLEditText) findViewById(R.id.notesEditText);
            accountNameEditText.setText(passwordInfoBO.getEntity().getAccountName());
            userIdEditText.setText(passwordInfoBO.getEntity().getUserId());
            passwordEditText.setText(Encryption.decrypt(super.getMasterPassword(), passwordInfoBO.getEntity().getPassword()).getValue());
            urlEditText.setText(passwordInfoBO.getEntity().getUrl());
            urlTextView.setText(passwordInfoBO.getEntity().getUrl());
            notesEditText.setText(passwordInfoBO.getEntity().getNotes());
        }
    }

    private void showOverflowMenu(boolean showMenu){
        if(menu == null)
            return;
        if(showMenu){
            new MenuInflater(this).inflate(R.menu.menu_password, menu);
        }else {
            menu.clear();
        }
    }

    public void savePassword(View v){
        if(isValidEntry()) {
            savePassword();
            onBackPressed();
            UIHelper.ScrollPosition = 0;
        }
    }

    private boolean isValidEntry(){
        boolean isValidEntry = true;
        PLEditText accountNameEditText = (PLEditText) findViewById(R.id.accountNameEditText);
        String accountName = accountNameEditText.getText().toString();
        if(accountName.trim().equals("")){
            isValidEntry = false;
            PLDialog dialog = new PLDialog(this, R.string.account_name_required, R.string.error);
            dialog.show();
        }
        else if(isAccountExists(accountName) && !isCurrentAccount(accountName)){
            isValidEntry = false;
            PLDialog dialog = new PLDialog(this, R.string.account_exists, R.string.error);
            dialog.show();
        }

        return isValidEntry;
    }

    private boolean isAccountExists(String accountName){
        boolean isAccountExists = false;
        try {
            PasswordInfoBO passwordInfoBO =
                    (PasswordInfoBO) getFourContext().getBusinessObject(PasswordInfoBO.tableName, "AccountName",accountName);
            if(passwordInfoBO != null && passwordInfoBO.getEntity().getEntityStatus().equals(EntityStatus.Loaded)) {
                isAccountExists = true;
            }
        } catch (FourException e) {
            e.printStackTrace();
        }
        return isAccountExists;
    }

    private boolean isCurrentAccount(String accountName){
        boolean isCurrentAccount = isLoadedMode() && accountName.equals(passwordInfoBO.getEntity().getAccountName());
        return isCurrentAccount;
    }

    private void savePassword(){
        PLEditText accountNameEditText = (PLEditText) findViewById(R.id.accountNameEditText);
        PLEditText userIdEditText = (PLEditText) findViewById(R.id.userIdEditText);
        PLEditText passwordEditText = (PLEditText) findViewById(R.id.passwordEditText);
        PLEditText urlEditText = (PLEditText) findViewById(R.id.urlEditText);
        PLTextView urlTextView = (PLTextView) findViewById(R.id.urlTextView);
        PLEditText notesEditText = (PLEditText) findViewById(R.id.notesEditText);
        String accountName = accountNameEditText.getText().toString();
        String userId = userIdEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String url = urlEditText.getText().toString();
        String notes = notesEditText.getText().toString();

        urlTextView.setText(urlEditText.getText().toString());

        try {
            if(!isLoadedMode()) {
                passwordInfoBO = (PasswordInfoBO) BusinessObject.getBusinessObject(PasswordInfoBO.tableName,
                        getFourContext());
            }
            passwordInfoBO.getEntity().setAccountName(accountName);
            passwordInfoBO.getEntity().setUserId(userId);
            passwordInfoBO.getEntity().setPassword(Encryption.encrypt(super.getMasterPassword(), password).getValue());
            passwordInfoBO.getEntity().setUrl(url);
            passwordInfoBO.getEntity().setNotes(notes);
            passwordInfoBO.save();
        } catch (FourException e) {
            e.printStackTrace();
        }
    }

    private void deletePassword(){
        PLDialog dialog = new PLDialog(this, R.string.delete_password_warning_message, R.string.delete_password_warning_title);
        dialog.setNegativeButton(R.string.button_no, null);
        dialog.setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    passwordInfoBO.delete();
                } catch (FourException e) {
                    e.printStackTrace();
                }
                onBackPressed();
            }
        });
        dialog.show();
    }

    private void manageAd(){
        if(screenMode == ScreenMode.LOADED){
            String sharedPreferenceKey = "OpenCount";
            long openCount = SharedPrefenceManager.getSharedPreferenceValue(this, sharedPreferenceKey);
            int interstitialFrequency = AdManager.getDefaultInterstitialFrequency();
            if(openCount % interstitialFrequency == 0) {
                AdManager adManager = new AdManager(this);
                adManager.showInterstitialAd();
            }
            SharedPrefenceManager.setSharedPreferenceValue(this, sharedPreferenceKey, openCount + 1);
        }

    }
}
