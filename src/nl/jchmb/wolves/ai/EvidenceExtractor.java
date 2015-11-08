package nl.jchmb.wolves.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;
import nl.jchmb.wolves.core.Vote;

public class EvidenceExtractor {
	public WorldAcceptor extract(Day day) {
		if (day.getLynched() == null) {
			return null;
		}
		Collection<WorldAcceptor> acceptors = new ArrayList<WorldAcceptor>();
		Collection<Player> players = new ArrayList<Player>();
		for (Vote vote : day.getVotes()) {
			if (vote.getVotee().equals(day.getLynched())) {
				players.add(vote.getVoter());
			}
		}
		
		/* Players are more likely innocent. */
		if (day.getLynched().getRole().equals(Role.WOLF)) {
			for (Player player : players) {
				acceptors.add(new RoleAcceptor(player, Role.WOLF));
			}
			
		/* Players are more likely wolves. */
		} else {
			for (Player player : players) {
				acceptors.add(new RoleAcceptor(player, Role.INNOCENT));
			}
		}
		
		return new DisjunctionAcceptor(acceptors);
	}
}
