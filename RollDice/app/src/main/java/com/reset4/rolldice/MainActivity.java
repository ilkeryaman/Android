package com.reset4.rolldice;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBackgroundImage();
        Wait();
    }

    public void Wait()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Start();
            }
        }, 3000);
    }

    public void Start(){
        Intent intent = new Intent(MainActivity.this, DiceActivity.class);
        startActivity(intent);
    }

    public void setBackgroundImage()
    {
        ImageView backgroundImageView = (ImageView) findViewById(R.id.backgroundImageView);
        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    class WaitTask extends AsyncTask {
        WaitTask(Context context) {
        }

        @Override
        protected void onPostExecute(Object result) {

        }

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }
    }
}