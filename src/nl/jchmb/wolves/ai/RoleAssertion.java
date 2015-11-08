package nl.jchmb.wolves.ai;

import nl.jchmb.ai.epistemology.Model;
import nl.jchmb.ai.epistemology.assertion.Assertion;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class RoleAssertion implements Assertion<Player, World> {
	private Player player;
	private Role role;
	
	public RoleAssertion(Player player, Role role) {
		this.player = player;
		this.role = role;
	}
	
	@Override
	public boolean resolve(Model<Player, World> model, World world) {
		return world.playerHasRole(player, role);
	}

}
