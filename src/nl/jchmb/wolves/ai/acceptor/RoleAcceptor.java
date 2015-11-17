package nl.jchmb.wolves.ai.acceptor;

import nl.jchmb.wolves.ai.World;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class RoleAcceptor implements WorldAcceptor {

	private Player player;
	private Role role;
	
	public RoleAcceptor(Player player, Role role) {
		this.player = player;
		this.role = role;
	}
	
	@Override
	public boolean accept(World world) {
		return world.playerHasRole(player, role);
	}

}
