package br.com.wrpinheiro.jgraphlib.base.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;

import br.com.wrpinheiro.jgraphlib.Arc;
import br.com.wrpinheiro.jgraphlib.Graph;
import br.com.wrpinheiro.jgraphlib.InvalidVertexException;
import br.com.wrpinheiro.jgraphlib.Vertex;

public class ArcTest {
	private Graph<Integer> g1;
	private Graph<Integer> g2;
	private Vertex<Integer> v1g1;
	private Vertex<Integer> v2g1;
	private Vertex<Integer> v3g1;
	private Vertex<Integer> v1g2;
	private Arc<Integer> a1g1;

	
	@Before
	public void setUp() {
		g1 = new Graph<Integer>();
		g2 = new Graph<Integer>();

		v1g1 = new Vertex<Integer>(g1);
		v2g1 = new Vertex<Integer>(g1);
		v3g1 = new Vertex<Integer>(g1);

		v1g2 = new Vertex<Integer>(g2);
		
		a1g1 = new Arc<Integer>(g1);
	}
	
	@Test(expected = InvalidVertexException.class)
	public void testInvalidArcConnectionVerticesInDifferentGraphs() {
		a1g1.setSource(v1g1);
		a1g1.setTarget(v1g2);
	}

	@Test
	public void testArcIdentity() {
		assertEquals(a1g1, a1g1);
	}
	
	@Test
	public void testChangeVertexOutArc() {
		a1g1.setSource(v1g1);
		assertEquals(0, v1g1.getOutArcs().indexOf(a1g1));
		assertEquals(v1g1, a1g1.getSource());
		
		a1g1.setSource(v2g1);
		assertEquals(-1, v1g1.getOutArcs().indexOf(a1g1));
		assertEquals(0, v2g1.getOutArcs().indexOf(a1g1));
		assertEquals(v2g1, a1g1.getSource());
	}

	@Test
	public void testChangeVertexInArc() {
		a1g1.setTarget(v1g1);
		assertEquals(0, v1g1.getInArcs().indexOf(a1g1));
		assertEquals(v1g1, a1g1.getTarget());
		
		a1g1.setTarget(v2g1);
		assertEquals(-1, v1g1.getInArcs().indexOf(a1g1));
		assertEquals(0, v2g1.getInArcs().indexOf(a1g1));
		assertEquals(v2g1, a1g1.getTarget());
	}

	@Test
	public void testChangeArcEndpoint() {		
		// v1 -> v2
		a1g1.setEndings(v1g1, v2g1);
		assertEquals(a1g1, g1.getArc(0));
		assertEquals(v1g1, a1g1.getSource());
		assertEquals(v2g1, a1g1.getTarget());
		assertEquals(0, v1g1.getOutArcs().indexOf(a1g1));
		assertEquals(0, v2g1.getInArcs().indexOf(a1g1));
		
		// v3 -> v2
		a1g1.setSource(v3g1);
		assertNotSame(v1g1, a1g1.getSource());
		assertEquals(v3g1, a1g1.getSource());
		assertEquals(v2g1, a1g1.getTarget());
		assertEquals(-1, v1g1.getOutArcs().indexOf(a1g1));
		assertEquals(0, v3g1.getOutArcs().indexOf(a1g1));
		assertEquals(0, v2g1.getInArcs().indexOf(a1g1));
		
		// v3 -> v1
		a1g1.setTarget(v1g1);
		assertNotSame(v2g1, a1g1.getTarget());
		assertEquals(v3g1, a1g1.getSource());
		assertEquals(v1g1, a1g1.getTarget());
		assertEquals(0, v1g1.getInArcs().indexOf(a1g1));
		assertEquals(0, v3g1.getOutArcs().indexOf(a1g1));
		assertEquals(-1, v2g1.getInArcs().indexOf(a1g1));
	}
}
