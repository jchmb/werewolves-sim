package nl.jchmb.wolves.core.policy;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class RoleDependentPolicy implements Policy {
	private Policy wolfPolicy;
	private Policy innocentPolicy;
	
	public RoleDependentPolicy(Policy wolfPolicy, Policy innocentPolicy) {
		this.wolfPolicy = wolfPolicy;
		this.innocentPolicy = innocentPolicy;
	}
	
	@Override
	public Player choose(Player actor, Day day) {
		if (actor.getRole().equals(Role.WOLF)) {
			return wolfPolicy.choose(actor, day);
		} else {
			return innocentPolicy.choose(actor, day);
		}
	}

}
