package com.reset4.passlock.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import com.reset4.passlockpro.R;

/**
 * Created by ilkery on 31.12.2016.
 */
public class PLDialog {

    private AlertDialog.Builder alertDialogBuilder;
    private Context context;

    public PLDialog(Context context, int messageResource, int titleResource){
        this.context = context;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(messageResource);
        alertDialogBuilder.setTitle(titleResource);
        alertDialogBuilder.setPositiveButton(R.string.button_ok, null);
    }

    public PLDialog(Context context, int messageResource, int titleResource, int iconResource){
        this.context = context;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(messageResource);
        alertDialogBuilder.setTitle(titleResource);
        alertDialogBuilder.setPositiveButton(R.string.button_ok, null);
        alertDialogBuilder.setIcon(iconResource);
    }

    public void show(){
        AlertDialog alertDialog = alertDialogBuilder.show();
        TextView messageTextView = (TextView) alertDialog.findViewById(android.R.id.message);
        messageTextView.setTypeface(Font.RalewaySemiBoldFont);
        messageTextView.setTextColor(UIHelper.getColor(context, R.color.darkGray));
        messageTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIHelper.getDensityPixel(context, 17));
        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(negativeButton != null) {
            negativeButton.setTypeface(Font.RalewaySemiBoldFont);
            negativeButton.setTextColor(UIHelper.getColor(context, R.color.colorPrimary));
            negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIHelper.getDensityPixel(context, 17));
        }
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTypeface(Font.RalewaySemiBoldFont);
        positiveButton.setTextColor(UIHelper.getColor(context, R.color.colorPrimary));
        positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIHelper.getDensityPixel(context, 17));
    }

    public void setNegativeButton(int captionResource, DialogInterface.OnClickListener onClickListener)
    {
        alertDialogBuilder.setNegativeButton(captionResource, onClickListener);
    }

    public void setPositiveButton(int captionResource, DialogInterface.OnClickListener onClickListener)
    {
        alertDialogBuilder.setPositiveButton(captionResource, onClickListener);
    }


    /*public static void showCloseApplicationWarning(Activity activity){
        final Activity _activity = activity;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_activity);
        alertDialogBuilder.setTitle(R.string.warning);
        alertDialogBuilder.setIcon(UIOperations.getWarningStyle(activity));
        alertDialogBuilder.setMessage(R.string.exitQuestion);
        alertDialogBuilder.setNegativeButton(R.string.warningCancel, null);
        alertDialogBuilder.setPositiveButton(R.string.warningYes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                GeneralOperations.closeApplication(_activity);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.show();
        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        negativeButton.setTypeface(Font.RalewayMediumFont);
        positiveButton.setTypeface(Font.RalewayMediumFont);
        TextView messageTextView = (TextView) alertDialog.findViewById(android.R.id.message);
        messageTextView.setTypeface(Font.RalewayMediumFont);
    }*/
}
