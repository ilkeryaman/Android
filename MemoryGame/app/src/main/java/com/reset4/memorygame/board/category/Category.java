package com.reset4.memorygame.board.category;

import android.content.Context;

import com.reset4.memorygame.board.property.Card;

import java.util.ArrayList;

/**
 * Created by eilkyam on 27.12.2017.
 */

public abstract class Category {
    private Context context;

    public Category(Context context){
        setContext(context);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract ArrayList<Card> getCards();
}
