package nl.jchmb.wolves.core;

import nl.jchmb.wolves.core.policy.Policy;

public class Agent {
	private String name;
	private Policy votePolicy;
	private Policy murderPolicy;
	
	public Agent(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setVotePolicy(Policy policy) {
		this.votePolicy = policy;
	}
	
	public void setMurderPolicy(Policy policy) {
		this.murderPolicy = policy;
	}
	
	public Player chooseVotee(Player player, Day day) {
		return votePolicy.choose(player, day);
	}
	
	public Player chooseVictim(Player player, Day day) {
		return murderPolicy.choose(player, day);
	}
}
