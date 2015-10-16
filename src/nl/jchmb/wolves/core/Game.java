package nl.jchmb.wolves.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.jchmb.wolves.ai.World;

public class Game {
	private Set<World> possibleWorlds = null;
	private List<Player> players;
	private List<Day> days;
	private int numWolves;
	private static final int DEFAULT_LIMIT = 20000;
	
	public Game(List<Agent> agents, int numWolves) {
		players = new ArrayList<Player>();
		for (Agent agent : agents) {
			players.add(new Player(agent));
		}
		days = new ArrayList<Day>();
		this.numWolves = numWolves;
	}
	
	public Set<World> getAllPossibleWorlds() {
		if (possibleWorlds != null) {
			return possibleWorlds;
		}
		int n = (int) Math.pow(2, players.size());
		Set<World> worlds = new HashSet<World>();
		Set<Player> wolves;
		World world;
		for (int i = 0; i < n; i++) {
			wolves = new HashSet<Player>();
			for (int j = 0; j < players.size(); j++) {
				if (((i >> j) & 0x1) == 1) {
					wolves.add(players.get(j));
				}
				if (wolves.size() > players.size()) {
					break;
				}
			}
			if (wolves.size() == numWolves) {
				world = new World(wolves);
				worlds.add(world);
			}
		}
		possibleWorlds = worlds;
		return worlds;
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
	
	public Day getDay(int number) {
		if (number < 0 || number >= days.size()) {
			return null;
		}
		return days.get(number);
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
	
	public Role play() {
		giveRoles();
		while (!terminates() && 
				(getCurrentDay() == null || getCurrentDay().getNumber() < DEFAULT_LIMIT)) {
			playRound();
		}
		return getVictor();
	}
	
	public void playRound() {
		nextDay();
		Player votee;
		for (Player voter : getAlivePlayers()) {
			votee = voter.getAgent().choose(voter, getCurrentDay());
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
