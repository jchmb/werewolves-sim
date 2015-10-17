package nl.jchmb.wolves.tests;

import java.util.ArrayList;
import java.util.List;

import nl.jchmb.wolves.core.Agent;
import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.policy.AnalyticInnocentPolicy;
import nl.jchmb.wolves.core.policy.RandomPolicy;
import nl.jchmb.wolves.core.policy.RoleDependentPolicy;
import nl.jchmb.wolves.core.policy.VoteInnocentPolicy;

public class RandomGameTest {
	public static void main(String[] args) {
		List<Agent> agents = new ArrayList<Agent>();
		Agent agent;
		for (int i = 0; i < 7; i++) {
			agent = new Agent("a_" + i);
			agent.setPolicy(
				new RoleDependentPolicy(
					new VoteInnocentPolicy(),
					new RandomPolicy()
				)
			);
			agents.add(agent);
		}
		Evaluator evaluator = new Evaluator(agents, 2);
		double evaluation = evaluator.evaluate(50000);
		System.out.println("Random evaluation: " + evaluation);
	}
}
