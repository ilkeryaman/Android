package com.reset4.memorygame.result;

import android.app.Activity;

import java.util.Calendar;

/**
 * Created by eilkyam on 02.01.2018.
 */

public class ResultCalculator {
    private final int consolationPoint = 100;
    private Activity activity;

    public ResultCalculator(Activity activity){
        setActivity(activity);
    }

    private Activity getActivity() {
        return activity;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    public GameResult calculateResult(int attempt, int turnedCard, long startTime, String durationStr){
        GameResult gameResult = new GameResult();
        GameLevel gameLevel = getGameLevel(turnedCard);
        gameResult.setGameLevel(gameLevel);
        long now = Calendar.getInstance().getTimeInMillis();
        int duration = (int)((now - startTime) / 1000);
        int calculatedPoint = calculatePoint(gameLevel, attempt, duration);
        gameResult.setAttempt(attempt);
        gameResult.setDuration(durationStr);
        gameResult.setPoint(calculatedPoint);
        gameResult.setStarCount(getStarCount(gameLevel, calculatedPoint));
       return gameResult;
    }

    private GameLevel getGameLevel(int turnedCard){
        GameLevel gameLevel = null;
        if(turnedCard == GameLevel.BABY.getCardCount()){
            gameLevel = GameLevel.BABY;
        }else if(turnedCard == GameLevel.CHILD.getCardCount()){
            gameLevel = GameLevel.CHILD;
        }else if(turnedCard == GameLevel.BROTHER.getCardCount()){
            gameLevel = GameLevel.BROTHER;
        }else if(turnedCard == GameLevel.FATHER.getCardCount()){
            gameLevel = GameLevel.FATHER;
        }else if(turnedCard == GameLevel.GRANDFATHER.getCardCount()){
            gameLevel = GameLevel.GRANDFATHER;
        }
        return gameLevel;
    }

    private int getStarCount(GameLevel gameLevel, int calculatedPoint){
        int starCount;
        int bestPoint = calculatePoint(gameLevel, gameLevel.getCardCount(), gameLevel.getCardCount() / 2);

        if(calculatedPoint <= bestPoint / 5){
            starCount = 1;
        }else if(calculatedPoint <= (bestPoint * 2) / 5){
            starCount = 2;
        }else if(calculatedPoint <= (bestPoint * 3) / 5){
            starCount = 3;
        }else if(calculatedPoint <= (bestPoint * 4) / 5){
            starCount = 4;
        }else{
            starCount = 5;
        }

        return starCount;
    }

    private int calculatePoint(GameLevel gameLevel, int attempt, int duration){
        int attemptPoint =
                (gameLevel.getWorstAttempt() - attempt <= 0) ?  consolationPoint
                        : (gameLevel.getWorstAttempt() - attempt) * consolationPoint;
        int durationPoint =
                (gameLevel.getWorstDuration() - duration <= 0) ? consolationPoint
                        : (gameLevel.getWorstDuration() - duration) * consolationPoint;
        return attemptPoint + durationPoint;
    }
}
