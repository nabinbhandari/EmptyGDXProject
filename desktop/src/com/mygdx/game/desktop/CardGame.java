package com.mygdx.game.desktop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created on 3/3/17.
 *
 * @author Nabin Bhandari
 */
public class CardGame {

    private int noOfPlayers;
    public List<Player> players = new ArrayList<Player>();
    public Stack<Card> cardsInDeck = new Stack<Card>();

    public CardGame(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
        init();
        initPlayers();
    }

    private void init() {
        for (int i = 0; i <= 51; i++) {
            cardsInDeck.push(Card.newCardFromId(i));
        }
        Collections.shuffle(cardsInDeck);
    }

    public void initPlayers() {
        for (int i = 0; i < noOfPlayers; i++) {
            players.add(new Player(i));
        }
    }

    public void reset() {
        cardsInDeck.clear();
        players.clear();
        init();
        initPlayers();
    }

    public void deal(int noOfCardsForEach) {
        for (int i = 0; i < noOfCardsForEach; i++) {
            for (Player player : players) {
                player.addCard(cardsInDeck.pop());
            }
        }
    }

    private static String displayCards(List<Card> cards) {
        Card[] cardsArray = new Card[0];
        cardsArray = cards.toArray(cardsArray);
        return Arrays.toString(cardsArray);
    }

    public static void main(String[] args) {
        CardGame cardGame = new CardGame(4);
        cardGame.deal(13);
        for (Player player : cardGame.players) {
            player.sort();
            System.out.println("Player " + player.id + ": " + displayCards(player.getCards()));
        }
    }
}
