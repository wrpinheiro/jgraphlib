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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.wrpinheiro.jgraphlib.familyset.SetMaintainer;

/**
 * Vertex definition.
 * 
 * @author wrp 26/01/2008
 */
public class Vertex<T> {
	/**
	 * The graph maitaining this vertex.
	 */
	private Graph<T> graph;

	/**
	 * A list of input arcs.
	 */
	private List<Arc<T>> inArcs = new ArrayList<Arc<T>>();

	/**
	 * A list of output arcs.
	 */
	private List<Arc<T>> outArcs = new ArrayList<Arc<T>>();

	/**
	 * This is a set of label on the arcs from a root vertex until this vertex.
	 * This information is not processed by this vertex, its is just a facility
	 * to someone fill in.
	 */
	private SetMaintainer<T> labelsOnArcsFromRoot = new SetMaintainer<T>();

	/**
	 * The label of this vertex.
	 */
	private SetMaintainer<T> label;

	/**
	 * The id of this vertex. Every vertex must have an unique id for graph, set
	 * when this vertex is added to a graph.
	 */
	private int id;

	/**
	 * A map of properties for this node.
	 */
	private Map<String, Object> property = new HashMap<String, Object>();

	/**
	 * Creates vertex and add it to the graph G.
	 * 
	 * @param g
	 *            the graph of this vertex.
	 */
	public Vertex(Graph<T> g) {
		this.graph = g;
		g.addVertex(this);
	}

	/**
	 * Return the id of this vertex.
	 * 
	 * @return the id of this vertex.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Return the label of this vertex.
	 * 
	 * @return the label of this vertex.
	 */
	public SetMaintainer<T> getLabel() {
		return label;
	}

	/**
	 * Set the label of this vertex.
	 * 
	 * @param label
	 *            the label to set
	 */
	public void setLabel(SetMaintainer<T> label) {
		this.label = label;
	}

	/**
	 * Add an input arc to this vertex.
	 * 
	 * @param arc
	 *            the input arc.
	 */
	public void addInArc(Arc<T> arc) {
		this.inArcs.add(arc);
	}

	/**
	 * Add an output arc to this vertex.
	 * 
	 * @param arc
	 *            the output arc.
	 */
	public void addOutArc(Arc<T> arc) {
		this.outArcs.add(arc);
	}

	/**
	 * Get the list of input arcs of this vertex.
	 * 
	 * @return the list of input arcs of this vertex.
	 */
	public List<Arc<T>> getInArcs() {
		return new ArrayList<Arc<T>>(this.inArcs);
	}

	/**
	 * Get the list of output arcs of this vertex.
	 * 
	 * @return the list of output arcs of this vertex.
	 */
	public List<Arc<T>> getOutArcs() {
		return new ArrayList<Arc<T>>(this.outArcs);
	}

	/**
	 * Remove an input arc of this vertex.
	 * 
	 * @param arc
	 *            the input arc to be removed.
	 */
	public void removeInArc(Arc<T> arc) {
		this.inArcs.remove(arc);
		arc.setTarget(null);
	}
	
	/**
	 * Remove an input arc from this vertex but do not call
	 * arc.setTarget(null). Used only for internal purposes.
	 * 
	 * @param arc
	 *            the output arc to be removed
	 */
	void internalRemoveInArc(Arc<T> arc) {
		this.inArcs.remove(arc);
	}

	/**
	 * Remove an output arc of this vertex.
	 * 
	 * @param arc
	 *            the output arc to be removed.
	 */
	public void removeOutArc(Arc<T> arc) {
		this.outArcs.remove(arc);
		arc.setSource(null);
	}

	/**
	 * Remove an output arc from this vertex but do not call
	 * arc.setSource(null). Used only for internal purposes.
	 * 
	 * @param arc
	 *            the output arc to be removed
	 */
	void internalRemoveOutArc(Arc<T> arc) {
		this.outArcs.remove(arc);
	}

	/**
	 * Return the graph of this vertex.
	 * 
	 * @return the graph of this vertex.
	 */
	public Graph<T> getGraph() {
		return this.graph;
	}

	/**
	 * Test if a vertex is adjacent of this vertex. An adjacent vertex is a
	 * vertex linked by an output arc of this vertex.
	 * 
	 * @param other
	 *            the vertex tested for adjacency.
	 * @return TRUE if other is adjacent to this vertex or FALSE, otherwise.
	 */
	public boolean isAdjacent(Vertex<T> other) {
		for (Iterator<Arc<T>> it = this.outArcs.iterator(); it.hasNext();) {
			if (it.next().getTarget().equals(other))
				return true;
		}
		return false;
	}

	/**
	 * Set the id of this vertex. A vertex is identified by a sequential number
	 * given by the current number of vertex from a graph plus one.
	 * 
	 * @param id
	 *            the id of this vertex.
	 */
	void setId(int id) {
		this.id = id;
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
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		result = prime * result + id;
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		final Vertex<T> other = (Vertex<T>) obj;
		if (!graph.equals(other.graph))
			return false;
		return this.id == other.id;
	}

	/**
	 * Return a list of labels on the arcs from a root vertex until this vertex.
	 * 
	 * @return a list of labels on the arcs.
	 */
	public SetMaintainer<T> getLabelsOnArcsFromRoot() {
		return labelsOnArcsFromRoot;
	}

	/**
	 * Set the list of labels on the arcs from a root vertex until this vertex.
	 * 
	 * @return a list of labels on the arcs.
	 */
	public void setLabelsOnArcsFromRoot(SetMaintainer<T> H) {
		this.labelsOnArcsFromRoot = H;
	}

	/**
	 * (non-Javadoc).
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (this.label != null)
			return this.label.toString();

		return super.toString();
	}

	/**
	 * Set a property in this vertex.
	 * 
	 * @param key
	 *            the key for the property.
	 * @param value
	 *            the value forthe property.
	 */
	public void setProperty(String key, Object value) {
		this.property.put(key, value);
	}

	/**
	 * Retrieve a property previously set in this vertex.
	 * 
	 * @param key
	 *            the key for the property.
	 * @return the value set in this property or null if the property does not
	 *         exist.
	 */
	public Object getProperty(String key) {
		return this.property.get(key);
	}

	/**
	 * Check if this vertex is a root vertex from its graph.
	 * 
	 * @return true if this vertex is root or false otherwise.
	 */
	public boolean isRoot() {
		return this.graph.getRoot().equals(this);
	}
}
