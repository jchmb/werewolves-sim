package nl.jchmb.wolves.tests;

import java.util.ArrayList;
import java.util.List;

import nl.jchmb.wolves.core.Agent;
import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.policy.RandomPolicy;

public class RandomGameTest {
	public static void main(String[] args) {
		List<Player> players = new ArrayList<Player>();
		Player player;
		Agent agent;
		for (int i = 0; i < 9; i++) {
			agent = new Agent("a_" + i);
			agent.setPolicy(new RandomPolicy());
			player = new Player(agent);
			players.add(player);
		}
		Game game = new Game(players, 3);
		System.out.println(game.play(30));
		System.out.println(game.toString());
	}
}
