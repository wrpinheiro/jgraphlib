/*
 * Copyright 2013 Wellington Ricardo Pinheiro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.wrpinheiro.jgraphlib.hittingset.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.wrpinheiro.jgraphlib.hittingset.ArrayFamilySet;
import br.com.wrpinheiro.jgraphlib.hittingset.FamilySet;
import br.com.wrpinheiro.jgraphlib.hittingset.MinimalHittingSet;
import br.com.wrpinheiro.jgraphlib.set.SetMaintainer;


/**
 * @author wrp 01/02/2008
 */
public class MinimalHittingSetTest {
	@SuppressWarnings("unchecked")
	private <T> FamilySet<T> getFamilySet(SetMaintainer<?>... sets) {
		return new ArrayFamilySet<T>((SetMaintainer<T>[]) sets);
	}

	private <T> void testMHSSolution(FamilySet<T> fs, FamilySet<T> fsSolution) {
		MinimalHittingSet<T> mhs = new MinimalHittingSet<T>();
		FamilySet<T> mhsSolution = mhs.solve(fs);

		assertEquals(fsSolution.size(), mhsSolution.size());
		for (int i = 0; i < mhsSolution.size(); i++) {
			assertTrue(fsSolution.contains(mhsSolution.getSet(i)));
		}
	}

	// @Test
	public void testMinimalHittingSet() {
		MinimalHittingSet<Integer> mhs = new MinimalHittingSet<Integer>();

		assertNotNull(mhs);
	}

	@Test
	public void testSolve0() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// F: {1, 2, 3}, {2, 4}, {1, 3}
		// MHS(F): {1, 2}, {2, 3}, {3, 4}, {1, 4}
		family = getFamilySet(new SetMaintainer<Integer>(1, 2, 3),
				new SetMaintainer<Integer>(2, 4), new SetMaintainer<Integer>(1,
						3));
		fsSolution = getFamilySet(new SetMaintainer<Integer>(1, 2),
				new SetMaintainer<Integer>(2, 3), new SetMaintainer<Integer>(3,
						4), new SetMaintainer<Integer>(1, 4));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve1() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// F: {1, 2, 3, 4}, {2, 4}, {1, 3}
		// MHS(F): {2, 1}, {2, 3}, {4, 1}, {4, 3}
		family = getFamilySet(new SetMaintainer<Integer>(1, 2, 3, 4),
				new SetMaintainer<Integer>(2, 4), new SetMaintainer<Integer>(1,
						3));
		fsSolution = getFamilySet(new SetMaintainer<Integer>(2, 1),
				new SetMaintainer<Integer>(2, 3), new SetMaintainer<Integer>(4,
						1), new SetMaintainer<Integer>(4, 3));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve2() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// F: {1, 2, 3}, {2, 4}, {2}, {1, 3}
		// MHS(F): {2, 1}, {2, 3}
		family = getFamilySet(new SetMaintainer<Integer>(1, 2, 3),
				new SetMaintainer<Integer>(2, 4),
				new SetMaintainer<Integer>(2), new SetMaintainer<Integer>(1, 3));
		fsSolution = getFamilySet(new SetMaintainer<Integer>(2, 1),
				new SetMaintainer<Integer>(2, 3));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve3() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// F: {1}, {2}, {3}, {4}
		// MHS(F): {2, 1}, {2, 3}
		family = getFamilySet(new SetMaintainer<Integer>(1),
				new SetMaintainer<Integer>(2), new SetMaintainer<Integer>(3),
				new SetMaintainer<Integer>(4));
		fsSolution = getFamilySet(new SetMaintainer<Integer>(1, 2, 3, 4));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve4() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// F: {1, 2}, {3, 4}, {5, 6}, {7, 8}
		// MHS(F): {2, 1}, {2, 3}
		family = getFamilySet(new SetMaintainer<Integer>(1, 2),
				new SetMaintainer<Integer>(3, 4), new SetMaintainer<Integer>(5,
						6), new SetMaintainer<Integer>(7, 8),
				new SetMaintainer<Integer>(1, 3), new SetMaintainer<Integer>(5,
						8));

		fsSolution = getFamilySet(new SetMaintainer<Integer>(1, 3, 5, 7),
				new SetMaintainer<Integer>(1, 3, 5, 8),
				new SetMaintainer<Integer>(1, 3, 6, 8),
				new SetMaintainer<Integer>(1, 4, 5, 7),
				new SetMaintainer<Integer>(1, 4, 5, 8),
				new SetMaintainer<Integer>(1, 4, 6, 8),
				new SetMaintainer<Integer>(2, 3, 5, 7),
				new SetMaintainer<Integer>(2, 3, 5, 8),
				new SetMaintainer<Integer>(2, 3, 6, 8));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve5() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// F: {3}, {5}, {7}, {9}
		// MHS(F): {3, 5, 7, 9}
		family = getFamilySet(new SetMaintainer<Integer>(3),
				new SetMaintainer<Integer>(5), new SetMaintainer<Integer>(7),
				new SetMaintainer<Integer>(9));

