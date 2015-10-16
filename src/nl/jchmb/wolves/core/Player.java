package nl.jchmb.wolves.core;

public class Player {
	private boolean dead;
	private Agent agent;
	private Role role;
	
	public Player(Agent agent) {
		this.agent = agent;
		dead = false;
	}
	
	public Agent getAgent() {
		return agent;
	}
	
	public Role getRole() {
		return role;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	@Override
	public int hashCode() {
		return agent.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Player)) {
			return false;
		}
		return agent.equals(((Player) other).agent);
	}
}
