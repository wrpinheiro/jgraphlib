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
package br.com.wrpinheiro.jid.jgraphlib.hittingset.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.wrpinheiro.jid.jgraphlib.set.SetMaintainer;


/**
 * @author wrp
 *
 * 08/01/2009
 *
 */
public class SetMaintainerTest {
	@Test
	public void testEquallity() {
		SetMaintainer<Integer> s1 = new SetMaintainer<Integer>(1, 2, 3);
		SetMaintainer<Integer> s2 = new SetMaintainer<Integer>(1, 2, 3);
		
		assertTrue(s1.equals(s2));
	}
	
	@Test
	public void testInequallity() {
		SetMaintainer<Integer> s1 = new SetMaintainer<Integer>(1, 2, 3);
		SetMaintainer<Integer> s2 = new SetMaintainer<Integer>(1, 3);
		
		assertFalse(s1.equals(s2));
	}

}
