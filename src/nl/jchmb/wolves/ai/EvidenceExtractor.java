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
	 * Alpha parameter. Determines the speed of belief update. Analogous to the learning rate in machine learning, but defined differently.
	 */
	private double alpha = 0.6d;
	
	public MassFunction<World> getUniformFunction(Game game) {
		MassFunction<World> f = new MassFunction<World>();
		return buildUniformFunction(game, f);
	}
	
	public MassFunction<World> buildUniformFunction(Game game, MassFunction<World> f) {
		Set<World> worlds = game.getAllPossibleWorlds();
		double mass = 1.0d / ((double) worlds.size());
		
		for (World world : worlds) {
			f.add(world, mass);
		}
		
		return f;
	}
	
	private void extractVote(Day day, Vote vote, MassFunction<World> f) {
		Set<World> possibleWorlds = day.getGame().getAllPossibleWorlds();
		Player player = day.getLynched();
		Player voter = vote.getVoter();
		Role role = player.getRole();
		Set<World> positiveWorlds = new HashSet<World>();
		Set<World> negativeWorlds = new HashSet<World>();
		WorldFilter filter = new WorldFilter();
		double voteCount = (double) day.countVotes(player, day.getVotes());
		
		/* Player is more likely to be innocent. */
		if (role == Role.WOLF) {
			positiveWorlds = filter.assumePlayerHasRole(possibleWorlds, voter, Role.INNOCENT);
			negativeWorlds = filter.assumePlayerHasRole(possibleWorlds, voter, Role.WOLF);
			
		/* Player is more likely to be a wolf. */
		} else if (role == Role.INNOCENT) {
			positiveWorlds = filter.assumePlayerHasRole(possibleWorlds, voter, Role.WOLF);
			negativeWorlds = filter.assumePlayerHasRole(possibleWorlds, voter, Role.INNOCENT);
			
		} else {
			buildUniformFunction(day.getGame(), f);
			return;
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
