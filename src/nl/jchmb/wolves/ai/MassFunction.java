package nl.jchmb.wolves.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MassFunction<W> {
	private List<Mass> distribution;
	
	public MassFunction() {
		distribution = new ArrayList<Mass>();
	}
	
	public void add(Mass m) {
		distribution.add(m);
	}
	
	public double getMass(W world) {
		Set<W> worlds = new HashSet<W>();
		worlds.add(world);
		return getMass(worlds);
	}
	
	public double getMass(Set<W> worlds) {
		for (Mass m : distribution) {
			if (m.worlds.equals(worlds)) {
				return m.mass;
			}
		}
		return 0.0d;
	}
	
	public void add(Set<W> worlds, double mass) {
		distribution.add(new Mass(worlds, mass));
	}
	
	public void add(W world, double mass) {
		Set<W> worlds = getSingleton(world);
		add(worlds, mass);
	}
	
	public MassFunction<W> combine(MassFunction<W> f) {
		MassFunction<W> newFunction = new MassFunction<W>();
		for (Mass m1 : distribution) {
			for (Mass m2 : f.distribution) {
				Mass m3 = m1.intersect(m2, f);
				if (m3 != null) {
					newFunction.add(m3);
				}
			}
		}
		return newFunction;
	}
	
	public double getBelief(W w) {
		return getBelief(getSingleton(w));
	}
	
	public double getBelief(Set<W> worlds) {
		double belief = 0.0d;
		for (Mass m : distribution) {
			if (worlds.containsAll(m.worlds)) {
				belief += m.mass;
			}
		}
		return belief;
	}
	
	public double getPlausibility(W w) {
		return 0.0d;
	}
	
	private Set<W> getSingleton(W w) {
		Set<W> worlds = new HashSet<W>();
		worlds.add(w);
		return worlds;
	}
	
	private class Mass {
		private Set<W> worlds;
		private double mass;
		
		public Mass(Set<W> worlds, double mass) {
			this.worlds = worlds;
			this.mass = mass;
		}
		
		public double calculateWeight(MassFunction<W> f) {
			double k = 0.0d;
			for (Mass m1 : distribution) {
				for (Mass m2 : f.distribution) {
					Set<W> intersection = m1.intersect(m2.worlds);
					if (intersection.isEmpty()) {
						k += m1.mass * m2.mass;
					}
				}
			}
			return 1.0d/(1.0d-k);
		}
		
		public Set<W> intersect(Set<W> worlds) {
			Set<W> intersection = new HashSet<W>(this.worlds);
			intersection.retainAll(worlds);
			return intersection;
		}
		
		public Mass intersect(Mass m, MassFunction<W> f) {
			Set<W> intersection = new HashSet<W>(worlds);
			intersection.retainAll(m.worlds);
			if (intersection.isEmpty()) {
				return null;
			}
			double newMass = calculateWeight(f) * mass * m.mass;
			if (newMass == 0.0d) {
				return null;
			}
			return new Mass(intersection, newMass);
		}
	}
}
