package nl.jchmb.wolves.core.policy;

import nl.jchmb.wolves.ai.EvidenceExtractor;
import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;

public class AnalyticInnocentPolicy extends BeliefBasedPolicy {

	private EvidenceExtractor evidenceExtractor;
	
	public AnalyticInnocentPolicy() {
		super();
		evidenceExtractor = new EvidenceExtractor();
	}

	@Override
	public Player choose(Player actor, Day day) {
		if (day.getGame().getID() != lastGameID) {
			lastGameID = day.getGame().getID();
			lastDay = 1;
			reset();
		}
		/* On the second day, start analyzing evidence and updating one's beliefs. */
		if (day.getNumber() > 1) {
			if (lastDay < day.getNumber()) {
				lastDay = day.getNumber();
				pruneMassFunction(day.getGame().getAllPossibleWorlds());
				combine(evidenceExtractor.extract(day.getPreviousDay()));
			}
		}
		
		if (hasMassFunction()) {
			return super.choose(actor, day);
		} else {
			return new RandomPolicy().choose(actor, day);
		}
	}
}
