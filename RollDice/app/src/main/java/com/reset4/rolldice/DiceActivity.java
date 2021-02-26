package com.reset4.rolldice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.reset4.rolldice.color.Color;
import com.reset4.rolldice.shaker.AccelerometerManager;
import com.reset4.rolldice.shaker.EditionControl;
import com.reset4.rolldice.shaker.GeneralOperations;
import com.reset4.rolldice.shaker.RollingManager;
import com.reset4.rolldice.shaker.SoundManager;

public class DiceActivity extends ActionBarActivity implements SensorEventListener {
    public ImageView dicePicture1, dicePicture2;
    public int diceColor = Color.Red;
    public SoundManager soundManager;
    public RollingManager rollingManager;
    AccelerometerManager accManager;

    public void initializeVariables()
    {
        rollingManager = new RollingManager(this);
        accManager = new AccelerometerManager(this);
        soundManager = new SoundManager(this);
        dicePicture1 = (ImageView) findViewById(R.id.imageView1);
        dicePicture2 = (ImageView) findViewById(R.id.imageView2);
    }

    public void initializeOptionsMenu(Menu menu)
    {
        EditionControl.showHideMenuItems(menu);

        MenuItem soundOnOff = (MenuItem) menu.findItem(R.id.soundOnOff);
        if(soundManager.isSoundOn())
            soundOnOff.setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        GeneralOperations.setBackgroundImage(this, -1);
        initializeVariables();
        GeneralOperations.setDiceColor(this, -1);
        EditionControl.cheatAllowance(this);
        addBanner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dice, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        initializeOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.soundOnOff:
                if(item.isChecked()) {
                    item.setChecked(false);
                    soundManager.soundOff();
                }
                else {
                    item.setChecked(true);
                    soundManager.soundOn();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        accManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accManager.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        accManager.stop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        accManager.onSensorChange(event);
    }


    @Override
    public void onBackPressed() {
        GeneralOperations.goToHomePage(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        rollingManager.cancelTimer();
    }

    public void addBanner()
    {
        if(!EditionControl.isAdFree()) {
            AdView adView = new AdView(this);
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId("ca-app-pub-2395384153614571/8108051441");

            FrameLayout diceActivityLayout = (FrameLayout) this.findViewById(R.id.diceActivityLayout);
            RelativeLayout rl = new RelativeLayout(this);
            RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            lay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            rl.addView(adView, lay);
            rl.setLayoutParams(lay);
            diceActivityLayout.addView(rl);
            setContentView(diceActivityLayout);

            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
    }

    public void showBackgroundColorOptions(MenuItem arg0)
    {
        CharSequence colors[] = new CharSequence[] {"blue", "red", "green", "black"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Background:");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GeneralOperations.setBackgroundImage(DiceActivity.this, which);
            }
        });
        builder.show();
    }

    public void showDiceColorOptions(MenuItem arg0)
    {
        CharSequence colors[] = new CharSequence[] {"blue", "red", "green", "black", "yellow"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Dice Color:");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GeneralOperations.setDiceColor(DiceActivity.this, which);
            }
        });
        builder.show();
    }

    public void openGooglePlay(MenuItem arg0)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.reset4.rolldice"));
        startActivity(browserIntent);
    }

    public void HandleClick(View arg0){
        boolean cheat = false;
        rollingManager.handleClick(cheat);
    }
}
