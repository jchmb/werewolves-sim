package nl.jchmb.wolves.ai;

import java.util.HashSet;
import java.util.Set;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Game;
import nl.jchmb.wolves.core.Player;
import nl.jchmb.wolves.core.Role;
import nl.jchmb.wolves.core.Vote;

public class EvidenceExtractor {
	/**
	 * Alpha parameter. Determines the speed of belief update. Analogous to the learning rate in machine learning.
	 */
	private double alpha = 0.6d;
	
	private void extractVote(Day day, Vote vote, MassFunction<World> f) {
		Set<World> possibleWorlds = day.getGame().getAllPossibleWorlds();
		Player player = day.getLynched();
		Player voter = vote.getVoter();
		Role role = player.getRole();
		Set<World> positiveWorlds = new HashSet<World>();
		Set<World> negativeWorlds = new HashSet<World>();
		WorldFilter filter = new WorldFilter();
		double voteCount = (double) day.countVotes(player);
		
		/* Player is more likely to be innocent. */
		if (role == Role.WOLF) {
			positiveWorlds = filter.assumePlayerHasRole(possibleWorlds, voter, Role.INNOCENT);
			negativeWorlds = filter.assumePlayerHasRole(possibleWorlds, voter, Role.WOLF);
			
		/* Player is more likely to be a wolf. */
		} else if (role == Role.INNOCENT) {
			positiveWorlds = filter.assumePlayerHasRole(possibleWorlds, voter, Role.WOLF);
			negativeWorlds = filter.assumePlayerHasRole(possibleWorlds, voter, Role.INNOCENT);
			
		} else {
			// Error
		}
		
		f.add(positiveWorlds, alpha / voteCount);
		f.add(negativeWorlds, (1 - alpha) / voteCount);
	}
	
	public MassFunction<World> extract(Day day) {
		if (day.getLynched() == null) {
			return null;
		}
		MassFunction<World> f = new MassFunction<World>();
		for (Vote vote : day.getVotes()) {
			extractVote(day, vote, f);
		}
		return f;
	}
}
