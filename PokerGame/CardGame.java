package PokerGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CardGame implements PlayGame {
	private List<DeckCards> cards;
	private List<Player> players = new ArrayList<Player>();
	private Map<Player, List<DeckCards>> cardsPlayerMap = new HashMap<Player, List<DeckCards>>();
	private int currentPlayerIdx = 0;
	private static final int numberOfCardsPerPlayerD = 3;
	private int numberOfPlayers = 4;

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public CardGame() {
		cards = DeckCards.getPackOfCards();
	}

	public void distributeCardsForPlayers(List<Player> player) {
		this.players = player;
		DeckCards.shuffleCards(cards);
		if (cardsPlayerMap.size() == 0)
			cardsPlayerMap.clear();

		int m = 0;
		for (Player pl : players) {
			pl.setPoints(0);
			List<DeckCards> cds = new ArrayList<DeckCards>();
			int cardLimit = m + numberOfCardsPerPlayerD;
			for (int i = m; i < cardLimit; i++) {
				cds.add(cards.get(i));
			}
			m = cardLimit;
			cardsPlayerMap.put(pl, cds);
		}
	}

	public void playGame(int numberOfPlayers, int numberofCardtoDealt) {
		this.numberOfPlayers = numberOfPlayers;
		createMultipleUser(numberOfPlayers);

		List<DeckCards> selCards = new ArrayList<DeckCards>();
		DeckCards maxCard = null;
		Player maxPlayer = new Player(0);
		distributeCardsForPlayers(players);
		for (int j = 0; j < numberofCardtoDealt; j++) {
			int s = 0;
			do {
				Player player = getNextPlayer();

				int m = 1;
				DeckCards c = cardsPlayerMap.get(player).get(m - 1);
				cardsPlayerMap.get(player).remove(m - 1);
				if (maxCard == null) {
					maxCard = c;
					maxPlayer = player;
				} else {
					if (maxCard.compareTo(c) < 0) {
						maxCard = c;
						maxPlayer = player;
					}
				}
				selCards.add(c);

				s++;
			} while (s < players.size());
			if (maxPlayer.getPlayerId() > 0)
				maxPlayer.setPoints((maxPlayer.getPoints()) + 1);
			maxCard = null;
			maxPlayer = null;
			displayScores();
		}
	}

	private void displayScores() {
		for (Player player : players) {
			System.out.println("Player " + player.getPlayerId() + " Points: " + player.getPoints());
		}

	}

	public void displayWinners() {
		Collections.sort(players);
		int maxPoints = 0;
		Map<String, List<Player>> playerPointsMap = new TreeMap<String, List<Player>>();
		for (Player p : players) {

			maxPoints = p.getPoints();
			if (playerPointsMap.get(maxPoints + "") != null) {
				List<Player> lst = playerPointsMap.get(maxPoints + "");
				lst.add(p);
				playerPointsMap.put(maxPoints + "", lst);
			} else {
				List<Player> lst = new ArrayList<Player>();
				lst.add(p);
				playerPointsMap.put(maxPoints + "", lst);
			}
		}

		String points = new Integer(players.get(players.size() - 1).getPoints()).toString();
		if (playerPointsMap.get(points) != null && playerPointsMap.get(points).size() > 1) {
			System.out.println("Its a tie, No single winner found");
			playGame(4, 1); // Dealing single cards
			displayWinners(); // Displaying the winner after single Draws

		} else if (playerPointsMap.get(points) != null) {
			System.out.println("Winning Player is -> " + playerPointsMap.get(points).get(0).getPlayerId());
		}
	}

	private void createMultipleUser(int j) {
		if (players.size() != 0) {
			players.clear();
		}

		for (int i = 0; i < j; i++) {
			int id = i + 1;
			Player player = new Player(id);
			players.add(player);
		}

	}

	private Player getNextPlayer() {

		Player p = null;
		if (currentPlayerIdx == players.size()) {
			currentPlayerIdx = 1;
			p = players.get(0);
		} else {
			p = players.get(currentPlayerIdx);
			currentPlayerIdx++;
		}

		return p;
	}
}