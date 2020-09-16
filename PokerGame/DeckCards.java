package PokerGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckCards {

	public DeckCards() {
	}

	public enum PlayingCards {
		ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12),
		KING(13);

		private int ord;

		private PlayingCards(int i) {
			this.ord = i;
		}

		public int getOrd() {
			return ord;
		}
	}

	private PlayingCards cdNumber;

	public PlayingCards getCdNumber() {
		return cdNumber;
	}

	public static List<DeckCards> getPackOfCards() {
		List<DeckCards> DeckList = new ArrayList<DeckCards>();

		for (PlayingCards cNums : PlayingCards.values()) {
			DeckCards deckCards = new DeckCards();
			deckCards.cdNumber = cNums;
			DeckList.add(deckCards);
		}

		return DeckList;
	}

	public static void shuffleCards(List<DeckCards> cards) {
		Collections.shuffle(cards); // Using inbuilt Shuffling

	}

//To determine the winner each cards will be compared between players and if its greater returns 1 
//	and add the Single point to the player
	public int compareTo(DeckCards o) {
		if (this.getCdNumber() == o.getCdNumber()) {
			return 0;
		} else if (this.getCdNumber().getOrd() > o.getCdNumber().getOrd()) {
			return 1;
		} else
			return -1;
	}

	@Override
	public String toString() {
		return "CARD [cdNumber=" + cdNumber + "]";
	}
}