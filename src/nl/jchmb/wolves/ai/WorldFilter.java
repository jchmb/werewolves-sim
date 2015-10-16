package nl.jchmb.wolves.ai;

import java.util.HashSet;
import java.util.Set;

import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class WorldFilter {
	/**
	 * Filters all possible worlds in which the assumption that the player has the role is true.
	 * 
	 * @param possibleWorlds
	 * @param player
	 * @param role
	 * @return
	 */
	public Set<World> assumePlayerHasRole(Set<World> possibleWorlds, Player player, Role role) {
		Set<World> filteredWorlds = new HashSet<World>();
		for (World world : possibleWorlds) {
			if (world.playerHasRole(player, role)) {
				filteredWorlds.add(world);
			}
		}
		return filteredWorlds;
	}
}
