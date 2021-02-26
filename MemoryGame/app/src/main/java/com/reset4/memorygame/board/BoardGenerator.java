package com.reset4.memorygame.board;

import android.content.Context;
import android.widget.LinearLayout;

import com.reset4.memorygame.R;
import com.reset4.memorygame.action.OnCardSelectListener;
import com.reset4.memorygame.board.category.AnimalCategory;
import com.reset4.memorygame.board.property.Card;
import com.reset4.memorygame.board.property.SummaryHolder;
import com.reset4.memorygame.ui.CardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by eilkyam on 20.12.2017.
 */

public class BoardGenerator {

    public ArrayList<Card> generateBoard(LinearLayout boardLayout, SummaryHolder summaryHolder){
        int row_count = summaryHolder.getRowCount();
        int card_in_a_row = summaryHolder.getCardInARow();
        int distinct_card_count = (row_count * card_in_a_row) / 2;
        ArrayList<Card> selected_cards = selectCards(boardLayout.getContext(), distinct_card_count);
        selected_cards.addAll(selected_cards);
        Collections.shuffle(selected_cards);

        int row_layout_padding_bottom = boardLayout.getContext().getResources().getDimensionPixelOffset(R.dimen.row_layout_padding_bottom);
        int carview_padding = boardLayout.getContext().getResources().getDimensionPixelOffset(R.dimen.carview_padding);
        LinearLayout row_layout = null;
        for(int j = 0; j < row_count; j ++) {
            row_layout = new LinearLayout(boardLayout.getContext());

            row_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
            row_layout.setPadding(0, 0, 0, row_layout_padding_bottom);
            row_layout.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < card_in_a_row; i++) {
                Card card = selected_cards.get((j * card_in_a_row) + i);
                CardView cardView = new CardView(boardLayout.getContext(), card);
                cardView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                cardView.setPadding(carview_padding, 0, carview_padding, 0);
                cardView.setImageResource(R.drawable.question);
                cardView.setClickable(true);
                cardView.setOnClickListener(new OnCardSelectListener(summaryHolder, cardView));
                row_layout.addView(cardView);
            }
            boardLayout.addView(row_layout);
        }
        return selected_cards;
    }

    private ArrayList<Card> selectCards(Context context, int distinct_card_count){
        ArrayList<Card> all_cards = new AnimalCategory(context).getCards();
        ArrayList<Card> selected_cards = new ArrayList<>();
        for(int i=0; i < distinct_card_count; i++){
            selected_cards.add(selectCard(all_cards));
        }
        return selected_cards;
    }

    private Card selectCard(ArrayList<Card> cards){
        Random rand = new Random();
        int value = rand.nextInt(cards.size());
        Card card = cards.get(value);
        cards.remove(value);
        return card;
    }
}
