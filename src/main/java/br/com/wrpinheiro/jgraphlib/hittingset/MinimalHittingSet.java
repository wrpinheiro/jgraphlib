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
package br.com.wrpinheiro.jgraphlib.hittingset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.wrpinheiro.jgraphlib.Arc;
import br.com.wrpinheiro.jgraphlib.Graph;
import br.com.wrpinheiro.jgraphlib.Vertex;
import br.com.wrpinheiro.jgraphlib.familyset.ArrayFamilySet;
import br.com.wrpinheiro.jgraphlib.familyset.FamilySet;
import br.com.wrpinheiro.jgraphlib.familyset.SetMaintainer;



/**
 * Reiter's minimal hitting set implementation.
 *
 * @author wrp 02/02/2008
 */
public class MinimalHittingSet<T> {
	/**
	 * Find all the minimal hitting sets for the fs's family of sets.
	 * 
	 * @param fs
	 *            a family of sets.
	 * @return a familySet of MHS for fs.
	 */
	public FamilySet<T> solve(FamilySet<T> fs) {
		Vertex<T> n;
		SetMaintainer<T> S;
		SetMaintainer<T> Hs;

		Graph<T> g = new Graph<T>();

		Vertex<T> root = new Vertex<T>(g);
		SetMaintainer<T> rootLabel = fs
				.findEmptyIntersection(new SetMaintainer<T>());
		root.setLabel(rootLabel);

		Queue<Vertex<T>> q = new LinkedList<Vertex<T>>();
		q.offer(root);

		// iterates each vertex ordering by level.
		while (!q.isEmpty()) {
			n = q.poll();
			S = n.getLabel();

			// TODO falta verifica no prune os arcos que foram cortados nao
			// devem continuar sendo processados. Remove-los do queue arcs.

			// for each vertex (one at time) creates out arcs according to its
			// label.
			Queue<Arc<T>> arcs = new LinkedList<Arc<T>>(createOutArcs(g, n, S));

			// for each arc chooses a vertex to link as target for the arc.
			while (!arcs.isEmpty()) {
				Arc<T> s_arco = arcs.poll();

				Hs = n.getLabelsOnArcsFromRoot().clone();
				Hs.add(s_arco.getLabel());

				// steps P1, P2 and P3
				boolean found = findReusableVertex(g, Hs, q, s_arco);

				if (!found) { // step P4
					Vertex<T> m = new Vertex<T>(g);
					s_arco.setTarget(m);
					m.setLabelsOnArcsFromRoot(Hs);

					SetMaintainer<T> SLinha = fs.findEmptyIntersection(Hs);

					if (SLinha.size() == 0) {
						this.markNodeWithAt(m);
					} else {
						m.setLabel(SLinha);
						HashSet<Vertex<T>> vertexRemoved = new HashSet<Vertex<T>>();
						HashSet<Arc<T>> arcsRemoved = new HashSet<Arc<T>>();
						if (!pruneGraph(g, q, m, vertexRemoved, arcsRemoved))
							q.offer(m);
						else {
							q.removeAll(vertexRemoved);
							g.removeVertices(vertexRemoved);

							arcs.removeAll(arcsRemoved);
							g.removeArcs(arcsRemoved);
						}
					}
				}
			}
		}

		Vertex<T> v;
		FamilySet<T> result = new ArrayFamilySet<T>();

		for (int i = 0; i < g.getNumVertex(); i++) {
			v = g.getVertex(i);
			// if (v.getLabel().equals(AT))
			if (this.checkMarkedWithAt(v))
				result.add(v.getLabelsOnArcsFromRoot());
		}

		return result;
	}

	/**
	 * Prunes the graph. If a vertex could not be reused and a new label from
	 * the family is scrictly contained in some other V vertex's label, then V's
	 * label can be set to m's label and prune all out arcs of V...
	 * 
	 * @param g
	 *            the graph.
	 * @param q
	 *            the queue of vertices. If a vertex is pruned it's necessary to
	 *            remove this vertex from the queue.
	 * @param m
	 *            the vertex just created.
	 */
	private boolean pruneGraph(Graph<T> g, Queue<Vertex<T>> q, Vertex<T> m,
			HashSet<Vertex<T>> vertexRemoved, HashSet<Arc<T>> arcsRemoved) {
		// setp P4'

		boolean found = false;

		for (int i = 0; !found && i < g.getNumVertex(); i++) {
			Vertex<T> nDuasLinhas = g.getVertex(i);
			if (!nDuasLinhas.equals(m) && !this.checkMarkedWithAt(nDuasLinhas)) {
				SetMaintainer<T> S1 = nDuasLinhas.getLabel();

				if (S1.strictlyContains(m.getLabel())) {
					nDuasLinhas.setLabel(m.getLabel().clone());
					S1.removeAll(nDuasLinhas.getLabel());
					removeOutArcs(nDuasLinhas, S1, vertexRemoved, arcsRemoved);
					found = true;
				}
			}
		}
		return found;
	}

