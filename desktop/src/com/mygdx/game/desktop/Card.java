package com.mygdx.game.desktop;

import java.util.HashMap;

/**
 * Created on 3/3/17.
 *
 * @author Nabin Bhandari
 */
public class Card {
    public class Color {
        public static final int SPADE = 0, HEART = 1, CLUB = 2, DIAMOND = 3;
    }

    private static HashMap<Integer, Character> cardMap = new HashMap<Integer, Character>();

    static {
        cardMap.put(Color.SPADE, 'S');
        cardMap.put(Color.HEART, 'H');
        cardMap.put(Color.CLUB, 'C');
        cardMap.put(Color.DIAMOND, 'D');
    }

    public static Card newCardFromId(int id) {
        int color = id / 13;
        int index = id % 13;

        if (index > 1 && index < 10) {
            return new Card(id, color, (char) (48 + index));
        } else {
            switch (index) {
                case 0:
                    return new Card(id, color, 'K');
                case 1:
                    return new Card(id, color, 'A');
                case 10:
                    return new Card(id, color, 'T');
                case 11:
                    return new Card(id, color, 'J');
                case 12:
                    return new Card(id, color, 'Q');
                default:
                    return null;
            }
        }
    }

    public int id;
    public int color;
    public char card;

    private Card(int id, int color, char card) {
        this.id = id;
        this.color = color;
        this.card = card;
    }

    @Override
    public String toString() {
        return card + "" + cardMap.get(color);
    }
}
