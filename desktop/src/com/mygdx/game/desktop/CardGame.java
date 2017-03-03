package com.mygdx.game.desktop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created on 3/3/17.
 *
 * @author Nabin Bhandari
 */
public class CardGame {

    private int noOfPlayers;
    public List<Player> players = new ArrayList<Player>();
    public List<Card> cardsInDeck = new ArrayList<Card>() {
    };

    public CardGame(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
        init();
        initPlayers();
    }

    private void init() {
        for (int i = 0; i <= 51; i++) {
            cardsInDeck.add(new Card(i));
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

    /**
     * Deal cards one for each player sequentially.
     *
     * @param noOfCardsForEach No of cards to be dealt per Player.
     * @return true if dealing succeed.
     */
    public boolean deal(int noOfCardsForEach) {
        if (cardsInDeck.size() != 52) {
            return false;
        }
        for (int i = 0; i < noOfCardsForEach; i++) {
            for (Player player : players) {
                Card card = cardsInDeck.get(cardsInDeck.size() - 1);
                cardsInDeck.remove(card);
                player.addCard(card);
            }
        }
        return true;
    }

    /**
     * Deal cards in 5-4-4 pattern
     *
     * @return true if dealing succeed.
     */
    public boolean nepaliDeal() {
        if (cardsInDeck.size() != 52) {
            return false;
        }
        for (Player player : players) {
            giveSomeCardsToPlayer(player, 5);
        }
        for (Player player : players) {
            giveSomeCardsToPlayer(player, 4);
        }
        for (Player player : players) {
            giveSomeCardsToPlayer(player, 4);
        }
        return true;
    }

    private void giveSomeCardsToPlayer(Player player, int noOfCards) {
        for (int i = 0; i < noOfCards; i++) {
            Card card = cardsInDeck.get(cardsInDeck.size() - 1);
            cardsInDeck.remove(card);
            player.addCard(card);
        }
    }

    private static String displayCards(List<Card> cards) {
        Card[] cardsArray = new Card[0];
        cardsArray = cards.toArray(cardsArray);
        return Arrays.toString(cardsArray);
    }

    public void collectCardsFromPlayers() {
        for (Player player : players) {
            cardsInDeck.addAll(player.collectCards());
        }
    }

    public boolean nepaliShuffle() {
        if (cardsInDeck.size() != 52) {
            return false;
        }
        int noOfShuffles = 2 + (int) (Math.random() * 5);
        for (int i = 0; i < noOfShuffles; i++) {
            int start = (int) (Math.random() * 5);
            int end = 40 - (int) (Math.random() * 10);

            for (int j = start; j < end; j++) {
                Card card = cardsInDeck.get(start);
                cardsInDeck.remove(card);
                cardsInDeck.add(card);
            }
        }
        System.out.println("shuffled " + noOfShuffles + " times.");
        return true;
    }

    public static void main(String[] args) {
        CardGame cardGame = new CardGame(4);
        System.out.println("initial cards: " + displayCards(cardGame.cardsInDeck));
        cardGame.deal(13);
        for (Player player : cardGame.players) {
            player.sort();
            System.out.println("Player " + player.id + ": " + displayCards(player.getCards()));
        }

        cardGame.collectCardsFromPlayers();
        System.out.println("collected cards: " + displayCards(cardGame.cardsInDeck));
        cardGame.nepaliShuffle();
        System.out.println("shuffled cards: " + displayCards(cardGame.cardsInDeck));

        cardGame.nepaliDeal();
        for (Player player : cardGame.players) {
            player.sort();
            System.out.println("Player " + player.id + ": " + displayCards(player.getCards()));
        }
    }
}
