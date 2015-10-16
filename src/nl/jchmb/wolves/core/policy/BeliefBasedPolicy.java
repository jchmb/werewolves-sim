package nl.jchmb.wolves.core.policy;

import java.util.Collections;
import java.util.List;

import nl.jchmb.wolves.ai.BeliefComparator;
import nl.jchmb.wolves.ai.MassFunction;
import nl.jchmb.wolves.ai.World;
import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;

public class BeliefBasedPolicy implements Policy {
	private MassFunction<World> f;
	
	public BeliefBasedPolicy(MassFunction<World> f) {
		this.f = f;
	}
	
	protected void combine(MassFunction<World> g) {
		f = f.combine(g);
	}
	
	@Override
	public Player choose(Player actor, Day day) {
		List<Player> alivePlayers = day.getAlivePlayers();
		Collections.sort(alivePlayers, new BeliefComparator(
				day.getGame(),
				f
		));
		return alivePlayers.isEmpty() ? null : alivePlayers.get(alivePlayers.size() - 1);
	}
	
}
