/*
 * 
 */
package unimelb.distributed_algo_game.player;

import java.io.Serializable;
import java.util.ArrayList;

import unimelb.distributed_algo_game.pokers.Card;
import unimelb.distributed_algo_game.pokers.Deck;
import unimelb.distributed_algo_game.state.GameState;


// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 *
 * @author Ting-Ying Tsai
 */
/**
 * @author templeton
 *
 */
public abstract class Player implements Serializable, Runnable, NetworkObserver {

	/** The selected card. */
	private Card selectedCard = null;


	/** The player score. */
	protected PlayerScore playerScore = null;

	/** The name. */
	private String name = null;

	/** The id. */
	private int id = -1;
	
	/** The boolean of whether the player is a dealer or not. */
	private boolean isDealer = false;
	
	/** The game state of the player. */
	private GameState gameState = null;
	
	/** This is the deck of the dealer. */
	private Deck mDeck = null;



	/**
	 * Method to initialize the player name, id and state.
	 *
	 * @param name
	 *            the name
	 * @param id
	 *            the id
	 * @param playerState
	 *            the player state
	 * @param playerScore
	 *            the player score
	 */
	public Player(String name, int id, GameState gameState, PlayerScore playerScore) {
		this.name = name;
		this.id = id;
		this.playerScore = playerScore;
		this.gameState = gameState;
	}

	/**
	 * Returns the player name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the player ID.
	 *
	 * @return the id
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Returns the card user selected from the deck.
	 *
	 * @return the selected card
	 */
	public Card getSelectedCard() {
		return selectedCard;
	}

	/**
	 * This method returns a player score object.
	 *
	 * @return the player score
	 */
	public PlayerScore getPlayerScore() {
		return playerScore;
	}

	/**
	 * This method updates the player score.
	 */
	public void updateScore() {
		playerScore.updateScore();
	}

	/**
	 * Sets the card the user selected from the deck.
	 *
	 * @param card
	 *            the card
	 */
	public void selectFromDeck(Card card) {
		selectedCard = card;
	}

	/**
	 * Prints out the card selected by the user.
	 *
	 * @param option
	 *            the option
	 */
	public void showCard(int option) {
		System.out.println("You selected " + selectedCard.getCardRank() + "," + selectedCard.getPattern());
	}

	/**
	 * This sets the player as the dealer of the game.
	 * @param isDealer
	 */
	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
		// Create card deck for the game and shuffle
		mDeck = Deck.getInstance();
		mDeck.shuffle();
	}
	
	/**
	 * Returns the boolean of whether the player is a dealer or not.
	 * @return
	 */
	public boolean isDealer() {
		return this.isDealer;
	}
	
	/**
	 * Returns the card from the deck at the given index.
	 * @param cardIndex
	 * @return
	 */
	public Card getCard(int cardIndex) {
		Card card = null;
		if(this.isDealer)
			card = mDeck.getCard(cardIndex);
		else {
			System.out.println("Only dealer can be in charge of giving cards");
			throw new NullPointerException();
		}
		
		return card;
	}
	
	/**
	 * Returns the game state of the player.
	 * @return
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Sets the game state of the player.
	 * @param gameState
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * Runs an update
	 */
	public abstract void update();

}
