package nl.jchmb.wolves.tests;

import java.util.ArrayList;
import java.util.List;

import nl.jchmb.wolves.core.Agent;
import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.policy.AnalyticInnocentPolicy;
import nl.jchmb.wolves.core.policy.LynchCombinedPolicy;
import nl.jchmb.wolves.core.policy.MurderFirstInnocentPolicy;
import nl.jchmb.wolves.core.policy.RandomPolicy;
import nl.jchmb.wolves.core.policy.RoleDependentPolicy;
import nl.jchmb.wolves.core.policy.VoteInnocentCombinedPolicy;
import nl.jchmb.wolves.core.policy.VoteInnocentPolicy;

public class AnalyticGameTest {
	public static void main(String[] args) {
		List<Agent> agents = new ArrayList<Agent>();
		Agent agent;
		for (int i = 0; i < 12; i++) {
			agent = new Agent("a_" + i);
			agent.setVotePolicy(
				new RoleDependentPolicy(
					new VoteInnocentCombinedPolicy(),
					new AnalyticInnocentPolicy()
					//new LynchCombinedPolicy()
				)
			);
			agent.setMurderPolicy(
					new MurderFirstInnocentPolicy()
			);
			agents.add(agent);
		}
		Evaluator evaluator = new Evaluator(agents, 3, false);
		evaluator.evaluate(1000);
	}
}
