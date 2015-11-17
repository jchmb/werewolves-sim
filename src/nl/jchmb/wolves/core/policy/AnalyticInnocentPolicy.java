package nl.jchmb.wolves.core.policy;

import nl.jchmb.wolves.ai.EvidenceExtractor;
import nl.jchmb.wolves.ai.WorldAcceptor;
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
			reset(day.getGame());
			loadMaxPlayers(day);
		}
		/* On the second day, start analyzing evidence and updating one's beliefs. */
		if (day.getNumber() > 1) {
			if (lastDay < day.getNumber()) {
				loadMaxPlayers(day);
				lastDay = day.getNumber();
				WorldAcceptor acceptor = evidenceExtractor.extract(day.getPreviousDay());
				if (acceptor != null) {
					upgrade(acceptor);
				}
			}
		} else {
			//combine(evidenceExtractor.getUniformFunction(day.getGame()));
		}
		
		return super.choose(actor, day);
	}
}
