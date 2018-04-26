package org.acme.crazyace;

public class Card {

	private int id;
	private Suit suit;
	private int value;;

	public Card(int id, Suit suit, int value) {
		this.id = id;
		this.suit = suit;
		this.value = value;
	}

	public String getImageName() {

		String valueString;

		switch (value) {
		case 1:
			valueString = "Ace";
			break;
		case 11:
			valueString = "Jack";
			break;
		case 12:
			valueString = "Queen";
			break;
		case 13:
			valueString = "King";
			break;
		default:
			valueString = Integer.toString(value);
		}

		return (valueString + "_" + suit.name()).toLowerCase();
	}

	@Override
	public String toString() {
		return "Card " + value + " of " + suit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}
}
