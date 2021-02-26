package com.reset4.rolldice.shaker;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import com.reset4.rolldice.DiceActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by eilkyam on 28.05.2015.
 */
public class RollingManager {
    boolean rolling = false;        //Is die rolling?
    Handler handler;    //Post message to start roll
    Timer timer = new Timer();    //Used to implement feedback to user
    Random rng = new Random();    //generate random numbers
    int rollCount = 0;
    int lastDice1 = 0;
    int lastDice2 = 0;
    int waitFor = 300;
    int rollPerThrow = 3;
    DiceActivity diceActivity;

    public RollingManager(DiceActivity activity) {
        diceActivity = activity;
        //link handler to callback
        handler = new Handler(callback);
    }

    public void cheat()
    {
        boolean cheat = true;
        handleClick(cheat);
    }

    //User pressed dice, lets start
    public void handleClick(boolean cheat) {
        if(!rolling) {
            rolling = true;
            //Pause to allow image to update
            diceActivity.soundManager.throwDiceSound();
            waitFor = 300;
            timer.schedule(new Roll(cheat), waitFor);
        }
    }

    public void handleShake()
    {
        if(!rolling) {
            rolling = true;
            diceActivity.soundManager.shakeDiceSound();
            waitFor = 120;
            timer.schedule(new Roll(), waitFor);
        }
    }

    //When pause completed message sent to callback
    class Roll extends TimerTask {
        private boolean cheat;
        Roll(){
            cheat = false;
        }
        Roll(boolean cheat)
        {
            this.cheat = cheat;
        }
        public void run() {
            if(cheat)
                handler.sendEmptyMessage(1);
            else
                handler.sendEmptyMessage(0);
        }
    }

    //Receives message from timer to start dice roll
    Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            rollDices();

            if (rollCount != rollPerThrow) {
                rollCount++;
                timer.schedule(new Roll(msg.what != 0), waitFor);
            } else {
                rollCount = 0;
                rolling = false;    //user can press again
                if(msg.what == 1)
                {
                    Integer resourceId = (Integer)diceActivity.dicePicture2.getTag();
                    diceActivity.dicePicture1.setImageResource(resourceId);
                    diceActivity.dicePicture1.setTag(resourceId);
                }
            }
            return true;
        }
    };

    public void rollDices() {
        roll(diceActivity.dicePicture1, lastDice1, 1);
        roll(diceActivity.dicePicture2, lastDice2, 2);
    }

    private void roll(ImageView dicePicture, int lastDice, int diceNumber) {
        int dice = rng.nextInt(6) + 1;
        do {
            dice = rng.nextInt(6) + 1;
            int resourceId = GeneralOperations.getDiceResourceId(diceActivity, dice);
            dicePicture.setImageResource(resourceId);
            dicePicture.setTag(resourceId);
        } while (dice == lastDice);

        if (diceNumber == 1) {
            lastDice1 = dice;
        } else if (diceNumber == 2) {
            lastDice2 = dice;
        }
    }

    public void cancelTimer()
    {
        timer.cancel();
    }
}
