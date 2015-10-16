package nl.jchmb.wolves.core.policy;

import java.util.Collections;
import java.util.List;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class VoteInnocentPolicy implements Policy {

	@Override
	public Player choose(Player actor, Day day) {
		List<Player> players = day.getAlivePlayers();
		Collections.shuffle(players);
		for (Player player : players) {
			if (player.getRole().equals(Role.INNOCENT)) {
				return player;
			}
		}
		return null;
	}

}
