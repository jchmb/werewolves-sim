package nl.jchmb.wolves.core.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;

public class RandomPolicy implements Policy {

	@Override
	public Player choose(Player actor, Day day) {
		List<Player> players = new ArrayList<Player>(day.getAlivePlayers());
		Collections.shuffle(players);
		return players.isEmpty() ? null : players.get(0);
	}
	
}
