package com.reset4.passlock.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.reset4.passlock.PassLockActivity;
import com.reset4.passlock.PasswordActivity;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.manifest.PassLockApp;
import com.reset4.passlock.security.Encryption;
import com.reset4.passlock.ui.PLTextView;
import com.reset4.passlock.ui.UIHelper;
import com.reset4.passlockpro.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ilkery on 31.12.2016.
 */
public class PasswordListAdapter extends BaseAdapter {
    private PassLockActivity context;
    private List<PasswordInfoBO> passwordInfoBOList;

    public PasswordListAdapter(PassLockActivity context, List<PasswordInfoBO> passwordInfoBOList) {
        this.context = context;
        this.passwordInfoBOList = passwordInfoBOList;
    }

    public List<PasswordInfoBO> getPasswordInfoBOList(){
        return passwordInfoBOList;
    }

    @Override
    public int getCount() {
        return passwordInfoBOList.size();
    }

    @Override
    public PasswordInfoBO getItem(int position) {
        return passwordInfoBOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View list = inflater.inflate(R.layout.password_listview, null);
        final PasswordInfoBO passwordInfoBO = passwordInfoBOList.get(position);

        PLTextView textView = (PLTextView) list.findViewById(R.id.passwordTextView);
        textView.setText(passwordInfoBO.getEntity().getAccountName());
        list.setLongClickable(true);
        setViewOnClick(list, passwordInfoBO, position);
        setLongClickAction(list);
        return list;
    }

    private void setViewOnClick(View view, final PasswordInfoBO passwordInfoBO, final int position){
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("PasswordInfoBOId", passwordInfoBO.getEntity().getPrimaryKeyField().getValue().toString());
                PassLockApp app = (PassLockApp) context.getApplication();
                app.redirect(context, PasswordActivity.class, parameters);
                UIHelper.ScrollPosition = position;
            }
        });
    }

    private void setLongClickAction(View view){
        ListView lv = (ListView) context.findViewById(R.id.password_listview);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                openLongClickMenu(view, position);
                return true;
            }
        });
    }

    private void openLongClickMenu(View view, int position){
        final PasswordInfoBO passwordInfoBO = getItem(position);
        PopupMenu popup = new PopupMenu(view.getContext(), view, Gravity.TOP);
        popup.getMenuInflater().inflate(R.menu.menu_password_longclick, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.editAccount){
                    HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("PasswordInfoBOId", passwordInfoBO.getEntity().getPrimaryKeyField().getValue().toString());
                    parameters.put("EditMode", "1");
                    PassLockApp app = (PassLockApp) context.getApplication();
                    app.redirect(context, PasswordActivity.class, parameters);
                    return true;
                } else if (item.getItemId() == R.id.copyUserId){
                    copyText(passwordInfoBO.getEntity().getUserId());
                    return true;
                } else if (item.getItemId() == R.id.copyPassword){
                    copyText(Encryption.decrypt(context.getMasterPassword(), passwordInfoBO.getEntity().getPassword()).getValue());
                    return true;
                } else if (item.getItemId() == R.id.launchUrl){
                    String url = passwordInfoBO.getEntity().getUrl();
                    if(url == null || url.trim() == ""){
                        url = "http://www.google.com";
                    }else if(!url.startsWith("http")){
                        url = "http://" + url;
                    }
                    final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                    context.startActivity(intent);
                    return true;
                } else {
                    return true;
                }
            }
        });
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void copyText(String text) {

        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text label",text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
