package nl.jchmb.wolves.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day {
	private Game game;
	private int n;
	private List<Vote> votes;
	private Player lynched = null;
	
	public Day(Game game, int n) {
		this.game = game;
		this.n = n;
		votes = new ArrayList<Vote>();
	}
	
	public Game getGame() {
		return game;
	}
	
	public List<Player> getAlivePlayers() {
		return game.getAlivePlayers();
	}
	
	public int countVotes(Player player) {
		int count = 0;
		for (Vote vote : votes) {
			if (vote.getVotee().equals(player)) {
				count++;
			}
		}
		return count;
	}
	
	public Vote vote(Player voter, Player votee) {
		Vote vote = new Vote(voter, votee);
		votes.add(vote);
		return vote;
	}
	
	public void lynch() {
		List<Player> maxPlayers = null;
		int maxVotes = 0;
		int count;
		for (Player alivePlayer : getAlivePlayers()) {
			count = countVotes(alivePlayer);
			if (count > maxVotes) {
				maxPlayers = new ArrayList<Player>();
				maxVotes = count;
				maxPlayers.add(alivePlayer);
			} else if (count == maxVotes && maxPlayers != null) {
				maxPlayers.add(alivePlayer);
			}
		}
		
		lynched = (maxPlayers == null || maxPlayers.size() != 1) ? null : maxPlayers.get(0);
		if (lynched != null) {
			lynched.setDead(true);
		}
	}
	
	public Player getLynched() {
		return lynched;
	}
	
	public List<Vote> getVotes() {
		return votes;
	}
	
	public int getNumber() {
		return n;
	}
}
