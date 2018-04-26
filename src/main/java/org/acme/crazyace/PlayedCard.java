package org.acme.crazyace;

import com.fasterxml.jackson.annotation.JsonValue;

public class PlayedCard {

    private String playerName;
    private int    cardId;
    private Suit   aceSuit;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }

    @JsonValue
    public void setAceSuit(Suit aceSuit) {
        this.aceSuit = aceSuit;
    }

    public Suit getAceSuit() {
        return aceSuit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aceSuit == null) ? 0 : aceSuit.hashCode());
        result = prime * result + cardId;
        result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PlayedCard other = (PlayedCard) obj;
        if (aceSuit != other.aceSuit) {
            return false;
        }
        if (cardId != other.cardId) {
            return false;
        }
        if (playerName == null) {
            if (other.playerName != null) {
                return false;
            }
        } else if (!playerName.equals(other.playerName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlayedCard [playerName=" + playerName + ", cardId=" + cardId + ", aceSuit=" + aceSuit + "]";
    }
}
