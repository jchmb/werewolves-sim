package nl.jchmb.wolves.tests;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import nl.jchmb.wolves.core.Agent;
import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Reward;
import nl.jchmb.wolves.core.Role;

public class Evaluator {
	public int numWolves;
	private List<Agent> agents;
	private boolean nightModeEnabled = false;
	
	public Evaluator(List<Agent> agents, int numWolves) {
		this(agents, numWolves, false);
	}
	
	public Evaluator(List<Agent> agents, int numWolves, boolean nightModeEnabled) {
		this.agents = agents;
		this.numWolves = numWolves;
		this.nightModeEnabled = nightModeEnabled;
	}
	
	/**
	 * Prints the win ratio of wolves.
	 * 
	 * @param n sampleSize
	 */
	public void evaluate(int n) {
		double wolfCount = 0.0d;
		double innocentCount = 0.0d;
		double neutralCount = 0.0d;
		Game game;
		Reward victor;
		double doubleN = (double) n;
		for (int i = 0; i < n; i++) {
			game = new Game(agents, numWolves);
			game.setNightMode(nightModeEnabled);
			victor = game.play();
			if (victor != null) {
				if (victor.equals(Reward.WOLF)) {
					wolfCount += 1.0d;
				} else if (victor.equals(Reward.INNOCENT)) {
					innocentCount += 1.0d;
				} else {
					neutralCount += 1.0d;
				}
			} else {
				neutralCount += 1.0d;
			}
			System.out.println(i + 1);
		}
		
		wolfCount += neutralCount / 2;
		innocentCount += neutralCount / 2;
		
		wolfCount /= doubleN;
		innocentCount /= doubleN;
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		System.out.println(df.format(wolfCount) + " & " + df.format(innocentCount) + " \\\\");
		//System.out.println("Statistics [W=" + (wolfCount / doubleN) + ", I=" + (innocentCount / doubleN) + ", D=" + (neutralCount / doubleN));
	}
	
	public class Evaluation {
		private double wolfCount;
		private double innocentCount;
		private double neutralCount;
		
		public Evaluation(double wolfCount, double innocentCount, double neutralCount) {
			this.wolfCount = wolfCount;
			this.innocentCount = innocentCount;
			this.neutralCount = neutralCount;
		}
	}
}
