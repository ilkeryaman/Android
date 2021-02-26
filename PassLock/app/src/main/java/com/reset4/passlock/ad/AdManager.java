package com.reset4.passlock.ad;


import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.reset4.passlock.PassLockActivity;
import com.reset4.passlock.R;

/**
 * Created by ilkery on 8.01.2017.
 */
public class AdManager {
    private static boolean professionalEdition = true;
    private static int defaultInterstitialFrequency = 3;
    private PassLockActivity context;

    public AdManager(PassLockActivity context){
        this.context = context;
    }

    public void prepareBanner(){
        AdView adView = (AdView) context.findViewById(R.id.bannerAdView);
        if(isProfessionalEdition()){
            adView.setVisibility(View.GONE);
        }else {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            // Start loading the ad in the background.
            adView.loadAd(adRequest);
        }
    }

    public void showInterstitialAd(){
        if(isProfessionalEdition()){
            //do nothing
            ;
        }else {
            final InterstitialAd interstitial = new InterstitialAd(context);
            interstitial.setAdUnitId(context.getResources().getString(R.string.interstitial));
            interstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    displayInterstitial();
                }

                @Override
                public void onAdClosed() {
                    //you can do anything
                    ;
                }

                public void displayInterstitial() {
                    if (interstitial.isLoaded()) {
                        interstitial.show();
                    }
                }
            });

            requestNewInterstitial(interstitial);
        }
    }

    private void requestNewInterstitial(InterstitialAd interstitial) {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .tagForChildDirectedTreatment(true)
                .build();

        interstitial.loadAd(adRequest);
}

    public void openPassLockProWebPage(){
        if(!isProfessionalEdition()){
            String url = context.getResources().getString(R.string.passlock_pro);
            final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
            context.startActivity(intent);
        }
    }

    public static int getDefaultInterstitialFrequency(){
        return defaultInterstitialFrequency;
    }

    public static boolean isProfessionalEdition(){
        return professionalEdition;
    }
}
