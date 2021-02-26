package com.reset4.memorygame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.reset4.memorygame.board.BoardGenerator;
import com.reset4.memorygame.board.property.SummaryHolder;
import com.reset4.memorygame.preference.PreferenceManager;
import com.reset4.memorygame.ui.UserInterfaceUpdater;
import com.reset4.memorygame.ui.font.Font;

import java.util.Calendar;

public class GameActivity extends AppCompatActivity implements UserInterfaceUpdater {
    private SummaryHolder summaryHolder;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_game);
        prepareTextViews();
        generateBoard();
        manageAd();
        startDurationCalculator();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    private void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    private void prepareTextViews(){
        Typeface PorkysFont = Font.PorkysFont;
        Typeface DestroyEarthFont = Font.DestroyEarthFont;
        TextView textview_attempt_caption = (TextView) findViewById(R.id.textview_attempt_caption);
        TextView textview_turned_caption = (TextView) findViewById(R.id.textview_turned_caption);
        TextView textview_duration_caption = (TextView) findViewById(R.id.textview_duration_caption);
        TextView textview_attempt = (TextView) findViewById(R.id.textview_attempt);
        TextView textview_turned = (TextView) findViewById(R.id.textview_turned);
        TextView textview_duration = (TextView) findViewById(R.id.textview_duration);
        textview_attempt_caption.setTypeface(PorkysFont);
        textview_turned_caption.setTypeface(PorkysFont);
        textview_duration_caption.setTypeface(PorkysFont);
        textview_attempt.setTypeface(DestroyEarthFont);
        textview_turned.setTypeface(DestroyEarthFont);
        textview_duration.setTypeface(DestroyEarthFont);
    }

    private void generateBoard(){
        summaryHolder = new SummaryHolder(getRowCount(), getCardCountInARow());
        BoardGenerator boardGenerator = new BoardGenerator();
        boardGenerator.generateBoard((LinearLayout) findViewById(R.id.boardLayout), summaryHolder);
    }

    private int getRowCount(){
        Intent mIntent = getIntent();
        return mIntent.getIntExtra("row_count", 0);
    }

    private int getCardCountInARow(){
        Intent mIntent = getIntent();
        return mIntent.getIntExtra("card_in_a_row", 0);
    }

    private void manageAd(){
        PreferenceManager preferenceManager = new PreferenceManager(this);
        AdView adView = (AdView) findViewById(R.id.adView);
        if(isPurchased(preferenceManager) == false) {
            AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            adView.loadAd(adRequest);
        }else{
            adView.setVisibility(View.GONE);
        }
    }

    private boolean isPurchased(PreferenceManager preferenceManager){
        String is_purchased_preference_key = getString(R.string.is_purchased);
        return preferenceManager.getIntegerValue(is_purchased_preference_key) == 1;
    }

    @Override
    public void updateUI() {
        TextView textview_attempt = (TextView) findViewById(R.id.textview_attempt);
        TextView textview_turned = (TextView) findViewById(R.id.textview_turned);
        textview_attempt.setText(String.valueOf(summaryHolder.getAttempt()));
        textview_turned.setText(String.valueOf(summaryHolder.getTurnedCard()));
    }

    private void startDurationCalculator(){
        handler.post(updateDuration);
    }

    private final Runnable updateDuration = new Runnable() {
        public void run() {
            updateDurationDisplay();
            handler.postDelayed(updateDuration, 1000); // 1 second
        }
    };

    private void updateDurationDisplay(){
        String val;
        long now = Calendar.getInstance().getTimeInMillis();
        long duration = (now - summaryHolder.getStartTime()) / 1000;
        String hourStr, minuteStr, secondStr;
        if(duration / 60 < 1){
            hourStr = "00";
            minuteStr = "00";
            secondStr = (duration < 10 ? "0" : "").concat(String.valueOf(duration));
        }else if(duration / 3600 < 1){
            long minute = duration / 60;
            long second = duration % 60;
            hourStr = "00";
            minuteStr = (minute < 10 ? "0" : "").concat(String.valueOf(minute));
            secondStr = (second < 10 ? "0" : "").concat(String.valueOf(second));
        }else{
            long hour = duration / 3600;
            long minute = (duration % 3600) / 60;
            long second = duration % 60;
            hourStr = (hour < 10 ? "0" : "").concat(String.valueOf(hour));
            minuteStr = (minute < 10 ? "0" : "").concat(String.valueOf(minute));
            secondStr = (second < 10 ? "0" : "").concat(String.valueOf(second));
        }

        val = hourStr.concat(":").concat(minuteStr).concat(":").concat(secondStr);
        TextView textview_duration = (TextView)findViewById(R.id.textview_duration);
        textview_duration.setText(val);
        summaryHolder.setDuration(val);
    }
}
