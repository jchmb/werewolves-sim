package nl.jchmb.wolves.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.jchmb.wolves.ai.MassFunction;

public class MassIdentityTest {
	public static void main(String[] args) {
		MassFunction<X> f1, f2, f3;
		
		f1 = getUniformFunction();
		f2 = getBiasedFunction();
		f3 = f1.combine(f2);
		printBeliefs(f1);
		printBeliefs(f2);
		printBeliefs(f3);
	}
	
	private static void printBeliefs(MassFunction<X> f) {
		for (Set<X> set : getSets()) {
			System.out.println("Belief: " + f.getBelief(set));
		}
		System.out.println("----");
	}
	
	private static List<Set<X>> getSets() {
		List<Set<X>> sets = new ArrayList<Set<X>>();
		Set<X> x1, x2, x3;
		
		x1 = new HashSet<X>();
		x1.add(X.X1);
		x2 = new HashSet<X>();
		x2.add(X.X2);
		x3 = new HashSet<X>();
		x3.add(X.X3);
		sets.add(x1);
		sets.add(x2);
		sets.add(x3);
		
		Set<X> x12, x23, x13;
		x12 = new HashSet<X>();
		x12.add(X.X1);
		x12.add(X.X2);
		x23 = new HashSet<X>();
		x23.add(X.X2);
		x23.add(X.X3);
		x13 = new HashSet<X>();
		x13.add(X.X1);
		x13.add(X.X3);
		sets.add(x12);
		sets.add(x23);
		sets.add(x13);
		
		
		
		
		return sets;
	}
	
	private static MassFunction<X> getUniformFunction() {
		MassFunction<X> f = new MassFunction<X>();
		Set<X> x12, x23, x13;
		x12 = new HashSet<X>();
		x12.add(X.X1);
		x12.add(X.X2);
		x23 = new HashSet<X>();
		x23.add(X.X2);
		x23.add(X.X3);
		x13 = new HashSet<X>();
		x13.add(X.X1);
		x13.add(X.X3);
		double mass = 1.0d/6.0d;
		
		
		f.add(X.X1, mass);
		f.add(X.X2, mass);
		f.add(X.X3, mass);
		f.add(x12, mass);
		f.add(x23, mass);
		f.add(x13, mass);
		
		return f;
	}
	
	private static MassFunction<X> getBiasedFunction() {
		MassFunction<X> f = new MassFunction<X>();
		Set<X> x12, x23, x13;
		x12 = new HashSet<X>();
		x12.add(X.X1);
		x12.add(X.X2);
		x23 = new HashSet<X>();
		x23.add(X.X2);
		x23.add(X.X3);
		x13 = new HashSet<X>();
		x13.add(X.X1);
		x13.add(X.X3);
		double mass = 1.0d/6.0d;
		double bias = 0.1d;
		
		
		f.add(X.X1, mass);
		f.add(X.X2, mass);
		f.add(X.X3, mass);
		f.add(x12, mass - bias);
		f.add(x23, mass + bias);
		f.add(x13, mass);
		
		return f;
	}
	
	private enum X {
		X1, X2, X3;
	}
}
