package org.acme.crazyace;

public class CardView {

    private int     id;
    private String  url;
    private Suit    suit;
    private boolean ace;

    public CardView(int id, String url, Suit suit, boolean ace) {
        this.id = id;
        this.url = url;
        this.suit = suit;
        this.ace = ace;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isAce() {
        return ace;
    }
}
