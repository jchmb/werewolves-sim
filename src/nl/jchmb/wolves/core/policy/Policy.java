package nl.jchmb.wolves.core.policy;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;

public interface Policy {
	public Player choose(Player actor, Day day);
}
