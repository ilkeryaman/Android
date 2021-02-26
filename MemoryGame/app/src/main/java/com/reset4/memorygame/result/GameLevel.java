package com.reset4.memorygame.result;

import com.reset4.memorygame.R;
import com.reset4.memorygame.application.MemoryGameApp;

/**
 * Created by eilkyam on 02.01.2018.
 */

public enum GameLevel {
    BABY(R.string.baby, 8, 18, 30), CHILD(R.string.child, 12, 45, 60), BROTHER(R.string.brother, 16, 60, 80), FATHER(R.string.father, 20, 80, 105), GRANDFATHER(R.string.grandfather, 30, 120, 150);

    private final int nameResource;
    private final int cardCount;
    private final int worstAttempt;
    private final int worstDuration;

    private GameLevel(int nameResource, int cardCount, int worstAttempt, int worstDuration){
        this.nameResource = nameResource;
        this.cardCount = cardCount;
        this.worstAttempt = worstAttempt;
        this.worstDuration = worstDuration;
    }

    public String getName() {
        return MemoryGameApp.getAppContext().getResources().getString(getNameResource());
    }

    private int getNameResource() {
        return nameResource;
    }

    public int getCardCount() {
        return cardCount;
    }

    public int getWorstAttempt() {
        return worstAttempt;
    }

    public int getWorstDuration() {
        return worstDuration;
    }
}
