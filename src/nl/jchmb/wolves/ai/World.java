package nl.jchmb.wolves.ai;

import java.util.Set;

import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class World {
	private Set<Player> wolves;
	
	public World(Set<Player> wolves) {
		this.wolves = wolves;
	}
	
	public boolean playerHasRole(Player player, Role role) {
		return wolves.contains(player) ? role.equals(Role.WOLF) : role.equals(Role.INNOCENT);
	}
}
