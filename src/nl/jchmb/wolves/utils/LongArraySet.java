package nl.jchmb.wolves.utils;

public class LongArraySet {
	private int size;
	private long[] values;
	
	public LongArraySet(int size) {
		this.size = size;
		values = new long[(size / 64) + 1];
	}
	
	public LongArraySet intersect(LongArraySet set) {
		LongArraySet intersection = new LongArraySet(size);
		for (int i = 0; i < values.length; i++) {
			intersection.values[i] = values[i] & set.values[i];
		}
		return intersection;
	}
}
