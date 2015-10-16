package nl.jchmb.wolves.tests;

import java.util.ArrayList;
import java.util.List;

import nl.jchmb.wolves.core.Agent;
import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class Evaluator {
	public int numWolves;
	private List<Agent> agents;
	
	public Evaluator(List<Agent> agents, int numWolves) {
		this.agents = agents;
		this.numWolves = numWolves;
	}
	
	/**
	 * Returns the win ratio of wolves.
	 * 
	 * @param n sampleSize
	 * @return
	 */
	public double evaluate(int n) {
		double wolfCount = 0.0d;
		double innocentCount = 0.0d;
		double neutralCount = 0.0d;
		Game game;
		Role victor;
		double doubleN = (double) n;
		for (int i = 0; i < n; i++) {
			game = new Game(agents, numWolves); 
			victor = game.play();
			if (victor != null) {
				if (victor.equals(Role.WOLF)) {
					wolfCount += 1.0d;
				} else {
					innocentCount += 1.0d;
				}
			} else {
				neutralCount += 1.0d;
			}
		}
		return wolfCount / ((doubleN - neutralCount));
	}
}