package nl.jchmb.wolves.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.jchmb.wolves.ai.World;

public class Game {	
	private long id;
	private Set<World> possibleWorlds = null;
	private Set<World> impossibleWorlds;
	private List<Player> players;
	private List<Day> days;
	private int numWolves;
	private boolean nightModeEnabled = false;
	private boolean mayorModeEnabled = false;
	private Player mayor = null;
	
	private static long LAST_ID = 0;
	private static final int DEFAULT_LIMIT = 20000;
	
	public Game(List<Agent> agents, int numWolves) {
		id = LAST_ID++;
		impossibleWorlds = new HashSet<World>();
		players = new ArrayList<Player>();
		for (Agent agent : agents) {
			players.add(new Player(agent));
		}
		days = new ArrayList<Day>();
		this.numWolves = numWolves;
	}
	
	public long getID() {
		return id;
	}
	
	/**
	 * Get all impossible worlds that are excluded by common knowledge.
	 * 
	 * @return
	 */
	public Set<World> getAllImpossibleWorlds() {
		return impossibleWorlds;
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
		/* Prune all impossible worlds, since the impossibility has become common knowledge after the previous lynch. */
		if (getCurrentDay() != null && getCurrentDay().getLynched() != null) {
//			Player lynched = getCurrentDay().getLynched();
//			Player victim = getCurrentDay().getVictim();
//			Set<World> worlds = getAllPossibleWorlds();
//			WorldFilter filter = new WorldFilter();
//			possibleWorlds = filter.assumePlayerHasRole(worlds, lynched, lynched.getRole());
//			if (victim != null) {
//				possibleWorlds = filter.assumePlayerHasRole(possibleWorlds, victim, victim.getRole());
//			}
			//impossibleWorlds.addAll(filter.assumePlayerHasRole(worlds, lynched, lynched.getRole().equals(Role.WOLF) ? Role.INNOCENT : Role.WOLF));
		}
		
		Day day = new Day(this, days.size() + 1);
		days.add(day);
		return day;
	}
	
	public Reward getVictor() {
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
			return Reward.WOLF;
		} else if (wolfCount == 0) {
			return Reward.INNOCENT;
		} else if (wolfCount > innocentCount) {
			return Reward.WOLF;
		} else if (innocentCount == 1 && wolfCount == 1) {
			return Reward.NONE;
		} else {
			return null;
		}
	}
	
	public boolean isMayorModeEnabled() {
		return mayorModeEnabled;
	}
	
	public void setMayor(Player mayor) {
		this.mayor = mayor;
	}
	
	public Player getMayor() {
		return mayor;
	}
	
	public void setNightMode(boolean enabled) {
		nightModeEnabled = enabled;
	}
	
	public void setMayorMode(boolean enabled) {
		mayorModeEnabled = enabled;
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
	
	public Reward play() {
		giveRoles();
		while (!terminates() && 
				(getCurrentDay() == null || getCurrentDay().getNumber() < DEFAULT_LIMIT)) {
			playRound();
		}
		return getVictor();
	}
	
	public void playRound() {
		nextDay();
		Player votee, victim;
		for (Player voter : getAlivePlayers()) {
			votee = voter.getAgent().chooseVotee(voter, getCurrentDay());
			getCurrentDay().vote(voter, votee);
			if (nightModeEnabled && voter.getRole().equals(Role.WOLF)) {
				victim = voter.getAgent().chooseVictim(voter, getCurrentDay());
				getCurrentDay().murder(voter, victim);
			}
		}
		getCurrentDay().lynch();
		if (nightModeEnabled) {
			getCurrentDay().murder();
		}
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
