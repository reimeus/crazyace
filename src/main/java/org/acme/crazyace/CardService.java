package org.acme.crazyace;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CardService {

    private static final int        CARDS_PER_SUITE = 13;
    private String                  startTime;
    private List<Card>              fullDeck        = new ArrayList<>();
    private Map<String, List<Card>> playerCardMap   = new LinkedHashMap<>();
    private Card                    currentCard;
    private int                     currentPlayerIndex;
    private String                  winner;
    private Suit                    aceSuit;

    /**
     * Get map of players & their # of cards
     * 
     * @return map of card counts
     */
    public Map<String, Integer> getPlayersMap() {
        Map<String, Integer> playersMap = new HashMap<>();
        for (Map.Entry<String, List<Card>> entry : playerCardMap.entrySet()) {
            playersMap.put(entry.getKey(), entry.getValue().size());
        }

        return playersMap;
    }

    public CardService() {
        resetDeck();
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerCardMap.size();
    }

    public String getCurrentPlayer() {
        String[] players = playerCardMap.keySet().toArray(new String[0]);
        String playerName = players[currentPlayerIndex];
        return playerName;
    }

    private void resetDeck() {
        fullDeck.clear();
        int idCount = 0;
        for (Suit s : Suit.values()) {
            for (int i = 1; i <= CARDS_PER_SUITE; i++) {
                fullDeck.add(new Card(++idCount, s, i));
            }
        }
        Collections.shuffle(fullDeck);
    }

    public Card getCardFromDeck() {
        if (fullDeck.isEmpty()) {
            throw new IllegalStateException("No cards in deck");
        }

        return fullDeck.remove(0);
    }

    public void returnCard(Card card) {
        fullDeck.add(card);
        Collections.shuffle(fullDeck);
    }

    /**
     * Deal in player
     * 
     * @param playerName
     */
    public String addPlayer(String playerName) {
        if (playerCardMap.containsKey(playerName)) {
            throw new IllegalArgumentException(playerName
                    + " is already playing");
        }

        if (playerCardMap.size() == 1) { // someone already playing
            startTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            currentCard = getCardFromDeck();
        }

        playerCardMap.put(playerName, new ArrayList<>());
        dealCard(playerName, 7);

        return "carddeck";
    }

    public void dealCard(String playerName) {
        dealCard(playerName, 1);
    }

    public void dealCard(String playerName, int count) {
        if (!playerCardMap.containsKey(playerName)) {
            throw new IllegalArgumentException("Unknown player " + playerName);
        }

        List<Card> cards = playerCardMap.get(playerName);

        for (int i = 0; i < count; i++) {
            cards.add(getCardFromDeck());
        }

        System.out.println("Cards: " + cards);
    }

    public void clear() {
        resetDeck();
        startTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public List<Card> getPlayerCards(String playerName) {
        if (!playerCardMap.containsKey(playerName)) {
            throw new IllegalArgumentException("Unknown player " + playerName);
        }

        return playerCardMap.get(playerName);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getNextPlayerName() {
        return "Player " + (playerCardMap.size() + 1);
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
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
