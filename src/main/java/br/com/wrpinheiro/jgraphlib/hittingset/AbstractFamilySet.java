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
package br.com.wrpinheiro.jgraphlib.hittingset;

import java.util.Set;

import br.com.wrpinheiro.jgraphlib.set.SetMaintainer;



/**
 * An abstract implementation of the FamilySet interface.
 * 
 * @author wrp
 */
public abstract class AbstractFamilySet<T> implements FamilySet<T> {

	/**
	 * (non-Javadoc).
	 * 
	 * @see br.com.wrpinheiro.jgraphlib.hittingset.FamilySet#add(br.com.wrpinheiro.jgraphlib.set.SetMaintainer)
	 */
	public void add(SetMaintainer<T> set) {
		throw new UnsupportedOperationException(
				"the operation add(SetMaintainer<T> set) is not available for this class");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see br.com.wrpinheiro.jgraphlib.hittingset.FamilySet#contains(br.com.wrpinheiro.jgraphlib.set.SetMaintainer)
	 */
	public boolean contains(SetMaintainer<?> set) {
		throw new UnsupportedOperationException(
				"the operation contains(SetMaintainer<T> set) is not available for this class");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see br.com.wrpinheiro.jgraphlib.hittingset.FamilySet#findEmptyIntersection(br.com.wrpinheiro.jgraphlib.set.SetMaintainer)
	 */
	public SetMaintainer<T> findEmptyIntersection(SetMaintainer<T> otherSet) {
		throw new UnsupportedOperationException(
				"the operation findEmptyIntersection(SetMaintainer<T> otherSet) is not available for this class");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see br.com.wrpinheiro.jgraphlib.hittingset.FamilySet#getFamilySet()
	 */
	public SetMaintainer<T>[] getFamilySet() {
		throw new UnsupportedOperationException(
				"the operation getFamilySet() is not available for this class");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see br.com.wrpinheiro.jgraphlib.hittingset.FamilySet#getSet(int)
	 */
	public SetMaintainer<T> getSet(int i) {
		throw new UnsupportedOperationException(
				"the operation getSet(int i) is not available for this class");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see br.com.wrpinheiro.jgraphlib.hittingset.FamilySet#size()
	 */
	public int size() {
		return -1;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see br.com.wrpinheiro.jgraphlib.hittingset.FamilySet#getFamilySetAsSet()
	 */
	public Set<T[]> getFamilySetAsSet() {
		throw new UnsupportedOperationException(
				"the operation getFamilySetAsSet() is not available for this class");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see br.com.wrpinheiro.jgraphlib.hittingset.FamilySet#getEqual(br.com.wrpinheiro.jgraphlib.set.SetMaintainer)
	 */
	@Override
	public SetMaintainer<T> getEqual(final SetMaintainer<T> otherSet) {
		throw new UnsupportedOperationException(
				"the operation getFamilySetAsSet() is not available for this class");
	}

	/**
	 * (non-Javadoc).
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		SetMaintainer<T>[] sets = getFamilySet();

		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (SetMaintainer<T> s : sets) {
			sb.append(s).append(' ');
		}

		sb.append('}');

		return sb.toString();
	}

	/**
	 * (non-Javadoc).
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractFamilySet<?>))
			return false;

		AbstractFamilySet<?> other = (AbstractFamilySet<?>) obj;
		if (other.size() != this.size())
			return false;

		SetMaintainer<?>[] s1 = other.getFamilySet();

		for (SetMaintainer<?> s : s1) {
			if (!this.contains(s))
				return false;
		}

		return true;
	}
}
