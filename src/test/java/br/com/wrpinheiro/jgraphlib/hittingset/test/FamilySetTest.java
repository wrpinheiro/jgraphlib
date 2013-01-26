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

import org.junit.Assert;
import org.junit.Test;

import br.com.wrpinheiro.jgraphlib.hittingset.ArrayFamilySet;
import br.com.wrpinheiro.jgraphlib.hittingset.FamilySet;
import br.com.wrpinheiro.jgraphlib.set.SetMaintainer;


/**
 * @author wrp 01/02/2008
 * 
 */
public class FamilySetTest {
	@SuppressWarnings("unchecked")
	@Test
	public void testFamilySetCreate() {
		FamilySet<Integer> fs = new ArrayFamilySet<Integer>(new SetMaintainer[0]);

		Assert.assertNotNull(fs);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindEmptyIntersection() {
		SetMaintainer<Integer> set1 = new SetMaintainer<Integer>(new Integer[] { 1, 2, 3 });
		SetMaintainer<Integer> set2 = new SetMaintainer<Integer>(new Integer[] { 2, 4 });
		SetMaintainer<Integer> set3 = new SetMaintainer<Integer>(new Integer[] { 1, 3 });
		SetMaintainer<?>[] set = { set1, set2, set3 };
		FamilySet<Integer> fs = new ArrayFamilySet<Integer>((SetMaintainer<Integer>[])set);

		SetMaintainer<Integer> s1 = fs.findEmptyIntersection(new SetMaintainer<Integer>(new Integer[] { 1 }));
		assertEquals(set2, s1);

		s1 = fs.findEmptyIntersection(new SetMaintainer<Integer>(new Integer[] { 2 }));
		assertEquals(set3, s1);

		s1 = fs.findEmptyIntersection(new SetMaintainer<Integer>(new Integer[] { 2, 3 }));
		assertEquals(new SetMaintainer<Integer>(), s1);

		s1 = fs.findEmptyIntersection(new SetMaintainer<Integer>(new Integer[] {}));
		assertEquals(set1, s1);

		s1 = fs.findEmptyIntersection(null);
		assertEquals(set1, s1);
		SetMaintainer<Integer>[] s = new SetMaintainer[3];
		s[0] = new SetMaintainer<Integer>(new Integer[] { 1, 2, 3 });
		s[1] = new SetMaintainer<Integer>(new Integer[] { 2, 4 });
		s[2] = new SetMaintainer<Integer>(new Integer[] { 1, 3 });

		fs = new ArrayFamilySet<Integer>(s);
		SetMaintainer<Integer> s0 = fs.findEmptyIntersection(new SetMaintainer<Integer>(new Integer[] {1, 4}));
		assertEquals(new SetMaintainer<Integer>(), s0);		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetFamilySet() {
		@SuppressWarnings("rawtypes")
    SetMaintainer[] set = { new SetMaintainer<Integer>(new Integer[] { 1, 2, 3 }),
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
