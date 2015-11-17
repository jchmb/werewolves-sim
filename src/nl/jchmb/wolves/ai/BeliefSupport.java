package nl.jchmb.wolves.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.jchmb.wolves.ai.acceptor.NullAcceptor;
import nl.jchmb.wolves.ai.acceptor.WorldAcceptor;

public class BeliefSupport {
	private List<Support> supports;
	private double alpha = 1.2d;
	
	public BeliefSupport() {
		supports = new ArrayList<Support>();
	}
	
	public BeliefSupport(Collection<World> worlds) {
		this();
		double weight = 1.0d / ((double) worlds.size());		
		for (World world : worlds) {
			supports.add(new Support(world, weight));
		}
	}
	
	public void upgrade(WorldAcceptor acceptor) {
		for (Support support : supports) {
			if (acceptor.accept(support.world)) {
				support.weight *= alpha;
			} else {
				support.weight *= (1.0d / alpha);
			}
		}
	}
	
	public double getBelief(WorldAcceptor acceptor) {
		double belief = 0.0d;
		for (Support support : supports) {
			if (acceptor.accept(support.world)) {
				belief += support.weight;
			}
		}
		return belief;
	}
	
	public void normalize() {
		double totalBelief = getBelief(new NullAcceptor());
		double normalization = 1.0d / totalBelief;
		for (Support support : supports) {
			support.weight *= normalization;
		}
	}
	
	private class Support {
		private World world;
		private double weight;
		
		public Support(World world, double weight) {
			this.world = world;
			this.weight = weight;
		}
	}
}
