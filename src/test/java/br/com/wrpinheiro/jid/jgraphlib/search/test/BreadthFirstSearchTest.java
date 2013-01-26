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
package br.com.wrpinheiro.jid.jgraphlib.search.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.wrpinheiro.jid.jgraphlib.Arc;
import br.com.wrpinheiro.jid.jgraphlib.Graph;
import br.com.wrpinheiro.jid.jgraphlib.Vertex;
import br.com.wrpinheiro.jid.jgraphlib.search.BreadthFirstSearch;



/**
 * @author wrp
 * 27/01/2008
 */
public class BreadthFirstSearchTest {

	@Test
	public void testSearch() {
		Graph<Integer> g = new Graph<Integer>();

		//
		// 0 <------ 1
		// |
		// v
		// 2 ------> 4 ------> 3
		//
		
		Vertex<Integer> v0 = new Vertex<Integer>(g);
		Vertex<Integer> v1 = new Vertex<Integer>(g);
		Vertex<Integer> v2 = new Vertex<Integer>(g);
		Vertex<Integer> v3 = new Vertex<Integer>(g);
		Vertex<Integer> v4 = new Vertex<Integer>(g);
		
		Arc<Integer> a0 = new Arc<Integer>(g, v1, v0);
		Arc<Integer> a1 = new Arc<Integer>(g, v0, v2);
		Arc<Integer> a2 = new Arc<Integer>(g, v2, v4);
		Arc<Integer> a3 = new Arc<Integer>(g, v4, v3);
		
		g.addArc(a0);
		g.addArc(a1);
		g.addArc(a2);
		g.addArc(a3);
		
		BreadthFirstSearch<Integer> brFs = new BreadthFirstSearch<Integer>(g);
		Vertex<Integer> v[] = brFs.search(v1, v3);

		assertEquals(5, v.length);
			
		assertEquals(v1, v[0]);
		assertEquals(v0, v[1]);
		assertEquals(v2, v[2]);
		assertEquals(v4, v[3]);
		assertEquals(v3, v[4]);

		//
		// 0 <------ 1
		// |
		// v
		// 2 ------> 4 ------> 3
		// |                   ^
		// |                   |
		// +-------------------+
		//

		Arc<Integer> a4 = new Arc<Integer>(g, v2, v3);
		g.addArc(a4);
		v = brFs.search(v1, v3);
		
		assertEquals(4, v.length);
		
		assertEquals(v1, v[0]);
		assertEquals(v0, v[1]);
		assertEquals(v2, v[2]);
		assertEquals(v3, v[3]);
		
		//
		// 0 <------ 1---------+
		// |                   |
		// v                   v
		// 2 ------> 4 ------> 3
		// |                   ^
		// |                   |
		// +-------------------+
		//

		Arc<Integer> a5 = new Arc<Integer>(g, v1, v3);
		g.addArc(a5);
		v = brFs.search(v1, v3);
		
		assertEquals(2, v.length);
		
		assertEquals(v1, v[0]);
		assertEquals(v3, v[1]);

		v = brFs.search(v1, v1);
		assertEquals(1, v.length);
		assertEquals(v1, v[0]);
	}
}
