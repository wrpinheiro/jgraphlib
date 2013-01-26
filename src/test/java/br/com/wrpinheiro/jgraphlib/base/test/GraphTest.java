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
package br.com.wrpinheiro.jgraphlib.base.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.com.wrpinheiro.jgraphlib.Arc;
import br.com.wrpinheiro.jgraphlib.Graph;
import br.com.wrpinheiro.jgraphlib.Vertex;


/**
 * @author wrp 26/01/2008
 * 
 */
public class GraphTest {

	/**
	 * Test method for {@link org.jgraphlib.base.MatrixGraph#Graph(int)}.
	 */
	@Test
	public void testGraph() {
		Graph<Integer> g = new Graph<Integer>();
		assertNotNull(g);
	}

	@SuppressWarnings("unchecked")
	public void testGetVertex() {
		Graph<Integer> g = new Graph<Integer>();

		Vertex<Integer>[] vertices = new Vertex[5];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex<Integer>(g);
		}

		for (int i = 0; i < vertices.length; i++) {
			assertEquals(vertices[i], g.getVertex(i));
		}

		try {
			g.getVertex(-1);
			fail("Graph returned vertex indexed by -1");
		} catch (RuntimeException ex) {
			// does not need implementation.
		}
	}

	/**
	 * Test method for {@link org.jgraphlib.base.MatrixGraph#addEdge(int, int)}.
	 */
	@Test
	public void testAddEdge() {
		Graph<Integer> g = new Graph<Integer>();
		Arc<Integer> arc;
		Vertex<Integer> v0 = new Vertex<Integer>(g);
		Vertex<Integer> v1 = new Vertex<Integer>(g);

		arc = new Arc<Integer>(g, null, v0);
		assertEquals(arc, g.getArc(0));
		assertNull(g.getArc(0).getSource());
		assertEquals(v0, g.getArc(0).getTarget());

		arc = new Arc<Integer>(g, v0, null);
		assertEquals(arc, g.getArc(1));
		assertEquals(v0, g.getArc(1).getSource());
		assertNull(g.getArc(1).getTarget());

		arc = new Arc<Integer>(g, v0, v0);
		assertEquals(arc, g.getArc(2));
		assertEquals(v0, g.getArc(2).getSource());
		assertEquals(v0, g.getArc(2).getTarget());

		arc = new Arc<Integer>(g, v0, v1);
		assertEquals(arc, g.getArc(3));
		assertEquals(v0, g.getArc(3).getSource());
		assertEquals(v1, g.getArc(3).getTarget());

		arc = new Arc<Integer>(g, v0, v1);
		assertEquals(arc, g.getArc(4));
		assertEquals(v0, g.getArc(4).getSource());
		assertEquals(v1, g.getArc(4).getTarget());

		arc = new Arc<Integer>(g, null, null);
		assertEquals(arc, g.getArc(5));
		assertNull(g.getArc(5).getSource());
		assertNull(g.getArc(5).getTarget());
	}

	@Test
	public void testAdjacency() {
		Graph<Integer> g = new Graph<Integer>();

		Vertex<Integer> v0 = new Vertex<Integer>(g);
		Vertex<Integer> v1 = new Vertex<Integer>(g);
		Vertex<Integer> v2 = new Vertex<Integer>(g);

		Arc<Integer> a0 = new Arc<Integer>(g, v0, v1);
		g.addArc(a0);

		Arc<Integer> a1 = new Arc<Integer>(g, v2, v2);
		g.addArc(a1);

		Arc<Integer> a2 = new Arc<Integer>(g, v1, v2);
		g.addArc(a2);

		Arc<Integer> a3 = new Arc<Integer>(g, v2, v1);
		g.addArc(a3);

		assertFalse(g.isAdjacent(v0, v0));
		assertTrue(g.isAdjacent(v0, v1));
		assertFalse(g.isAdjacent(v0, v2));
		assertFalse(g.isAdjacent(v1, v0));
		assertFalse(g.isAdjacent(v1, v1));
		assertTrue(g.isAdjacent(v1, v2));
		assertFalse(g.isAdjacent(v2, v0));
		assertTrue(g.isAdjacent(v2, v1));
		assertTrue(g.isAdjacent(v2, v2));
	}

	@Test
	public void testArcEquals() {
		Graph<Integer> g = new Graph<Integer>();
		Arc<Integer> arc = new Arc<Integer>(g);

		assertEquals(arc, arc);
	}
}