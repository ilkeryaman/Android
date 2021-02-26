package com.reset4.memorygame.action;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.reset4.memorygame.GameActivity;
import com.reset4.memorygame.R;
import com.reset4.memorygame.ResultActivity;
import com.reset4.memorygame.board.property.SummaryHolder;
import com.reset4.memorygame.ui.CardView;

/**
 * Created by eilkyam on 27.12.2017.
 */

public class OnCardSelectListener implements View.OnClickListener {
    private SummaryHolder summaryHolder;
    private CardView cardView;
    final Handler handler = new Handler();

    public OnCardSelectListener(SummaryHolder summaryHolder, CardView cardView){
        setSummaryHolder(summaryHolder);
        setCardView(cardView);
    }

    private SummaryHolder getSummaryHolder() {
        return summaryHolder;
    }

    private void setSummaryHolder(SummaryHolder summaryHolder) {
        this.summaryHolder = summaryHolder;
    }

    private CardView getCardView() {
        return cardView;
    }

    private void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    @Override
    public void onClick(View v) {
        if(acceptClick()) {
            if (isFirstCardSelect()) {
                getSummaryHolder().setAttemptRunning(true);
                getCardView().setImageResource(getCardView().getCard().getResource());
                getCardView().setShowing(true);
                getSummaryHolder().setTurnedCard(getSummaryHolder().getTurnedCard() + 1);
                getSummaryHolder().setPreviousCardView(getCardView());
                getSummaryHolder().setAttemptRunning(false);
            }else{
                getSummaryHolder().setAttemptRunning(true);
                getCardView().setImageResource(cardView.getCard().getResource());
                getCardView().setShowing(true);
                getSummaryHolder().setTurnedCard(getSummaryHolder().getTurnedCard() + 1);

                if(isSuccessfulMatch()){
                    onSuccess();
                    getSummaryHolder().setPreviousCardView(null);
                    getSummaryHolder().setAttemptRunning(false);
                }else{
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getSummaryHolder().setTurnedCard(getSummaryHolder().getTurnedCard() - 2);
                            onFail();
                            getSummaryHolder().setPreviousCardView(null);
                            getSummaryHolder().setAttemptRunning(false);
                        }
                    }, 1000);
                }
            }

            getSummaryHolder().setAttempt(getSummaryHolder().getAttempt() + 1);
            updateUI();
        }
    }

    private boolean acceptClick(){
        return getCardView().isShowing() == false && getSummaryHolder().isAttemptRunning() == false;
    }

    private boolean isFirstCardSelect(){
        int attempt = getSummaryHolder().getAttempt();
        return attempt == 0 || attempt % 2 == 0;
    }

    private boolean isSuccessfulMatch(){
        return getCardView().getCard().getName().equals(getSummaryHolder().getPreviousCardView().getCard().getName());
    }

    private void onSuccess(){
        /*int imageAlpha = 120;
        getSummaryHolder().getPreviousCardView().setImageAlpha(imageAlpha);
        getCardView().setImageAlpha(imageAlpha);*/

        if(getSummaryHolder().getTurnedCard() == getSummaryHolder().getRowCount() * getSummaryHolder().getCardInARow()){
            GameActivity gameActivity = (GameActivity) getCardView().getContext();
            Intent newActivity = new Intent(gameActivity, ResultActivity.class);
            gameActivity.finishAffinity();
            newActivity.putExtra("attempt", getSummaryHolder().getAttempt());
            newActivity.putExtra("turnedCard", getSummaryHolder().getTurnedCard());
            newActivity.putExtra("startTime", getSummaryHolder().getStartTime());
            newActivity.putExtra("duration", getSummaryHolder().getDuration());
            gameActivity.startActivity(newActivity);
        }
    }

    private void onFail(){
        CardView previousCardView = getSummaryHolder().getPreviousCardView();
        previousCardView.setImageResource(R.drawable.question);
        previousCardView.setShowing(false);
        getCardView().setImageResource(R.drawable.question);
        getCardView().setShowing(false);
        updateUI();
    }

    private void updateUI(){
        ((GameActivity)getCardView().getContext()).updateUI();
    }
}
