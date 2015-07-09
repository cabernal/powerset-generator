package me.cabernal.tests;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import me.cabernal.PowerSet;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * Unit tests for power set application
 */
public class PowerSetTest {

	@Test
	public void testPowerSet() {
		Set<String> testSet = new HashSet<String>();
		String[] setMembers = { "1", "2", "3" };
		testSet.addAll(Arrays.asList(setMembers));

		String[] powerSetMembers = {
				"[]",
				"[1, 2, 3]" ,
				"[1]", "[2]", "[3]",
				"[1, 2]", "[1, 3]", "[2, 3]"};
		Set<String> expectedPowerSet = new HashSet<String>();
		expectedPowerSet.addAll(Arrays.asList(powerSetMembers));

		PowerSet<String> powerSet = new PowerSet<String>(testSet);
		assertPowerSetEquality(powerSet, expectedPowerSet);
	}

	@Test
	public void testEmptyPowerSet() {
		Set<String> testSet = new HashSet<String>();

		String[] powerSetMembers = { "[]" };
		Set<String> expectedPowerSet = new HashSet<String>();
		expectedPowerSet.addAll(Arrays.asList(powerSetMembers));

		PowerSet<String> powerSet = new PowerSet<String>(testSet);
		assertPowerSetEquality(powerSet, expectedPowerSet);
	}

	@Test
	public void testSmallPowerSet() {
		Set<String> testSet = new HashSet<String>();
		testSet.add("1");

		String[] powerSetMembers = { "[]", "[1]" };
		Set<String> expectedPowerSet = new HashSet<String>();
		expectedPowerSet.addAll(Arrays.asList(powerSetMembers));

		PowerSet<String> powerSet = new PowerSet<String>(testSet);
		assertPowerSetEquality(powerSet, expectedPowerSet);
	}

	/**
	 * Helper method:
	 * 
	 * Assert equality between power set <code>ps</code> and the String set
	 * representation of power set <code>ps2</code>.
	 *
	 * @param ps1
	 *            power set
	 * @param ps2
	 *            string set representation of power set
	 */
	public void assertPowerSetEquality(PowerSet<String> ps1, Set<String> ps2) {
		for (Set<String> subset : ps1) {
			// assert that current subset is a member of the expected power set
			String subsetStr = subset.toString();
			assertTrue(ps2.contains(subsetStr));
			ps2.remove(subsetStr);
		}

		// make sure we have seen all members of the power set
		assertTrue(ps2.isEmpty());
	}

	// TODO: Test remaining methods
}
