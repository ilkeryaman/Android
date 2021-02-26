package com.reset4.memorygame.ad;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.reset4.memorygame.R;


/**
 * Created by eilkyam on 23.01.2016.
 */
public class AdManager {
    private Context context;

    public AdManager(Context context){
        this.context = context;
    }

    public void showInterstitialAd(){
        final InterstitialAd interstitial = new InterstitialAd(context);
        interstitial.setAdUnitId(context.getResources().getString(R.string.interstitial));
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded(){
                displayInterstitial();
            }

            @Override
            public void onAdClosed() {

            }

            public void displayInterstitial() {
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        });

        requestNewInterstitial(interstitial);
    }

    private void requestNewInterstitial(InterstitialAd interstitial) {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .tagForChildDirectedTreatment(true)
                .build();

        interstitial.loadAd(adRequest);
    }
}