		fsSolution = getFamilySet(new SetMaintainer<Integer>(3, 5, 7, 9));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve6() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// F: {3, 5, 7, 9}, {5, 7, 9, 11}, {3, 7, 9, 11}, {3, 5, 9, 11},
		// {3, 5, 7, 11}
		// MHS(F): {3, 5}, {3, 7}, {3, 9}, {3, 11}, {5, 7}, {5, 9}, {5, 11},
		// {7, 9}, {7, 11}, {9, 11}
		family = getFamilySet(new SetMaintainer<Integer>(3, 5),
				new SetMaintainer<Integer>(3, 7), new SetMaintainer<Integer>(3,
						9), new SetMaintainer<Integer>(3, 11),
				new SetMaintainer<Integer>(5, 7), new SetMaintainer<Integer>(5,
						9), new SetMaintainer<Integer>(5, 11),
				new SetMaintainer<Integer>(7, 9), new SetMaintainer<Integer>(7,
						11), new SetMaintainer<Integer>(9, 11));

		fsSolution = getFamilySet(new SetMaintainer<Integer>(3, 5, 7, 9),
				new SetMaintainer<Integer>(5, 7, 9, 11),
				new SetMaintainer<Integer>(3, 7, 9, 11),
				new SetMaintainer<Integer>(3, 5, 9, 11),
				new SetMaintainer<Integer>(3, 5, 7, 11));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve7() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// F: {3, 5}, {3, 7}, {3, 9}, {3, 11}, {5, 7}, {5, 9}, {5, 11},
		// {7, 9}, {7, 11}, {9, 11}
		// MHS(F): {3, 5, 7, 9}, {3, 5, 7, 11}, {3, 5, 9, 11}, {3, 7, 9,
		// 11}, {5, 7, 9, 11}
		family = getFamilySet(new SetMaintainer<Integer>(3, 5),
				new SetMaintainer<Integer>(3, 7), new SetMaintainer<Integer>(3,
						9), new SetMaintainer<Integer>(3, 11),
				new SetMaintainer<Integer>(5, 7), new SetMaintainer<Integer>(5,
						9), new SetMaintainer<Integer>(5, 11),
				new SetMaintainer<Integer>(7, 9), new SetMaintainer<Integer>(7,
						11), new SetMaintainer<Integer>(9, 11));

		fsSolution = getFamilySet(new SetMaintainer<Integer>(3, 5, 7, 9),
				new SetMaintainer<Integer>(3, 5, 7, 11),
				new SetMaintainer<Integer>(3, 5, 9, 11),
				new SetMaintainer<Integer>(3, 7, 9, 11),
				new SetMaintainer<Integer>(5, 7, 9, 11));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve8() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// F: {3, 5, 7, 9}, {3, 5, 7, 11}, {3, 5, 9, 11}, {3, 7, 9, 11},
		// {5, 7, 9, 11}
		// MHS(F): {3, 5}, {3, 7}, {3, 9}, {3, 11}, {5, 7}, {5, 9}, {5, 11},
		// {7, 9}, {7, 11}, {9, 11}
		family = getFamilySet(new SetMaintainer<Integer>(3, 5, 7, 9),
				new SetMaintainer<Integer>(3, 5, 7, 11),
				new SetMaintainer<Integer>(3, 5, 9, 11),
				new SetMaintainer<Integer>(3, 7, 9, 11),
				new SetMaintainer<Integer>(5, 7, 9, 11));

		fsSolution = getFamilySet(new SetMaintainer<Integer>(3, 5),
				new SetMaintainer<Integer>(3, 7), new SetMaintainer<Integer>(3,
						9), new SetMaintainer<Integer>(3, 11),
				new SetMaintainer<Integer>(5, 7), new SetMaintainer<Integer>(5,
						9), new SetMaintainer<Integer>(5, 11),
				new SetMaintainer<Integer>(7, 9), new SetMaintainer<Integer>(7,
						11), new SetMaintainer<Integer>(9, 11));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve9() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// F: {3, 5, 7, 9}, {5, 7, 9, 11}, {3, 7, 9, 11}, {3, 5, 9, 11},
		// {3, 5, 7, 11}
		// MHS(F): {3, 5}, {3, 7}, {3, 9}, {3, 11}, {5, 7}, {5, 9}, {5, 11},
		// {7, 9}, {7, 11}, {9, 11}
		family = getFamilySet(new SetMaintainer<Integer>(3, 5, 7, 9),
				new SetMaintainer<Integer>(5, 7, 9, 11),
				new SetMaintainer<Integer>(3, 7, 9, 11),
				new SetMaintainer<Integer>(3, 5, 9, 11),
				new SetMaintainer<Integer>(3, 5, 7, 11),
				new SetMaintainer<Integer>(9, 11));

