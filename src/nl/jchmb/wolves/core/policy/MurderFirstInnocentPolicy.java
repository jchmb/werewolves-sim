package nl.jchmb.wolves.core.policy;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class MurderFirstInnocentPolicy implements Policy {

	@Override
	public Player choose(Player actor, Day day) {
		for (Player player : day.getAlivePlayers()) {
			if (player.getRole().equals(Role.INNOCENT)) {
				return player;
			}
		}
		return actor;
	}

}
