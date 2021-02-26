package com.reset4.memorygame.board.property;

import com.reset4.memorygame.ui.CardView;

import java.util.Calendar;

/**
 * Created by eilkyam on 28.12.2017.
 */

public class SummaryHolder {
    private int rowCount;
    private int cardInARow;
    private int attempt;
    private boolean attemptRunning;
    private int turnedCard;
    private long startTime;
    private String duration;
    private CardView previousCardView;

    public SummaryHolder(int rowCount, int cardInARow){
        setRowCount(rowCount);
        setCardInARow(cardInARow);
        setStartTime(Calendar.getInstance().getTimeInMillis());
    }

    public int getRowCount() {
        return rowCount;
    }

    private void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getCardInARow() {
        return cardInARow;
    }

    private void setCardInARow(int cardInARow) {
        this.cardInARow = cardInARow;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public boolean isAttemptRunning() {
        return attemptRunning;
    }

    public void setAttemptRunning(boolean attemptRunning) {
        this.attemptRunning = attemptRunning;
    }

    public int getTurnedCard() {
        return turnedCard;
    }

    public void setTurnedCard(int turnedCard) {
        this.turnedCard = turnedCard;
    }

    public long getStartTime() {
        return startTime;
    }

    private void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public CardView getPreviousCardView() {
        return previousCardView;
    }

    public void setPreviousCardView(CardView previousCardView) {
        this.previousCardView = previousCardView;
    }
}
