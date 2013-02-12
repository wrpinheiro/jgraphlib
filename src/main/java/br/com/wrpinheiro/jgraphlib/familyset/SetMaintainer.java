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
package br.com.wrpinheiro.jgraphlib.familyset;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A set implementation with support for intersection and strict containment
 * operations.
 * 
 * @author wrp 08/02/2008
 */
public class SetMaintainer<T> extends HashSet<T> {
	/**
	 * Serial ID for serialization.
	 */
	private static final long serialVersionUID = 6209675331259759443L;

	/**
	 * Creates an instance of SetMaintainer and initializes it with the elements
	 * in the values.
	 * 
	 * @param value
	 *            a list of values. It can be a zero size.
	 */
	public SetMaintainer(T... values) {
		for (T value : values) {
			this.add(value);
		}
	}

	/**
	 * Creates an instance of SetMaintainer and initializes it with the elements
	 * in the values.
	 * 
	 * @param value
	 *            a list of values. It can be a zero size.
	 */
	public SetMaintainer(Set<T> values) {
		if (values == null)
			this.add(null);
		else {
			for (T value : values) {
				this.add(value);
			}
		}
	}

	/**
	 * Creates an empty SetMaintainer.
	 */
	public SetMaintainer() {
	}

	/**
	 * Check if this set strictly contains otherSet. In other words, it's the
	 * same as saying that this set contains every element in otherSet and this
	 * set is BIGGER than otherSet.
	 * 
	 * @param otherSet
	 *            the otherSet.
	 * @return TRUE if this set strictly contains otherSet or FALSE otherwise.
	 */
	public boolean strictlyContains(SetMaintainer<T> otherSet) {
		return this.size() > otherSet.size() && this.containsAll(otherSet);
	}

	/**
	 * Return the intersection set of this set with otherSet.
	 * 
	 * @param otherSet
	 *            the otherSet.
	 * @return the intersection set of this set with otherSet.
	 */
	public SetMaintainer<T> intersection(SetMaintainer<T> otherSet) {
		if (otherSet == null) {
			return new SetMaintainer<T>();
		}
		SetMaintainer<T> s1 = this.clone();

		s1.retainAll(otherSet);

		return s1;
	}

	/**
	 * Check it the intersection of this set with otherSet is empty.
	 * 
	 * @param otherSet
	 *            another set.
	 * @return TRUE if the intersection is empty or FALSE otherwise.
	 */
	public boolean isEmptyIntersection(SetMaintainer<T> otherSet) {
		return this.intersection(otherSet).size() == 0;
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
		result = prime * result + super.hashCode();
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

		final SetMaintainer<T> other = (SetMaintainer<T>) obj;
		if (this.size() != other.size())
			return false;

		for (Iterator<T> it = this.iterator(); it.hasNext();) {
			T o = it.next();
			if (!other.contains(o)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * (non-Javadoc).
	 * 
	 * @see java.util.HashSet#clone()
	 */
	@Override
	public SetMaintainer<T> clone() {
		SetMaintainer<T> newSet = new SetMaintainer<T>();
		newSet.addAll(this);
		return newSet;
	}
}
