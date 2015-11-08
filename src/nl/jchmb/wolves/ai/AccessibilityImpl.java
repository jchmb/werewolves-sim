package nl.jchmb.wolves.ai;

import java.util.ArrayList;
import java.util.List;

import nl.jchmb.ai.epistemology.Accessibility;
import nl.jchmb.ai.epistemology.Model;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;

public class AccessibilityImpl implements Accessibility<Player, World> {

	@Override
	public Iterable<World> iterate(Model<Player, World> model, Player agent,
			World world) {
		List<World> worlds = new ArrayList<World>();
		if (agent.getRole().equals(Role.WOLF)) {
			worlds.add(world);
		} else {
			for (World w : model.getUniverse()) {
				if (w.playerHasRole(agent, agent.getRole())) {
					worlds.add(w);
				}
			}
		}
		return worlds;
	}

}
