package com.reset4.passlock.ad;


import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.reset4.passlock.PassLockActivity;
import com.reset4.passlockpro.R;

/**
 * Created by ilkery on 8.01.2017.
 */
public class AdManager {
    private static boolean professionalEdition = true;
    private static int defaultInterstitialFrequency = 3;
    private PassLockActivity context;

    private InterstitialAd mInterstitialAd;

    public AdManager(PassLockActivity context){
        this.context = context;
    }

    public void prepareBanner(){
        AdView adView = (AdView) context.findViewById(R.id.bannerAdView);
        if(isProfessionalEdition()){
            adView.setVisibility(View.GONE);
        }else {
            AdRequest adRequest = new AdRequest.Builder().build();
            // Start loading the ad in the background.
            adView.loadAd(adRequest);
        }
    }

    public void showInterstitialAd(){
        if(isProfessionalEdition()){
            //do nothing
            ;
        }else {
            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(context, context.getResources().getString(R.string.interstitial), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            mInterstitialAd = null;
                        }
                    });

        }
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
