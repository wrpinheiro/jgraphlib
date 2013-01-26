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
package br.com.wrpinheiro.jgraphlib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A graph definition.
 * 
 * @author wrp 02/02/2008
 */
public class Graph<T> {
	/**
	 * An ID counter for the graph. Every graph must have an unique ID.
	 */
	private static int staticId = 1;

	/**
	 * List of the vertices of this graph.
	 */
	private List<Vertex<T>> vertices = new ArrayList<Vertex<T>>();

	/**
	 * List of the arcs of this graph.
	 */
	private List<Arc<T>> arcs = new ArrayList<Arc<T>>();

	/**
	 * ID of this graph.
	 */
	private int id;

	/**
	 * Create a new graph and set its unique id.
	 */
	public Graph() {
		this.id = staticId++;
	}

	/**
	 * Add a new vertex to this graph.
	 * 
	 * @param vertex
	 *            the vertex to be add.
	 */
	public void addVertex(Vertex<T> vertex) {
		this.vertices.add(vertex);
		vertex.setId(this.vertices.size() - 1);
	}

	/**
	 * Return the ith vertex.
	 * 
	 * @param i
	 *            the ith vertex index.
	 * @return the ith vertex.
	 */
	public Vertex<T> getVertex(final int i) {
		return this.vertices.get(i);
	}

	/**
	 * Add a new arc to this graph.
	 * 
	 * @param arc
	 *            the arc to be add.
	 */
	public void addArc(Arc<T> arc) {
		this.arcs.add(arc);
		arc.setId(this.arcs.size() - 1);
	}

	/**
	 * Return the ith arc.
	 * 
	 * @param i
	 *            the arc index.
	 * @return the ith arc of this graph.
	 */
	public Arc<T> getArc(int i) {
		return this.arcs.get(i);
	}

	/**
	 * Return the number of vertices of this graph.
	 * 
	 * @return the number of vertices of this graph.
	 */
	public int getNumVertex() {
		return this.vertices.size();
	}

	/**
	 * Check if from source is adjacent to target.
	 * 
	 * @param source
	 *            the source vertex.
	 * @param target
	 *            the target vertex.
	 * @return TRUE if target is adjacent to source.
	 */
	public boolean isAdjacent(Vertex<T> source, Vertex<T> target) {
		return source.isAdjacent(target);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Graph<?> other = (Graph<?>) obj;
		return this.id == other.id;
	}

	/**
	 * Remove a set of vertices.
	 * 
	 * @param vertices
	 *            the set of vertices to be removed.
	 */
	public void removeVertices(Set<Vertex<T>> vertices) {
		this.vertices.removeAll(vertices);
	}

	/**
	 * Remove a set of arcs.
	 * 
	 * @param arcsRemoved
	 *            the set of arcs to be removed.
	 */
	public void removeArcs(HashSet<Arc<T>> arcsRemoved) {
		this.arcs.removeAll(arcsRemoved);
	}
}
