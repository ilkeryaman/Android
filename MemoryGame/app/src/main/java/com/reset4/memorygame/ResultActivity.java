package com.reset4.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.reset4.memorygame.action.GameCreator;
import com.reset4.memorygame.ad.AdManager;
import com.reset4.memorygame.preference.PreferenceManager;
import com.reset4.memorygame.result.GameLevel;
import com.reset4.memorygame.result.GameResult;
import com.reset4.memorygame.result.ResultCalculator;
import com.reset4.memorygame.ui.font.Font;

public class ResultActivity extends AppCompatActivity {
    private ResultCalculator resultCalculator;
    private GameLevel gameLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_result);
        manageAd();
        resultCalculator = new ResultCalculator(this);
        setResults();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    @Override
    public void onBackPressed() {
        Intent newActivity = new Intent(this, MainActivity.class);
        finishAffinity();
        startActivity(newActivity);
    }

    private void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    private void manageAd(){
        PreferenceManager preferenceManager = new PreferenceManager(this);
        if(isPurchased(preferenceManager) == false) {
            String played_game_count_preference_key = getString(R.string.played_game_count);
            int playedGameCount = preferenceManager.getIntegerValue(played_game_count_preference_key);
            preferenceManager.setIntegerValue(played_game_count_preference_key, playedGameCount + 1);

            if ((playedGameCount + 1) % 3 == 0) {
                AdManager adManager = new AdManager(this);
                adManager.showInterstitialAd();
            }
        }
    }

    private boolean isPurchased(PreferenceManager preferenceManager){
        String is_purchased_preference_key = getString(R.string.is_purchased);
        return preferenceManager.getIntegerValue(is_purchased_preference_key) == 1;
    }

    private void setResults(){
        GameResult gameResult = resultCalculator.calculateResult(getAttempt(), getTurnedCard(), getStartTime(), getDuration());
        gameLevel = gameResult.getGameLevel();
        preparePointsLayout(gameResult.getPoint());
        prepareStarLayout(gameResult.getStarCount());
        prepareInfoLayout(gameResult.getGameLevel(), gameResult.getAttempt(), gameResult.getDuration());
        prepareButtonLayout();
    }

    private void preparePointsLayout(int points){
        TextView tv_pointsCaption = (TextView) findViewById(R.id.tv_pointsCaption);
        TextView tv_pointsInfo = (TextView) findViewById(R.id.tv_pointsInfo);
        tv_pointsInfo.setText(String.valueOf(points));
        tv_pointsInfo.setTypeface(Font.PorkysFont);
        tv_pointsCaption.setTypeface(Font.PorkysBoldFont);
    }

    private void prepareStarLayout(int starCount){
        if(starCount == 5){
            ImageView iv_firstStar = (ImageView) findViewById(R.id.iv_firstStar);
            ImageView iv_secondStar = (ImageView) findViewById(R.id.iv_secondStar);
            ImageView iv_thirdStar = (ImageView) findViewById(R.id.iv_thirdStar);
            ImageView iv_forthStar = (ImageView) findViewById(R.id.iv_forthStar);
            ImageView iv_fifthStar = (ImageView) findViewById(R.id.iv_fifthStar);
            iv_firstStar.setImageResource(R.drawable.star);
            iv_secondStar.setImageResource(R.drawable.star);
            iv_thirdStar.setImageResource(R.drawable.star);
            iv_forthStar.setImageResource(R.drawable.star);
            iv_fifthStar.setImageResource(R.drawable.star);
        }else if(starCount == 4){
            ImageView iv_firstStar = (ImageView) findViewById(R.id.iv_firstStar);
            ImageView iv_secondStar = (ImageView) findViewById(R.id.iv_secondStar);
            ImageView iv_thirdStar = (ImageView) findViewById(R.id.iv_thirdStar);
            ImageView iv_forthStar = (ImageView) findViewById(R.id.iv_forthStar);
            iv_firstStar.setImageResource(R.drawable.star);
            iv_secondStar.setImageResource(R.drawable.star);
            iv_thirdStar.setImageResource(R.drawable.star);
            iv_forthStar.setImageResource(R.drawable.star);
        }else if(starCount == 3){
            ImageView iv_firstStar = (ImageView) findViewById(R.id.iv_firstStar);
            ImageView iv_secondStar = (ImageView) findViewById(R.id.iv_secondStar);
            ImageView iv_thirdStar = (ImageView) findViewById(R.id.iv_thirdStar);
            iv_firstStar.setImageResource(R.drawable.star);
            iv_secondStar.setImageResource(R.drawable.star);
            iv_thirdStar.setImageResource(R.drawable.star);
        }else if(starCount == 2){
            ImageView iv_firstStar = (ImageView) findViewById(R.id.iv_firstStar);
            ImageView iv_secondStar = (ImageView) findViewById(R.id.iv_secondStar);
            iv_firstStar.setImageResource(R.drawable.star);
            iv_secondStar.setImageResource(R.drawable.star);
        }else {
            ImageView iv_firstStar = (ImageView) findViewById(R.id.iv_firstStar);
            iv_firstStar.setImageResource(R.drawable.star);
        }
    }


    private void prepareInfoLayout(GameLevel gameLevel, int attempt, String duration){
        TextView tv_gameLevelCaption = (TextView) findViewById(R.id.tv_gameLevelCaption);
        TextView tv_attemptCaption = (TextView) findViewById(R.id.tv_attemptCaption);
        TextView tv_durationCaption = (TextView) findViewById(R.id.tv_durationCaption);
        TextView tv_gameLevelInfo = (TextView) findViewById(R.id.tv_gameLevelInfo);
        TextView tv_attemptInfo = (TextView) findViewById(R.id.tv_attemptInfo);
        TextView tv_durationInfo = (TextView) findViewById(R.id.tv_durationInfo);
        tv_gameLevelCaption.setTypeface(Font.PorkysFont);
        tv_attemptCaption.setTypeface(Font.PorkysFont);
        tv_durationCaption.setTypeface(Font.PorkysFont);
        tv_gameLevelInfo.setText(gameLevel.getName());
        tv_gameLevelInfo.setTypeface(Font.PorkysFont);
        tv_attemptInfo.setText(String.valueOf(attempt));
        tv_attemptInfo.setTypeface(Font.DestroyEarthFont);
        tv_durationInfo.setText(duration);
        tv_durationInfo.setTypeface(Font.DestroyEarthFont);
    }

    private void prepareButtonLayout(){
        Button button_playAgain = (Button) findViewById(R.id.button_playAgain);
        Button button_mainMenu = (Button) findViewById(R.id.button_mainMenu);
        button_playAgain.setTypeface(Font.PorkysFont);
        button_mainMenu.setTypeface(Font.PorkysFont);
    }

    private int getAttempt(){
        Intent mIntent = getIntent();
        return mIntent.getIntExtra("attempt", 0);
    }

    private int getTurnedCard(){
        Intent mIntent = getIntent();
        return mIntent.getIntExtra("turnedCard", 0);
    }

    private long getStartTime(){
        Intent mIntent = getIntent();
        return mIntent.getLongExtra("startTime", 0);
    }

    private String getDuration(){
        Intent mIntent = getIntent();
        return mIntent.getStringExtra("duration");
    }

    public void playAgain(View v){
        if(gameLevel == GameLevel.BABY){
            GameCreator.createBabyGame(this);
        }else if(gameLevel == GameLevel.CHILD){
            GameCreator.createChildGame(this);
        }else if(gameLevel == GameLevel.BROTHER){
            GameCreator.createBrotherGame(this);
        }else if(gameLevel == GameLevel.FATHER){
            GameCreator.createFatherGame(this);
        }else{
            GameCreator.createGrandFatherGame(this);
        }
    }

    public void mainMenu(View v){
        onBackPressed();
    }
}
