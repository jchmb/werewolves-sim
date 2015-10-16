package nl.jchmb.wolves.core.policy;

import nl.jchmb.wolves.ai.EvidenceExtractor;
import nl.jchmb.wolves.ai.MassFunction;
import nl.jchmb.wolves.ai.World;
import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;

public class AnalyticInnocentPolicy extends BeliefBasedPolicy {

	private EvidenceExtractor evidenceExtractor;
	
	public AnalyticInnocentPolicy(MassFunction<World> f) {
		super(f);
		evidenceExtractor = new EvidenceExtractor();
	}

	@Override
	public Player choose(Player actor, Day day) {
		/* On the second day, start analyzing evidence and updating one's beliefs. */
		if (day.getNumber() > 1) {
			combine(evidenceExtractor.extract(day.getPreviousDay()));
		}
		return super.choose(actor, day);
	}
}