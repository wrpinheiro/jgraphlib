/*
 * Copyright 2006-2013 Wellington Ricardo Pinheiro.
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

import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.com.wrpinheiro.jgraphlib.hittingset.ArrayFamilySet;
import br.com.wrpinheiro.jgraphlib.hittingset.FamilySet;
import br.com.wrpinheiro.jgraphlib.set.SetMaintainer;

/**
 * @author wrp 01/02/2008
 */
public class ArrayFamilySetTest {
	@Test
	public void testCreationWithoutParameter() {
		FamilySet<Integer> f = new ArrayFamilySet<Integer>();
		Assert.assertNotNull(f);

		Assert.assertEquals(0, f.size());
		Assert.assertNotNull(f.getFamilySet());
		Assert.assertNotNull(f.getFamilySetAsSet());
	}

	@Test
	public void testCreationWithAnEmptyArrayOfSets() {
		@SuppressWarnings("unchecked")
		FamilySet<Integer> f = new ArrayFamilySet<Integer>(new SetMaintainer[0]);

		Assert.assertNotNull(f);
		Assert.assertNotNull(f.getFamilySet());
		Assert.assertNotNull(f.getFamilySetAsSet());
	}

	@Test
	public void testCreationOfArrayWithNullItem() {
		
		// in this case, java compiler suposes the "null" value is one
		// of the elements in the set. This way, the resulting set will
		// have one value, the null value: A = {null}.
		@SuppressWarnings("unchecked")
		FamilySet<Integer> f = new ArrayFamilySet<Integer>(
				(SetMaintainer<Integer>) null);
		Assert.assertNotNull(f);

		Assert.assertEquals(1, f.size());
		Assert.assertNotNull(f.getFamilySet());
		
		Set<Integer[]> s = f.getFamilySetAsSet();
		
		Assert.assertNotNull(s);
		
		Assert.assertArrayEquals(null, s.iterator().next());
	}
	
	@Test
	public void testCreationWithNull() {
		
		// in this case, java compiler suposes the "null" value is one
		// of the elements in the set. This way, the resulting set will
		// have one value, the null value: A = {null}.
		FamilySet<Integer> f = new ArrayFamilySet<Integer>(
				(SetMaintainer<Integer>[]) null);
		Assert.assertNotNull(f);

		Assert.assertEquals(0, f.size());
		Assert.assertNotNull(f.getFamilySet());
		
		Set<Integer[]> s = f.getFamilySetAsSet();
		
		Assert.assertNotNull(s);
		Assert.assertEquals(0, s.size());
	}
	
	@Test
	public void testFamilyAsSet() {
		SetMaintainer<Integer> s0 = new SetMaintainer<Integer>(1, 2, 3);
		SetMaintainer<Integer> s1 = new SetMaintainer<Integer>((Integer)null);
		SetMaintainer<Integer> s2 = new SetMaintainer<Integer>(4, 5, 6);
		
		FamilySet<Integer> f = new ArrayFamilySet<Integer>(s0, s1, s2);
		SetMaintainer<Integer>[] sets = f.getFamilySet();
		
		Assert.assertEquals(3, sets.length);
		Assert.assertEquals(s0, sets[0]);
		Assert.assertEquals(s1, sets[1]);
		Assert.assertEquals(s2, sets[2]);
		
		Set<Integer[]> s = f.getFamilySetAsSet();
		Assert.assertEquals(3, s.size());
		Iterator<Integer[]> it = s.iterator();
		
		Assert.assertArrayEquals(s0.toArray(new Integer[0]), it.next());
		Assert.assertArrayEquals(s1.toArray(new Integer[0]), it.next());
		Assert.assertArrayEquals(s2.toArray(new Integer[0]), it.next());
	}
	
