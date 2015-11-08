package nl.jchmb.wolves.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.jchmb.ai.belief.revision.LexicographicUpgrader;
import nl.jchmb.ai.belief.revision.NullRevisor;
import nl.jchmb.ai.belief.revision.Revisor;
import nl.jchmb.ai.epistemology.assertion.Assertion;
import nl.jchmb.ai.epistemology.assertion.Conjunction;
import nl.jchmb.ai.epistemology.assertion.Disjunction;
import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;
import nl.jchmb.wolves.core.Vote;

public class EvidenceExtractor {
	
	private Revisor<Player, World> extractVote(Day day) {
		Set<World> possibleWorlds = day.getGame().getAllPossibleWorlds();
		Player player = day.getLynched();
		Role role = player.getRole();
		
		/* Player is more likely to be innocent. */
		if (role == Role.WOLF) {
			Collection<Assertion<Player, World>> assertions = new ArrayList<Assertion<Player, World>>();
			for (Vote vote : day.getVotes()) {
				if (vote.getVotee().equals(player)) {
					assertions.add(
							new RoleAssertion(vote.getVoter(), Role.INNOCENT)
					);
				}
			}
			return new LexicographicUpgrader<Player, World>(
					new Disjunction<Player, World>(assertions)
			);
			
		/* Player is more likely to be a wolf. */
		} else if (role == Role.INNOCENT) {
			Collection<Assertion<Player, World>> assertions = new ArrayList<Assertion<Player, World>>();
			for (Vote vote : day.getVotes()) {
				if (vote.getVotee().equals(player)) {
					assertions.add(
							new RoleAssertion(vote.getVoter(), Role.WOLF)
					);
				}
			}
			return new LexicographicUpgrader<Player, World>(
					new Disjunction<Player, World>(assertions)
			);
			
		} else {
			return new NullRevisor<Player, World>();
		}
	}
}
