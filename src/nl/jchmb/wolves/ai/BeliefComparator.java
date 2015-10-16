package nl.jchmb.wolves.ai;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class BeliefComparator implements Comparator<Player> {
	private Game game;
	private MassFunction<World> f;
	private Map<Player, Double> beliefCache;
	private WorldFilter filter;
	
	public BeliefComparator(Game game, MassFunction<World> f) {
		this.game = game;
		this.f = f;
		beliefCache = new HashMap<Player, Double>();
		filter = new WorldFilter();
	}
	
	private double getBelief(Set<World> worlds, Player p) {
		Set<World> worldsWherePIsWolf = filter.assumePlayerHasRole(worlds, p, Role.WOLF);
		return f.getBelief(worldsWherePIsWolf);
	}
	
	@Override
	public int compare(Player p1, Player p2) {
		double b1, b2;
		Set<World> possibleWorlds = game.getAllPossibleWorlds();
		
		if (!beliefCache.containsKey(p1)) {
			beliefCache.put(p1, getBelief(possibleWorlds, p1));
		}
		if (!beliefCache.containsKey(p2)) {
			beliefCache.put(p2, getBelief(possibleWorlds, p2));
		}
		b1 = beliefCache.get(p1);
		b2 = beliefCache.get(p2);
		return b1 == b2 ? 0 : (b1 > b2 ? 1 : -1);
	}
	
}
