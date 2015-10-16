package nl.jchmb.wolves.ai;

import java.util.Map;

import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class World {
	private Map<Player, Role> state;
	
	public World(Map<Player, Role> state) {
		this.state = state;
	}
	
	public boolean playerHasRole(Player player, Role role) {
		return state.containsKey(player) ? state.get(player).equals(role) : false;
	}
}