	@Test
	public void testEquals() {
		SetMaintainer<Integer> s0 = new SetMaintainer<Integer>(1, 2, 3);
		SetMaintainer<Integer> s1 = new SetMaintainer<Integer>((Integer)null);
		SetMaintainer<Integer> s2 = new SetMaintainer<Integer>(4, 5, 6);
		
		FamilySet<Integer> f1 = new ArrayFamilySet<Integer>(s0, s1, s2);
		FamilySet<Integer> f2 = new ArrayFamilySet<Integer>(s0, s1, s2);
		
		Assert.assertEquals(f1,  f1);
		Assert.assertEquals(f2,  f1);
		Assert.assertEquals(f1,  f2);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindEmptyIntersection() {
		SetMaintainer<Integer> set1 = new SetMaintainer<Integer>(new Integer[] {
				1, 2, 3 });
		SetMaintainer<Integer> set2 = new SetMaintainer<Integer>(new Integer[] {
				2, 4 });
		SetMaintainer<Integer> set3 = new SetMaintainer<Integer>(new Integer[] {
				1, 3 });
		SetMaintainer<?>[] set = { set1, set2, set3 };
		FamilySet<Integer> fs = new ArrayFamilySet<Integer>(
				(SetMaintainer<Integer>[]) set);

		SetMaintainer<Integer> s1 = fs
				.findEmptyIntersection(new SetMaintainer<Integer>(
						new Integer[] { 1 }));
		assertEquals(set2, s1);

		s1 = fs.findEmptyIntersection(new SetMaintainer<Integer>(
				new Integer[] { 2 }));
		assertEquals(set3, s1);

		s1 = fs.findEmptyIntersection(new SetMaintainer<Integer>(new Integer[] {
				2, 3 }));
		assertEquals(new SetMaintainer<Integer>(), s1);

		s1 = fs.findEmptyIntersection(new SetMaintainer<Integer>(
				new Integer[] {}));
		assertEquals(set1, s1);

		s1 = fs.findEmptyIntersection(null);
		assertEquals(set1, s1);
		SetMaintainer<Integer>[] s = new SetMaintainer[3];
		s[0] = new SetMaintainer<Integer>(new Integer[] { 1, 2, 3 });
		s[1] = new SetMaintainer<Integer>(new Integer[] { 2, 4 });
		s[2] = new SetMaintainer<Integer>(new Integer[] { 1, 3 });

		fs = new ArrayFamilySet<Integer>(s);
		SetMaintainer<Integer> s0 = fs
				.findEmptyIntersection(new SetMaintainer<Integer>(
						new Integer[] { 1, 4 }));
		assertEquals(new SetMaintainer<Integer>(), s0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetFamilySet() {
		@SuppressWarnings("rawtypes")
		SetMaintainer[] set = {
				new SetMaintainer<Integer>(new Integer[] { 1, 2, 3 }),
				new SetMaintainer<Integer>(new Integer[] { 2, 4 }),
				new SetMaintainer<Integer>(new Integer[] { 1, 3 }) };

		FamilySet<Integer> fs = new ArrayFamilySet<Integer>(set);
		SetMaintainer<Integer>[] newSet = fs.getFamilySet();

		for (int i = 0; i < set.length; i++) {
			assertEquals(set[i], newSet[i]);
		}
	}

	@Test
	public void testRemoveDuplicatedSets() {
		FamilySet<Integer> fs = new ArrayFamilySet<Integer>();

		SetMaintainer<Integer> s1 = new SetMaintainer<Integer>(1, 2, 3);
		SetMaintainer<Integer> s2 = new SetMaintainer<Integer>(1, 2, 3);
		SetMaintainer<Integer> s3 = new SetMaintainer<Integer>(1, 3);

		fs.add(s1);
		fs.add(s2);
		fs.add(s3);

		assertEquals(2, fs.size());

		SetMaintainer<Integer>[] newSet = fs.getFamilySet();

		assertEquals(s1, newSet[0]);
		assertEquals(s3, newSet[1]);
	}
}
