package nl.jchmb.wolves.tests;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IntersectionBenchmark {
	private static long fac(int n) {
		long sum = 1;
		for (int i = 1; i <= n; i++) {
			sum *= (long) i;
		}
		return sum;
	}
	
	private static int numberOfCombinations(int n, int k) {
		return (int) (fac(n)/(fac(k)*(fac(n - k))));
	}
	
	private static BitSet getRandomBitSet(int size) {
		BitSet bits = new BitSet(size);
		return getRandomBitSet(size, bits);
	}
	
	private static BitSet getRandomBitSet(int size, BitSet bits) {	
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			bits.set(i, random.nextBoolean());
		}
		return bits;
	}
	
	private static LongArray getRandomLongArray(int size) {	
		Random random = new Random();
		long[] values = new long[(size / 64) + 1];
		for (int i = 0; i < values.length; i++) {
			values[i] = random.nextLong();
		}
		return new IntersectionBenchmark().new LongArray(values);
	}
	
	private static Set<Integer> getRandomSet(int size) {
		Random random = new Random();
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < size; i++) {
			if (random.nextBoolean()) {
				set.add(i);
			}
		}
		return set;
	}
	
	private static void benchmarkLongArray(int n, int k, int t) {
		LongArray b1, b2;
		int size = numberOfCombinations(n, k);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < t; i++) {
			b1 = getRandomLongArray(size);
			b2 = getRandomLongArray(size);
			b1.intersect(b2);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("LongArray: " + (endTime - startTime)  + " ms");
	}
	
	private static void benchmarkBitSet(int n, int k, int t) {
		BitSet b1, b2;
		int size = numberOfCombinations(n, k);
		long startTime = System.currentTimeMillis();
		b1 = getRandomBitSet(size);
		b2 = getRandomBitSet(size);
		for (int i = 0; i < t; i++) {
			b1 = getRandomBitSet(size, b1);
			b2 = getRandomBitSet(size, b2);
			b1.and(b2);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("BitSet: " + (endTime - startTime)  + " ms");
	}
	
	private static void benchmarkSetIntersection(int n, int k, int t) {
		int size = numberOfCombinations(n, k);
		Set<Integer> s1, s2, s3;
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < t; i++) {
			s1 = getRandomSet(size);
			s2 = getRandomSet(size);
			
			/* Copying and then performing intersection is necessary in the algorithms, so... */
			s3 = new HashSet<Integer>(s1);
			s3.retainAll(s2);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("SetIntersection: " + (endTime - startTime)  + " ms");
	}
	
	public static void main(String[] args) {
		int n = 20, k = 5, t = 5000;
		benchmarkBitSet(n, k, t);
		benchmarkSetIntersection(n, k, t);
		benchmarkLongArray(n, k, t);
	}
	
	public class LongArray {
		private long[] values;
		
		public LongArray(long[] values) {
			this.values = values;
		}
		
		public LongArray(int size) {
			values = new long[(size / 64) + 1];
		}
		
		public void set(int i, long value) {
			this.values[i] = value;
		}
		
		public LongArray intersect(LongArray array) {
			long[] values = new long[this.values.length];
			for (int i = 0; i < values.length; i++) {
				values[i] = this.values[i] & array.values[i];
			}
			return new LongArray(values);
		}
	}
}
