package nl.jchmb.wolves.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day {
	private Game game;
	private int n;
	private List<Vote> votes;
	private List<Vote> murderVotes;
	private Player lynched = null;
	private Player victim = null;
	
	public Day(Game game, int n) {
		this.game = game;
		this.n = n;
		votes = new ArrayList<Vote>();
		murderVotes = new ArrayList<Vote>();
	}
	
	public Game getGame() {
		return game;
	}
	
	public List<Player> getAlivePlayers() {
		return game.getAlivePlayers();
	}
	
	public Day getPreviousDay() {
		return game.getDay(getNumber() - 2);
	}
	
	public int countVotes(Player player, List<Vote> votes) {
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
	
	public Vote murder(Player voter, Player votee) {
		Vote vote = new Vote(voter, votee);
		murderVotes.add(vote);
		return vote;
	}
	
	public Player murder() {
		victim = findMaxPlayer(murderVotes);
		if (victim != null) {
			victim.setDead(true);
		}
		return victim;
	}
	
	public Player lynch() {
		lynched = findMaxPlayer(votes);
		if (lynched != null) {
			lynched.setDead(true);
		}
		return lynched;
	}
	
	public Player findMaxPlayer(List<Vote> votes) {
		List<Player> maxPlayers = null;
		Player maxPlayer = null;
		int maxVotes = 0;
		int count;
		for (Player alivePlayer : getAlivePlayers()) {
			count = countVotes(alivePlayer, votes);
			if (count > maxVotes) {
				maxPlayers = new ArrayList<Player>();
				maxVotes = count;
				maxPlayers.add(alivePlayer);
			} else if (count == maxVotes && maxPlayers != null) {
				maxPlayers.add(alivePlayer);
			}
		}
		
		maxPlayer = (maxPlayers == null || maxPlayers.size() != 1) ? null : maxPlayers.get(0);
		return maxPlayer;
		
	}
	
	public Player getVictim() {
		return victim;
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
