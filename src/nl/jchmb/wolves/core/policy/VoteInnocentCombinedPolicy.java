package nl.jchmb.wolves.core.policy;

import java.util.Collections;
import java.util.List;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class VoteInnocentCombinedPolicy implements Policy {

	private static long lastGameID = -1;
	private static int lastDay = 0;
	private static Player target = null;
	
	@Override
	public Player choose(Player actor, Day day) {
		long gameId = day.getGame().getID();
		int dayNumber = day.getNumber();
		if (gameId > lastGameID || dayNumber > lastDay) {
			lastGameID = gameId;
			lastDay = dayNumber;
			List<Player> players = day.getAlivePlayers();
			Collections.shuffle(players);
			for (Player player : players) {
				if (player.getRole().equals(Role.INNOCENT)) {
					target = player;
					break;
				}
			}
		}
		return target;
	}

}
