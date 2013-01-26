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
package br.com.wrpinheiro.jgraphlib.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.wrpinheiro.jgraphlib.Graph;
import br.com.wrpinheiro.jgraphlib.Vertex;



/**
 * A BreadthFirst search implementation.
 * 
 * @author wrp 27/01/2008
 */
public class BreadthFirstSearch<T> {
  /**
   * Possible visitation status for vertices.
   * @author wrp
   */
  private enum VertexVisitationStatus {
    NotVisited, /* the vertex hasn't been visited */ 
    Visited,    /* the vertex has already been visited */
    Expanded;   /* the vertex has already been visited and expanded */
  }

	/**
	 * The graph where the search is being executed. 
	 */
	private Graph<T> g;

	/**
	 * @param g
	 */
	public BreadthFirstSearch(Graph<T> g) {
		this.g = g;
	}

	/**
	 * Searches a path from u to v.
	 * 
	 * @param u the source vertex.
	 * @param v the target vertex.
	 * @return a path o vertex from u to v.
	 */
	@SuppressWarnings("unchecked")
  public Vertex<T>[] search(Vertex<T> u, Vertex<T> v) {
		HashMap<Vertex<T>, NodeInfo> nodes = new HashMap<Vertex<T>, NodeInfo>();
		for (int i = 0; i < g.getNumVertex(); i++) {
			NodeInfo ni = new NodeInfo(g.getVertex(i));
			nodes.put(ni.v, ni);
		}

		Queue<Vertex<T>> q = new LinkedList<Vertex<T>>();
		q.offer(u);
		while (!q.isEmpty()) {
			Vertex<T> current = q.poll();
			if (current.equals(v)) {
				return this.extractPath(v, nodes);
			}
			for (int i = 0; i < this.g.getNumVertex(); i++) {
				NodeInfo temp;
				if (g.isAdjacent(current, g.getVertex(i))
						&& (temp = nodes.get(g.getVertex(i))).searchState == VertexVisitationStatus.NotVisited) {
					temp.parent = current;
					temp.searchState = VertexVisitationStatus.Expanded;
					q.offer(g.getVertex(i));
				}
			}
			nodes.get(current).searchState = VertexVisitationStatus.Visited;
		}

		return new Vertex[0];
	}

	/**
	 * Extract a path.
	 * @param v
	 * @param nodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Vertex<T>[] extractPath(Vertex<T> v,
			HashMap<Vertex<T>, NodeInfo> nodes) {
		List<Vertex<T>> path = new ArrayList<Vertex<T>>();
		Vertex<T> parent;

		path.add(0, v);
		parent = nodes.get(v).parent;
		while (parent != null) {
			path.add(0, parent);
			parent = nodes.get(parent).parent;
		}

		Vertex<T>[] l = new Vertex[path.size()];
		for (int i = 0; i < l.length; i++) {
			l[i] = path.get(i);
		}
		return l;
	}

	/**
	 * An information class.
	 * @author wrp
	 * 15/03/2008
	 *
	 */
	private class NodeInfo {
		private Vertex<T> parent;
		private Vertex<T> v;
		private VertexVisitationStatus searchState;

		public NodeInfo(Vertex<T> v) {
			this.v = v;
			this.searchState = VertexVisitationStatus.NotVisited;
			this.parent = null;
		}
	}
}
