package nl.jchmb.wolves.core.policy;

import java.util.Random;

import nl.jchmb.wolves.core.Day;
import nl.jchmb.wolves.core.Player;

public class MixedPolicy implements Policy {

	private double randomProbability;
	private Policy purePolicy;
	private Policy randomPolicy;
	private Random random;
	
	public MixedPolicy(double randomProbability, Policy purePolicy) {
		this(randomProbability, purePolicy, new RandomPolicy());
	}
	
	public MixedPolicy(double randomProbability, Policy purePolicy, Policy otherPolicy) {
		this.randomProbability = randomProbability;
		this.purePolicy = purePolicy;
		randomPolicy = otherPolicy;
		random = new Random();
	}
	
	@Override
	public Player choose(Player actor, Day day) {
		if (random.nextDouble() <= randomProbability) {
			return randomPolicy.choose(actor, day);
		} else {
			return purePolicy.choose(actor, day);
		}
	}

}
