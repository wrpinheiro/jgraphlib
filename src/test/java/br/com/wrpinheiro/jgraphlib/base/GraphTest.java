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
package br.com.wrpinheiro.jgraphlib.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.wrpinheiro.jgraphlib.Arc;
import br.com.wrpinheiro.jgraphlib.Graph;
import br.com.wrpinheiro.jgraphlib.InvalidVertexException;
import br.com.wrpinheiro.jgraphlib.Vertex;

/**
 * @author wrp
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

	@Test
	public void testGetVertex() {
	  final int MAX_VERTICES = 5;
		Graph<Integer> g = new Graph<Integer>();

		List<Vertex<Integer>> vertices = new ArrayList<Vertex<Integer>>();
		for (int i = 0; i < MAX_VERTICES; i++) {
			vertices.add(new Vertex<Integer>(g));
		}

		for (int i = 0; i < MAX_VERTICES; i++) {
			assertEquals(vertices.get(i), g.getVertex(i));
		}
	}
	
	@Test(expected=InvalidVertexException.class)
	public void testAccessToVertexWithNegativeValue() {
		Graph<Integer> g = new Graph<Integer>();
		g.getVertex(-1);
	}
	
	@Test(expected=InvalidVertexException.class)
	public void testAccessToInvalidVertex() {
		Graph<Integer> g = new Graph<Integer>();
		g.getVertex(0);
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
		Graph<Integer> g1 = new Graph<Integer>();
		Graph<Integer> g2 = new Graph<Integer>();

		Vertex<Integer> v0 = new Vertex<Integer>(g1);
		Vertex<Integer> v1 = new Vertex<Integer>(g1);
		Vertex<Integer> v2 = new Vertex<Integer>(g1);

		Arc<Integer> a0 = new Arc<Integer>(g1, v0, v1);
		g1.addArc(a0);

		Arc<Integer> a1 = new Arc<Integer>(g1, v2, v2);
		g1.addArc(a1);

		Arc<Integer> a2 = new Arc<Integer>(g1, v1, v2);
		g1.addArc(a2);

		Arc<Integer> a3 = new Arc<Integer>(g1, v2, v1);
		g1.addArc(a3);

		assertFalse(g1.isAdjacent(v0, v0));
		assertTrue(g1.isAdjacent(v0, v1));
		assertFalse(g1.isAdjacent(v0, v2));
		assertFalse(g1.isAdjacent(v1, v0));
		assertFalse(g1.isAdjacent(v1, v1));
		assertTrue(g1.isAdjacent(v1, v2));
		assertFalse(g1.isAdjacent(v2, v0));
		assertTrue(g1.isAdjacent(v2, v1));
		assertTrue(g1.isAdjacent(v2, v2));
		
		Vertex<Integer> vg2 = new Vertex<Integer>(g2);
		assertFalse(g1.isAdjacent(v0, vg2));
		assertFalse(g2.isAdjacent(v0, vg2));
		assertFalse(g2.isAdjacent(vg2, vg2));
	}
	
	@Test
	public void testRootVertex() {
		Graph<Integer> g = new Graph<Integer>();
		Vertex<Integer> v = new Vertex<Integer>(g);
	
		g.setRoot(v);

		Assert.assertEquals(true, v.isRoot());
		Assert.assertEquals(v, g.getRoot());
	}
	
	@Test(expected=InvalidVertexException.class)
	public void testFailToSetRootVertexFromAnotherGraph() {
		Graph<Integer> g1 = new Graph<Integer>();
		Graph<Integer> g2 = new Graph<Integer>();
		
		new Vertex<Integer>(g1);
		Vertex<Integer> v2 = new Vertex<Integer>(g2);
		
		g1.setRoot(v2);
	}
	
	@Test
	public void testSettingANewRoot() {
		Graph<Integer> g1 = new Graph<Integer>();
		
		Vertex<Integer> v1 = new Vertex<Integer>(g1);
		Vertex<Integer> v2 = new Vertex<Integer>(g1);
		
		g1.setRoot(v1);
		
		Assert.assertEquals(true, v1.isRoot());
		
		g1.setRoot(v2);
		Assert.assertEquals(false, v1.isRoot());
		Assert.assertEquals(true, v2.isRoot());
		Assert.assertEquals(v2, g1.getRoot());
		
	}
}
