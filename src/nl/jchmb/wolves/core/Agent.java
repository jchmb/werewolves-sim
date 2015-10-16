package nl.jchmb.wolves.core;

import nl.jchmb.wolves.core.policy.Policy;

public class Agent {
	private String name;
	private Policy policy;
	
	public Agent(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
	
	public Player choose(Day day) {
		return policy.choose(day);
	}
}
