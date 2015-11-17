package nl.jchmb.wolves.core.policy;

import nl.jchmb.wolves.ai.EvidenceExtractor;
import nl.jchmb.wolves.ai.acceptor.WorldAcceptor;
import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;

public class AnalyticInnocentCombinedPolicy extends BeliefBasedPolicy {

	private EvidenceExtractor evidenceExtractor;
	private static Player target = null;
	
	public AnalyticInnocentCombinedPolicy() {
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
			target = super.choose(actor, day);
			
		}
		/* On the second day, start analyzing evidence and updating one's beliefs. */
		if (day.getNumber() > 1) {
			if (lastDay < day.getNumber()) {
				loadMaxPlayers(day);
				lastDay = day.getNumber();
				target = super.choose(actor, day);
				WorldAcceptor acceptor = evidenceExtractor.extract(day.getPreviousDay());
				if (acceptor != null) {
					upgrade(acceptor);
				}
			}
		} else {
			//combine(evidenceExtractor.getUniformFunction(day.getGame()));
		}
		
		return target;
	}
}
