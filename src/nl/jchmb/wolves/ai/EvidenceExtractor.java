package nl.jchmb.wolves.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.jchmb.wolves.ai.acceptor.DisjunctionAcceptor;
import nl.jchmb.wolves.ai.acceptor.RoleAcceptor;
import nl.jchmb.wolves.ai.acceptor.WorldAcceptor;
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
		Collection<Player> players = new HashSet<Player>();
		extractPlayers(day, day.getLynched(), players);
		
		/* Players are more likely innocent. */
		if (day.getLynched().getRole().equals(Role.WOLF)) {
			for (Player player : players) {
				acceptors.add(new RoleAcceptor(player, Role.INNOCENT));
			}
			
		/* Players are more likely wolves, but only if one wolf has already died. */
		} else {
			if (!wolfHasDied(day)) {
				return null;
			}
			
			for (Player player : players) {
				acceptors.add(new RoleAcceptor(player, Role.WOLF));
			}
		}
		
		return new DisjunctionAcceptor(acceptors);
	}
	
	private void extractPlayers(Day day, Player lynched, Collection<Player> players) {
		for (Vote vote : day.getVotes()) {
			if (vote.getVotee().equals(lynched)) {
				players.add(vote.getVoter());
			}
		}
		if (day.getNumber() > 1) {
			extractPlayers(day.getPreviousDay(), lynched, players);
		}
	}
	
	private boolean wolfHasDied(Day day) {
		for (Player player : day.getGame().getPlayers()) {
			if (player.isDead() && player.getRole().equals(Role.WOLF)) {
				return true;
			}
		}
		return false;
	}
}
