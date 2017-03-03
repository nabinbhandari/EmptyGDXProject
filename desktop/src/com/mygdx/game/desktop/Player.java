package com.mygdx.game.desktop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created on 3/3/17.
 *
 * @author Nabin Bhandari
 */
public class Player {
    int id;
    private List<Card> cards = new ArrayList<Card>();

    public Player(int id) {
        this.id = id;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> collectCards() {
        List<Card> cardList = new ArrayList<Card>();
        for (Card card : cards) {
            cardList.add(card);
        }
        cards.clear();
        return cardList;
    }

    @SuppressWarnings("Since15")
    public void sort() {
        cards.sort(new Comparator<Card>() {
            @Override
            public int compare(Card card, Card t1) {
                return card.id - t1.id;
            }
        });
    }
}
