package org.acme.crazyace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardResponse {

    private List<CardView>       cards = new ArrayList<>();
    private String               startTime;
    private String               currentCard;
    private String               currentPlayer;
    private String               winner;
    private Map<String, Integer> playersMap;
    private Suit                 aceSuit;

    public CardResponse(String startTime, String currentCard) {
        this.startTime = startTime;
        this.currentCard = currentCard;
    }

    public List<CardView> getCards() {
        return cards;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getCurrentCard() {
        return currentCard;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Map<String, Integer> getPlayersMap() {
        return playersMap;
    }

    public void setPlayersMap(Map<String, Integer> playersMap) {
        this.playersMap = playersMap;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Suit getAceSuit() {
        return aceSuit;
    }

    public void setAceSuit(Suit aceSuit) {
        this.aceSuit = aceSuit;
    }
}
