package nl.jchmb.wolves.core;

public class Vote {
	private Player voter;
	private Player votee;
	
	public Vote(Player voter, Player votee) {
		this.voter = voter;
		this.votee = votee;
	}
	
	public Player getVoter() {
		return voter;
	}
	
	public Player getVotee() {
		return votee;
	}
}
