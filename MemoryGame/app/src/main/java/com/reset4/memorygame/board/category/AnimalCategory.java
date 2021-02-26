package com.reset4.memorygame.board.category;

import android.content.Context;

import com.reset4.memorygame.R;
import com.reset4.memorygame.board.property.Card;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by eilkyam on 27.12.2017.
 */

public class AnimalCategory extends Category {

    public AnimalCategory(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Card> getCards() {
        return new ArrayList<>(
            Arrays .asList(
                    new Card(getContext().getString(R.string.bat), R.drawable.bat),
                    new Card(getContext().getString(R.string.chicken), R.drawable.chicken),
                    new Card(getContext().getString(R.string.cow), R.drawable.cow),
                    new Card(getContext().getString(R.string.crocodile), R.drawable.crocodile),
                    new Card(getContext().getString(R.string.deer), R.drawable.deer),
                    new Card(getContext().getString(R.string.elephant), R.drawable.elephant),
                    new Card(getContext().getString(R.string.fox), R.drawable.fox),
                    new Card(getContext().getString(R.string.frog), R.drawable.frog),
                    new Card(getContext().getString(R.string.giraffe), R.drawable.giraffe),
                    new Card(getContext().getString(R.string.goat), R.drawable.goat),
                    new Card(getContext().getString(R.string.hippopotam), R.drawable.hippopotam),
                    new Card(getContext().getString(R.string.horse), R.drawable.horse),
                    new Card(getContext().getString(R.string.kangaroo), R.drawable.kangaroo),
                    new Card(getContext().getString(R.string.lion), R.drawable.lion),
                    new Card(getContext().getString(R.string.monkey), R.drawable.monkey),
                    new Card(getContext().getString(R.string.owl), R.drawable.owl),
                    new Card(getContext().getString(R.string.pig), R.drawable.pig),
                    new Card(getContext().getString(R.string.rabbit), R.drawable.rabbit),
                    new Card(getContext().getString(R.string.raccoon), R.drawable.raccoon),
                    new Card(getContext().getString(R.string.rhino), R.drawable.rhino),
                    new Card(getContext().getString(R.string.taurus), R.drawable.taurus),
                    new Card(getContext().getString(R.string.tiger), R.drawable.tiger),
                    new Card(getContext().getString(R.string.turkey), R.drawable.turkey),
                    new Card(getContext().getString(R.string.zebra), R.drawable.zebra))
        );
    }
}