		fsSolution = getFamilySet(new SetMaintainer<Integer>(3, 9),
				new SetMaintainer<Integer>(3, 11), new SetMaintainer<Integer>(
						5, 9), new SetMaintainer<Integer>(5, 11),
				new SetMaintainer<Integer>(7, 9), new SetMaintainer<Integer>(7,
						11), new SetMaintainer<Integer>(9, 11));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve10() {
		// F: {A, B, C,}, {B, D}, {D, E}
		// MHS(F): {A, D}, {B, D}, {B, E}, {C, D}
		FamilySet<Character> cfamily = getFamilySet(
				new SetMaintainer<Character>('A', 'B', 'C'),
				new SetMaintainer<Character>('B', 'D'),
				new SetMaintainer<Character>('D', 'E'));

		FamilySet<Character> cfsSolution = getFamilySet(
				new SetMaintainer<Character>('A', 'D'),
				new SetMaintainer<Character>('B', 'D'),
				new SetMaintainer<Character>('B', 'E'),
				new SetMaintainer<Character>('C', 'D'));
		testMHSSolution(cfamily, cfsSolution);
	}

	@Test
	public void testSolve11() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		family = getFamilySet(new SetMaintainer<Integer>(2, 4),
				new SetMaintainer<Integer>(4, 1), new SetMaintainer<Integer>(2,
						3), new SetMaintainer<Integer>(3, 5));
		fsSolution = getFamilySet(new SetMaintainer<Integer>(2, 4, 5),
				new SetMaintainer<Integer>(2, 1, 3),
				new SetMaintainer<Integer>(2, 1, 5),
				new SetMaintainer<Integer>(4, 3));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve12() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		family = getFamilySet(new SetMaintainer<Integer>(2, 4),
				new SetMaintainer<Integer>(4, 1), new SetMaintainer<Integer>(2,
						3), new SetMaintainer<Integer>(3, 5),
				new SetMaintainer<Integer>(4));
		fsSolution = getFamilySet(new SetMaintainer<Integer>(2, 4, 5),
				new SetMaintainer<Integer>(4, 3));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve13() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// MHS({ {2,4}, {2,3} }) = { {2}, {3, 4} }
		family = getFamilySet(new SetMaintainer<Integer>(2, 4),
				new SetMaintainer<Integer>(2, 3));
		fsSolution = getFamilySet(new SetMaintainer<Integer>(2),
				new SetMaintainer<Integer>(4, 3));
		testMHSSolution(family, fsSolution);
	}

	@Test
	public void testSolve14() {
		FamilySet<Integer> family;
		FamilySet<Integer> fsSolution;

		// MHS({ {1,2}, {2,3}, {1,3}, {2,4}, {2} }) = { {1,2}, {2,3} }

		family = getFamilySet(new SetMaintainer<Integer>(1, 2),
				new SetMaintainer<Integer>(2, 3), new SetMaintainer<Integer>(1,
						3), new SetMaintainer<Integer>(2, 4),
				new SetMaintainer<Integer>(2));
		fsSolution = getFamilySet(new SetMaintainer<Integer>(1,2),
				new SetMaintainer<Integer>(2,3));
		testMHSSolution(family, fsSolution);
	}
	
	@Test
	public void testGreinerExampleForTheCorrectionOfReitersAlgorithm() {
		// F: {a, b}, {b, c}, {a, c}, {b, d}, {b}
		// MHS(F): {a, b}, {b, c}
		FamilySet<Character> cfamily = getFamilySet(
				new SetMaintainer<Character>('a', 'b'),
				new SetMaintainer<Character>('b', 'c'),
				new SetMaintainer<Character>('a', 'c'),
				new SetMaintainer<Character>('b', 'd'),
				new SetMaintainer<Character>('b'));

		FamilySet<Character> cfsSolution = getFamilySet(
				new SetMaintainer<Character>('a', 'b'),
				new SetMaintainer<Character>('b', 'c'));
		testMHSSolution(cfamily, cfsSolution);
	}
}
