package org.acme.crazyace;

import java.util.HashMap;
import java.util.Map;

public enum Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES;

    private static Map<String, Suit> suitMap;

    public static Suit getSuit(String suitName) {
        if (suitMap == null) {
            initMap();
        }
        return suitMap.get(suitName.toUpperCase());
    }

    private static void initMap() {
        suitMap = new HashMap<>();
        for (Suit suit : values()) {
            suitMap.put(suit.name(), suit);
        }
    }
}