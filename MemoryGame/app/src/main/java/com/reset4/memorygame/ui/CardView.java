package com.reset4.memorygame.ui;

import android.content.Context;
import android.widget.ImageView;

import com.reset4.memorygame.board.property.Card;

/**
 * Created by eilkyam on 28.12.2017.
 */

public class CardView extends ImageView {

    private Card card;
    private boolean showing;

    public CardView(Context context, Card card) {
        super(context);
        setCard(card);
        setShowing(false);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }
}
