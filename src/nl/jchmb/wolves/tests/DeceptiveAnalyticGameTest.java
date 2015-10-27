package nl.jchmb.wolves.tests;

import java.util.ArrayList;
import java.util.List;

import nl.jchmb.wolves.core.Agent;
import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.policy.AnalyticInnocentPolicy;
import nl.jchmb.wolves.core.policy.DeceptiveWolfPolicy;
import nl.jchmb.wolves.core.policy.MixedPolicy;
import nl.jchmb.wolves.core.policy.RandomPolicy;
import nl.jchmb.wolves.core.policy.RoleDependentPolicy;
import nl.jchmb.wolves.core.policy.VoteInnocentPolicy;

public class DeceptiveAnalyticGameTest {
	public static void main(String[] args) {
		List<Agent> agents = new ArrayList<Agent>();
		Agent agent;
		for (int i = 0; i < 7; i++) {
			agent = new Agent("a_" + i);
			agent.setVotePolicy(
				new RoleDependentPolicy(
					new MixedPolicy(
						Float.parseFloat(args[0]),
						new VoteInnocentPolicy(),
						new DeceptiveWolfPolicy()
					),
					new AnalyticInnocentPolicy()
				)
			);
			agents.add(agent);
		}
		Evaluator evaluator = new Evaluator(agents, 2);
		evaluator.evaluate(50000);
	}
}
