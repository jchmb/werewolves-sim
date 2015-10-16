package nl.jchmb.wolves.tests;

import java.util.HashSet;
import java.util.Set;

import nl.jchmb.wolves.ai.MassFunction;

public class MassTest {
	public static void main(String[] args) {
		MassFunction<Coin> f = new MassFunction<Coin>();
		MassFunction<Coin> f2 = new MassFunction<Coin>();
		MassFunction<Coin> g;
		f.add(Coin.H, 0.5d);
		f.add(Coin.T, 0.5f);
		f2.add(Coin.H, 0.25d);
		f2.add(Coin.T, 0.75d);
		
		g = f2.combine(f2);
		System.out.println(g.getMass(Coin.H));
		System.out.println(g.getMass(Coin.T));
		
		System.out.println(g.getBelief(Coin.H));
		System.out.println(g.getBelief(Coin.T));
		
		Set<Coin> allWorlds = new HashSet<Coin>();
		allWorlds.add(Coin.H);
		allWorlds.add(Coin.T);
		System.out.println(g.getBelief(allWorlds));
	}
	
	private enum Coin {
		H, T;
	}
}
