package PokerGame;

public class Game {
	public static void main(String[] args) {
		CardGame playCards = new CardGame();
		playCards.playGame(4, 3);
		playCards.displayWinners();

	}
}