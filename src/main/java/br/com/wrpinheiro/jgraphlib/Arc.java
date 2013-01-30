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

/**
 * Arc definition.
 * 
 * @author wrp 02/02/2008
 */
public class Arc<T> {
	/**
	 * Every arc has an unique ID.
	 */
	private int id;

	/**
	 * The label for this arc.
	 */
	private T label;

	/**
	 * The source vertex.
	 */
	private Vertex<T> source;

	/**
	 * The target vertex.
	 */
	private Vertex<T> target;

	/**
	 * This arc belongs to graph.
	 */
	private Graph<T> graph;

	/**
	 * Creates an arc and assign its source and target vertices.
	 * 
	 * @param g
	 *            the graph of this arc.
	 * @param source
	 *            the source vertex.
	 * @param target
	 *            the target vertex.
	 */
	public Arc(Graph<T> g, Vertex<T> source, Vertex<T> target) {
		this(g);
		this.setSource(source);
		this.setTarget(target);
	}

	/**
	 * Creates an arc but don't set its source and target vertices. Is a kind of
	 * temporary arc where we don't have sufficient information about its links.
	 * 
	 * @param g
	 *            the graph of this arc.
	 */
	public Arc(Graph<T> g) {
		this.graph = g;
		this.graph.addArc(this);
	}

	/**
	 * Set the id of this arc. Note that this setter has a default access
	 * modifier, so it can't be set externally.
	 * 
	 * @param id
	 *            the id to set
	 */
	void setId(int id) {
		this.id = id;
	}

	/**
	 * Set the label of this arc.
	 * 
	 * @param label
	 *            new label for this arc.
	 */
	public void setLabel(T label) {
		this.label = label;
	}

	/**
	 * Set the source vertex of this arc.
	 * 
	 * @param source
	 *            the source vertex of this arc.
	 */
	public void setSource(Vertex<T> source) {
		if (source != null && !source.getGraph().equals(this.graph)) {
			throw new InvalidVertexException();
		}
		
		if (this.source != null) {
			this.source.internalRemoveOutArc(this);
		}

		this.source = source;
		if (this.source != null)
			this.source.addOutArc(this);
	}

	/**
	 * Set the target vertex of this arc.
	 * 
	 * @param target
	 *            the target vertex of this arc.
	 */
	public void setTarget(Vertex<T> target) {
		if (target != null && !target.getGraph().equals(this.graph)) {
			throw new InvalidVertexException();
		}
		
		if (this.target != null) {
			this.target.internalRemoveInArc(this);
		}

		this.target = target;
		if (this.target != null)
			this.target.addInArc(this);
	}

	/**
	 * Return the label of this arc.
	 * 
	 * @return the label of this arc.
	 */
	public T getLabel() {
		return this.label;
	}

	/**
	 * Return the source vertex of this arc.
	 * 
	 * @return the source vertex of this arc.
	 */
	public Vertex<T> getSource() {
		return this.source;
	}

	/**
	 * Return the target vertex of this arc.
	 * 
	 * @return the target vertex of this arc.
	 */
	public Vertex<T> getTarget() {
		return this.target;
	}

	/**
	 * Verifies if this arc links vertex source and target vertices.
	 * 
	 * @param source
	 *            the source vertex.
	 * @param target
	 *            the target vertex.
	 * @return TRUE if this this arc is an output arc of source and is as input
	 *         arc of target.
	 */
	public boolean isAdjacent(Vertex<T> source, Vertex<T> target) {
		return this.source.equals(source) && this.target.equals(target);
	}

	/**
	 * Return the id of this arc.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
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

		final Arc<?> other = (Arc<?>) obj;
		if (!graph.equals(other.graph))
			return false;
		return this.id == other.id;
	}

	/**
	 * (non-Javadoc)
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
	 * A facility method to set both source and target vertices this arc connects.
	 * @param v1 the source vertex
	 * @param v2 the target vertex.
	 */
	public void setEndings(Vertex<T> v1, Vertex<T> v2) {
		this.setSource(v1);
		this.setTarget(v2);
	}
}
