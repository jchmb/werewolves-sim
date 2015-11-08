package nl.jchmb.wolves.core.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import nl.jchmb.wolves.ai.BeliefSupport;
import nl.jchmb.wolves.ai.MassFunction;
import nl.jchmb.wolves.ai.RoleAcceptor;
import nl.jchmb.wolves.ai.World;
import nl.jchmb.wolves.ai.WorldAcceptor;
import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class BeliefBasedPolicy implements Policy {
	private static BeliefSupport belief;
	protected static long lastGameID = -1;
	protected static int lastDay = -1;
	private Game game;

	public BeliefBasedPolicy() {
		
	}
	
	protected void reset(Game game) {
		belief = new BeliefSupport(game.getAllPossibleWorlds());
	}
	
	protected void upgrade(WorldAcceptor acceptor) {
		belief.upgrade(acceptor);
	}

	public boolean hasBelief() {
		return belief != null;
	}
	
	@Override
	public Player choose(Player actor, Day day) {
		List<Player> alivePlayers = day.getAlivePlayers();
		List<Player> maxPlayers = new ArrayList<Player>();
		double maxBelief = -1.0d;
		double b;
		for (Player player : alivePlayers) {
			b = belief.getBelief(new RoleAcceptor(player, Role.WOLF));
			if (b > maxBelief) {
				maxPlayers = new ArrayList<Player>();
				b = maxBelief;
				maxPlayers.add(player);
			} else if (b == maxBelief) {
				maxPlayers.add(player);
			}
		}
		if (maxPlayers.isEmpty()) {
			return null;
		}
		Collections.shuffle(maxPlayers);
		return maxPlayers.get(0);
	}
	
}