	private boolean findReusableVertex(Graph<T> g, SetMaintainer<T> Hs,
			Queue<Vertex<T>> q, Arc<T> s_arco) {
		boolean found = false;
		Vertex<T> nLinha;
		for (int i = 0; i < g.getNumVertex() && !found; i++) {
			nLinha = g.getVertex(i);
			if (nLinha.getLabelsOnArcsFromRoot().equals(Hs)) { // step P1
				s_arco.setTarget(nLinha);
				found = true;
			} else if (this.checkMarkedWithAt(nLinha) // step P2
					&& Hs.containsAll(nLinha.getLabelsOnArcsFromRoot())) {
				s_arco.setTarget(null);
				found = true;
			} else if (!this.checkMarkedWithAt(nLinha)
					&& nLinha.getLabel().isEmptyIntersection(Hs)) { // step P3
				Vertex<T> newV = new Vertex<T>(g);
				newV.setLabel(nLinha.getLabel().clone());
				newV.setLabelsOnArcsFromRoot(Hs);
				s_arco.setTarget(newV);
				q.offer(newV);
				found = true;
			}
		}
		return found;
	}

	/**
	 * Creates a list of out arcs from the vertex n with the information given
	 * in the set arcsLabels.
	 * 
	 * @param g
	 *            the graph where the arcs will be created.
	 * @param n
	 *            the source vertex of the arcs.
	 * @param arcsLabels
	 *            the set with the labels of the arcs.
	 * @return returns the list of created arcs (its just a facility).
	 */
	private List<Arc<T>> createOutArcs(Graph<T> g, Vertex<T> n,
			SetMaintainer<T> arcsLabels) {
		List<Arc<T>> arcs = new ArrayList<Arc<T>>();
		for (Iterator<T> it = arcsLabels.iterator(); it.hasNext();) {
			T s = it.next();
			Arc<T> s_arco = new Arc<T>(g);
			s_arco.setSource(n);
			s_arco.setLabel(s);
			arcs.add(s_arco);
		}
		return arcs;
	}

	/**
	 * @param vertexRemoved
	 * @param v
	 * @param removeAll
	 */
	private void removeOutArcs(Vertex<T> v, SetMaintainer<T> labels,
			HashSet<Vertex<T>> vertexRemoved, HashSet<Arc<T>> arcsRemoved) {
		List<Arc<T>> outArcs = v.getOutArcs();
		HashSet<Arc<T>> arcsToBeRemoved = new HashSet<Arc<T>>();
		Arc<T> arc;

		for (Iterator<Arc<T>> it = outArcs.iterator(); it.hasNext();) {
			arc = it.next();

			if (labels.contains(arc.getLabel())) {
				arcsToBeRemoved.add(arc);
				v.removeOutArc(arc);
				// arcsRemoved.add(a);
			}
		}

		removeArcsAndOrphanVertex(arcsToBeRemoved, vertexRemoved, arcsRemoved);
	}

	/**
	 * @param arcsToBeRemoved
	 * @param vertexRemoved
	 * @param arcsRemoved
	 */
	private void removeArcsAndOrphanVertex(HashSet<Arc<T>> arcsToBeRemoved,
			HashSet<Vertex<T>> vertexRemoved, HashSet<Arc<T>> arcsRemoved) {

		Vertex<T> v;
		Arc<T> arc;
		for (Iterator<Arc<T>> it = arcsToBeRemoved.iterator(); it.hasNext();) {
			arc = it.next();
			v = arc.getTarget();
			arcsRemoved.add(arc);
			// target is null if we are prunning the just created node.
			if (v != null) {
				v.removeInArc(arc);

				if (v.getInArcs().size() == 0) {
					vertexRemoved.add(v);
					removeArcsAndOrphanVertex(new HashSet<Arc<T>>(v
							.getOutArcs()), vertexRemoved, arcsRemoved);
				}
			}
		}
	}

	/**
	 * Check if a node (a vertex) is marked with an @ (AT).
	 * 
	 * @param v
	 *            the node.
	 * @return true if the node is marked with an At or false, otherwise.
	 */
	private boolean checkMarkedWithAt(Vertex<?> v) {
		return v.getProperty("at") == Boolean.TRUE;
	}

	/**
	 * Mark the node with an At.
	 * 
	 * @param v
	 *            the node to be marked.
	 */
	private void markNodeWithAt(Vertex<?> v) {
		v.setProperty("at", Boolean.TRUE);
	}
}
