package com.mygdx.game.desktop;

import java.util.HashMap;

/**
 * Created on 3/3/17.
 *
 * @author Nabin Bhandari
 */
public class Card {
    private static HashMap<Integer, Character> cardMap = new HashMap<Integer, Character>();

    static {
        cardMap.put(0, 'S');
        cardMap.put(1, 'H');
        cardMap.put(2, 'C');
        cardMap.put(3, 'D');
    }

    public int id;
    public int index;
    public int color;
    public char cardChar;

    public Card(int id) {
        this.id = id;
        index = id % 13;
        color = id / 13;
        cardChar = getCardChar();
    }

    private char getCardChar() {
        if (index > 1 && index < 10) {
            return (char) (48 + index);
        } else {
            switch (index) {
                case 0:
                    return 'K';
                case 1:
                    return 'A';
                case 10:
                    return 'T';
                case 11:
                    return 'J';
                case 12:
                    return 'Q';
                default:
                    return '!';
            }
        }
    }

    @Override
    public String toString() {
        return cardChar + "" + cardMap.get(color);
    }
}
