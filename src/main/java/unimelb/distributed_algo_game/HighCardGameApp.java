package unimelb.distributed_algo_game;



import java.util.ArrayList;

import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import unimelb.distributed_algo_game.pokers.Card;
import unimelb.distributed_algo_game.pokers.Deck;

public class HighCardGameApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	
		Deck deck = Deck.getInstance();
		deck.shuffle();
	
		
		Player p1 = new HumanPlayer("Templeton", 1);
		Player p2 = new AIPlayer("AI", 2);
		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);
		p1.setCards(deck.getCards(5));
		p2.setCards(deck.getCards(5));
		t1.start();
	//	t2.start();
	
	}

}