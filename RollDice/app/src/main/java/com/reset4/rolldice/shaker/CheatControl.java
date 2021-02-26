package com.reset4.rolldice.shaker;

import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.reset4.rolldice.DiceActivity;

/**
 * Created by eilkyam on 02.06.2015.
 */
public class CheatControl {
    private DiceActivity diceActivity;
    private GestureDetectorCompat mDetector;

    CheatControl(DiceActivity diceActivity, ImageView imageView)
    {
        this.diceActivity = diceActivity;
        mDetector = new GestureDetectorCompat(diceActivity, new MyGestureListener());
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public void onLongPress(MotionEvent e) {
            diceActivity.rollingManager.cheat();
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            boolean cheat = false;
            diceActivity.rollingManager.handleClick(cheat);
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            boolean cheat = false;
            diceActivity.rollingManager.handleClick(cheat);
            return super.onDoubleTap(e);
        }
    }
}
