package nl.jchmb.wolves.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	private List<Player> players;
	private List<Day> days;
	private int numWolves;
	
	public Game(List<Player> players, int numWolves) {
		this.players = players;
		days = new ArrayList<Day>();
		this.numWolves = numWolves;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public int getNumWolves() {
		return numWolves;
	}
	
	public List<Player> getAlivePlayers() {
		List<Player> alivePlayers = new ArrayList<Player>();
		for (Player player : getPlayers()) {
			if (!player.isDead()) {
				alivePlayers.add(player);
			}
		}
		return alivePlayers;
	}
	
	public List<Day> getHistory() {
		return days;
	}
	
	public boolean terminates() {
		return getVictor() != null;
	}
	
	public Day getCurrentDay() {
		return days.isEmpty() ? null : (days.get(days.size() - 1));
	}
	
	public Day nextDay() {
		Day day = new Day(this, days.size() + 1);
		days.add(day);
		return day;
	}
	
	public Role getVictor() {
		int innocentCount = 0, wolfCount = 0;
		for (Player player : players) {
			if (player.isDead()) {
				continue;
			}
			if (player.getRole().equals(Role.INNOCENT)) {
				innocentCount++;
			} else {
				wolfCount++;
			}
		}
		if (innocentCount == 0) {
			return Role.WOLF;
		} else if (wolfCount == 0) {
			return Role.INNOCENT;
		} else {
			return null;
		}
	}
	
	private void giveRoles() {
		List<Player> playerList = new ArrayList<Player>(players);
		Collections.shuffle(playerList);
		int i = 0;
		for (Player player : playerList) {
			if (i++ < numWolves) {
				player.setRole(Role.WOLF);
			} else {
				player.setRole(Role.INNOCENT);
			}
		}
	}
	
	public Role play(int limit) {
		giveRoles();
		while (!terminates() && 
				(getCurrentDay() == null || getCurrentDay().getNumber() < limit)) {
			playRound();
		}
		return getVictor();
	}
	
	public void playRound() {
		nextDay();
		Player votee;
		for (Player voter : getAlivePlayers()) {
			votee = voter.getAgent().choose(getCurrentDay());
			getCurrentDay().vote(voter, votee);
		}
		getCurrentDay().lynch();
	}
	
	public String toString() {
		String s = "Game (Players: " + players.size() + ", Day: " + getCurrentDay().getNumber() + ")";
		for (Player player : players) {
			s += "\n";
			s += player.getAgent().getName() + " isA " + player.getRole() + " and isDead = " + player.isDead();
		}
		return s;
	}
}
