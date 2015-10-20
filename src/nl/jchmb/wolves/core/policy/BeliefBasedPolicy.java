package nl.jchmb.wolves.core.policy;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import nl.jchmb.wolves.ai.BeliefComparator;
import nl.jchmb.wolves.ai.MassFunction;
import nl.jchmb.wolves.ai.World;
import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;

public class BeliefBasedPolicy implements Policy {
	private static MassFunction<World> f = null;
	protected static long lastGameID = -1;
	protected static int lastDay = -1;

	public BeliefBasedPolicy() {
		
	}
	
	protected void pruneMassFunction(Set<World> possibleWorlds) {
		if (f != null) {
			f.prune(possibleWorlds);
			f.merge();
			f.normalize();
		}
	}
	
	protected void reset() {
		f = null;
	}
	
	protected void combine(MassFunction<World> g) {
		if (f == null && g != null) {
			f = g;
		} else if (f != null && g != null) {
			
			f = f.combine(g);
		}
	}
	
	public boolean hasMassFunction() {
		return f != null;
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
