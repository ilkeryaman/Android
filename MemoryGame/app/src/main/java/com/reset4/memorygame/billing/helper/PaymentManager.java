package com.reset4.memorygame.billing.helper;

import android.content.Intent;
import android.util.Log;

import com.reset4.memorygame.R;
import com.reset4.memorygame.billing.util.IabHelper;
import com.reset4.memorygame.billing.util.IabResult;
import com.reset4.memorygame.billing.util.Inventory;
import com.reset4.memorygame.billing.util.Purchase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eilkyam on 04.01.2018.
 */

public class PaymentManager {
    private PaymentActivity activity;
    private IabHelper iabHelper;
    private boolean successfulSetUp;
    private static final String TAG = "com.r4.inappbilling";

    public PaymentManager(PaymentActivity activity){
        setActivity(activity);
        setUpInAppBilling();
    }

    private PaymentActivity getActivity() {
        return activity;
    }

    private void setActivity(PaymentActivity activity) {
        this.activity = activity;
    }

    public boolean isSuccessfulSetUp() {
        return successfulSetUp;
    }

    private void setSuccessfulSetUp(boolean successfulSetUp) {
        this.successfulSetUp = successfulSetUp;
    }

    private String getSKU() {
        return getActivity().getResources().getString(R.string.sku_item);
    }

    private String getPurchaseToken(){
        return getActivity().getResources().getString(R.string.purchase_token);
    }

    public void setUpInAppBilling(){
        String base64EncodedPublicKey = getActivity().getResources().getString(R.string.base64rsa);
        iabHelper = new IabHelper(getActivity(), base64EncodedPublicKey);
        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result){
                if (!result.isSuccess()) {
                    setSuccessfulSetUp(false);
                    getActivity().onPaymentDone();
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                } else {
                    setSuccessfulSetUp(true);
                    hasPurchase();
                    Log.d(TAG, "In-app Billing is set up OK");
                }
            }});
    }

    public void launchPurchaseFlow(){
        try {
            iabHelper.launchPurchaseFlow(getActivity(), getSKU(), 10001, purchaseFinishedListener, getPurchaseToken());
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    IabHelper.OnIabPurchaseFinishedListener purchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase){
            if (result.isFailure()) {
                // Handle error
                return;
            }
            else if (purchase.getSku().equals(getSKU())) {
                getActivity().onPaymentDone();
            }
        }
    };

    public boolean responseOfInAppBillingPurchase(int requestCode, int resultCode, Intent data){
        return iabHelper.handleActivityResult(requestCode, resultCode, data);
    }

    private void hasPurchase(){
        try {
            List<String> skus = new ArrayList<>();
            skus.add(getSKU());
            iabHelper.queryInventoryAsync(true, null, skus, gotInventoryListener);
            //iabHelper.queryInventoryAsync(gotInventoryListener);

        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    IabHelper.QueryInventoryFinishedListener gotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (result.isFailure()) {
                // handle error here

            }
            else {
                // does the user have the premium upgrade?
                if(inventory.hasPurchase(getSKU())) {
                    // update UI accordingly
                    getActivity().onPaymentDone();
                    //Button button_baby = (Button) getActivity().findViewById(R.id.button_baby);
                    //button_baby.setText("sagaaa");
                }else{
                    //Button button_baby = (Button) getActivity().findViewById(R.id.button_baby);
                    //button_baby.setText("ragaaa");
                }
            }
        }
    };

    public void dispose(){
        if (iabHelper != null) {
            try {
                iabHelper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            iabHelper = null;
        }
    }
}
