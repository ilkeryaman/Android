package com.reset4.memorygame;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.reset4.memorygame.action.GameCreator;
import com.reset4.memorygame.preference.PreferenceManager;
import com.reset4.memorygame.ui.font.Font;

public class MainActivity // extends PaymentActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_main);
        prepareButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
        hideRemoveAdsButton();
    }

    private void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    private void hideRemoveAdsButton(){
        LinearLayout removeAdLayout = (LinearLayout) findViewById(R.id.removeAdLayout);
        removeAdLayout.setVisibility(View.GONE);
    }

    private void prepareButtons(){
        Typeface PorkysFont = Font.PorkysFont;
        Button remove_ads = (Button) findViewById(R.id.remove_ads);
        Button button_baby = (Button) findViewById(R.id.button_baby);
        Button button_child = (Button) findViewById(R.id.button_child);
        Button button_brother = (Button) findViewById(R.id.button_brother);
        Button button_father = (Button) findViewById(R.id.button_father);
        Button button_grandfather = (Button) findViewById(R.id.button_grandfather);
        remove_ads.setTypeface(PorkysFont);
        button_baby.setTypeface(PorkysFont);
        button_child.setTypeface(PorkysFont);
        button_brother.setTypeface(PorkysFont);
        button_father.setTypeface(PorkysFont);
        button_grandfather.setTypeface(PorkysFont);
    }

    public void open2x4game(View view){
        GameCreator.createBabyGame(this);
    }

    public void open3x4game(View view){
        GameCreator.createChildGame(this);
    }

    public void open4x4game(View view){
        GameCreator.createBrotherGame(this);
    }

    public void open4x5game(View view){
        GameCreator.createFatherGame(this);
    }

    public void open5x6game(View view){
        GameCreator.createGrandFatherGame(this);
    }

    public void removeAds(View view) {

        // launchPurchaseFlow();
    }

    /*@Override
    public void onPaymentDone(){
        LinearLayout removeAdLayout = (LinearLayout) findViewById(R.id.removeAdLayout);
        if(removeAdLayout != null) {
            removeAdLayout.setVisibility(View.GONE);
        }
        setPaymentPreference();
    }*/

    private void setPaymentPreference(){
        String is_purchased_preference_key = getString(R.string.is_purchased);
        PreferenceManager preferenceManager = new PreferenceManager(this);
        preferenceManager.setIntegerValue(is_purchased_preference_key, 1);
    }
}
