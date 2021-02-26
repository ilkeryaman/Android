package com.reset4.memorygame.action;

import android.app.Activity;
import android.content.Intent;

import com.reset4.memorygame.GameActivity;

/**
 * Created by eilkyam on 02.01.2018.
 */

public class GameCreator {

    public static void createBabyGame(Activity activity){
        createGame(activity, 2, 4);
    }

    public static void createChildGame(Activity activity){
        createGame(activity, 3, 4);
    }

    public static void createBrotherGame(Activity activity){
        createGame(activity, 4, 4);
    }

    public static void createFatherGame(Activity activity){
        createGame(activity, 4, 5);
    }

    public static void createGrandFatherGame(Activity activity){
        createGame(activity, 5, 6);
    }

    private static void createGame(Activity activity, int row_count, int card_in_a_row){
        Intent newActivity = new Intent(activity, GameActivity.class);
        newActivity.putExtra("row_count", row_count);
        newActivity.putExtra("card_in_a_row", card_in_a_row);
        activity.startActivity(newActivity);
    }
}
