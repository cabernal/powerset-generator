package me.cabernal;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Powerset functionality.
 *
 * Iterator was used in order to avoid loading entire superset in memory,
 * and instead only load subset by subset.
 */
public class PowerSet<T> implements Iterable<Set<T>>, Iterator<Set<T>> {
	// Array representation of set
	private T[] array = null;
	// Bit mask representation of current subset combination
	private BitSet mask = null;

	@SuppressWarnings("unchecked")
	public PowerSet(Set<T> set) {
		this.array = (T[]) set.toArray();
		// extra bit to signal end of bit set
		this.mask = new BitSet(array.length + 1);
	}

	public boolean hasNext() {
		// if extra bit is set, then we have exhausted all subset combinations
		return !mask.get(array.length);
	}

	public Set<T> next() {
		Set<T> subset = new HashSet<T>();
		// get all subsets in subset combination represented by bit mask
		for (int i = 0; i < array.length; i++) {
			if (mask.get(i)) {
				subset.add(array[i]);
			}
		}

		incrementMaskValue();

		return subset;
	}

	public Iterator<Set<T>> iterator() {
		return this;
	}

	/**
	 * Increment mask value using binary addition by 1
	 */
	private void incrementMaskValue() {
		for (int i = 0; i < mask.size(); i++) {
			// bit is unset, do not carry
			if (!mask.get(i)) {
				mask.set(i);
				break;
			}
			// bit is set, carry the 1
			mask.clear(i);

		}
	}

}