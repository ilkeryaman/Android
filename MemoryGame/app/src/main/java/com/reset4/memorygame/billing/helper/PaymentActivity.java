package com.reset4.memorygame.billing.helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by eilkyam on 04.01.2018.
 */

public abstract class PaymentActivity extends AppCompatActivity {

    private PaymentManager paymentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePaymentManager();
    }

    private PaymentManager getPaymentManager() {
        return paymentManager;
    }

    private void initializePaymentManager(){
        paymentManager = new PaymentManager(this);
    }

    public void launchPurchaseFlow() {
        getPaymentManager().launchPurchaseFlow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (getPaymentManager().responseOfInAppBillingPurchase(requestCode, resultCode, data) == false) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPaymentManager().dispose();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID){
        super.setContentView(layoutResID);
    }

    public abstract void onPaymentDone();
}
