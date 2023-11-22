package com.reset4.passlock.manifest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import com.reset4.fourwork.manifest.FourApp;
import com.reset4.passlock.ui.Font;
import com.reset4.passlock.ui.PLDialog;
import com.reset4.passlockpro.R;

import java.util.Map;

/**
 * Created by ilkery on 31.12.2016.
 */
public class PassLockApp extends FourApp {

    @Override
    public void onCreate() {
        super.onCreate();
        Font.loadFont(getFourContext());
    }

    public void redirect(Activity activity, Class componentClass){
        Intent intent = new Intent(this, componentClass);
        activity.startActivity(intent);
    }

    public void redirect(Activity activity, Class componentClass, Map<String, String> parameters){
        Intent intent = new Intent(this, componentClass);
        for(String parameter : parameters.keySet())
        {
            String value = parameters.get(parameter);
            intent.putExtra(parameter, value);
        }
        activity.startActivity(intent);
    }

    public void showCloseApplicationWarning(final Activity activity){
        PLDialog dialog = new PLDialog(activity, R.string.exit_message, R.string.warning);
        dialog.setNegativeButton(R.string.button_no, null);
        dialog.setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                closeApplication(activity);
            }
        });
        dialog.show();
    }

    private void closeApplication(Activity activity)
    {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(a);
        activity.finish();
        System.exit(0);
    }
}
