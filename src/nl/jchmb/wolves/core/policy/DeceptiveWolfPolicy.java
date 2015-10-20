package nl.jchmb.wolves.core.policy;

import java.util.Collections;
import java.util.List;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class DeceptiveWolfPolicy implements Policy {
	
	private Policy fallbackPolicy;
	
	public DeceptiveWolfPolicy() {
		fallbackPolicy = new RandomPolicy();
	}
	
	@Override
	public Player choose(Player actor, Day day) {
		Player candidateWolf = null;
		List<Player> alivePlayers = day.getAlivePlayers();
		Collections.shuffle(alivePlayers);
		
		for (Player player : alivePlayers) {
			if (!player.equals(actor) && !player.isDead() && player.getRole().equals(Role.WOLF)) {
				candidateWolf = player;
			}
		}
		
		if (candidateWolf != null) {
			return candidateWolf;
		} else {
			return fallbackPolicy.choose(actor, day);
		}
	}

}
