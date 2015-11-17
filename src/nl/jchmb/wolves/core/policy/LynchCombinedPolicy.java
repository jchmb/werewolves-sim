package nl.jchmb.wolves.core.policy;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;

public class LynchCombinedPolicy implements Policy {

	private static long lastGameID = -1;
	private static int lastDay = 0;
	private static Player target = null;
	
	@Override
	public Player choose(Player actor, Day day) {
		long gameId = day.getGame().getID();
		if (gameId > lastGameID || day.getNumber() > lastDay) {
			lastGameID = gameId;
			lastDay = day.getNumber();
			target = new RandomPolicy().choose(actor, day);
		}
		return target;
	}

}
