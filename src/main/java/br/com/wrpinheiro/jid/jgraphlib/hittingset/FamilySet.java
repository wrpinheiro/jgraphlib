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
package br.com.wrpinheiro.jid.jgraphlib.hittingset;

import java.util.Set;

import br.com.wrpinheiro.jid.jgraphlib.set.SetMaintainer;



/**
 * Maintains a family of sets.
 * 
 * @author wrp 01/02/2008
 */
public interface FamilySet<T> {
	/**
	 * Add a new set to this familySet.
	 * 
	 * @param set
	 *            a new set to be added.
	 */
	public abstract void add(SetMaintainer<T> set);

	/**
	 * Return the list of sets maintained by this FamilySet.
	 * 
	 * @return list of sets.
	 */
	public abstract SetMaintainer<T>[] getFamilySet();

	/**
	 * Return the list of sets maintained by this FamilySet.
	 * 
	 * @return list of sets.
	 */
	public abstract Set<T[]> getFamilySetAsSet();

	/**
	 * Returns a set whose intersection with otherSet is empty.
	 * 
	 * @param otherSet
	 *            a set.
	 * @return a set.
	 */
	public abstract SetMaintainer<T> findEmptyIntersection(
			SetMaintainer<T> otherSet);

	/**
	 * Check if set is maintained by this familySet.
	 * 
	 * @param set
	 *            a set.
	 * @return TRUE if set is maintained by this familySet or FALSE otherwise.
	 */
	public abstract boolean contains(SetMaintainer<?> set);

	/**
	 * Return the number of sets maintained by this familySet.
	 * 
	 * @return the number of sets maintained by this familySet. If the descend
	 *         FamilySet does not implement this operation, it MUST return -1.
	 */
	public abstract int size();

	/**
	 * Return the ith set maintained by this familySet.
	 * 
	 * @param i
	 *            an integer representing the ith set.
	 * @return the ith set. This method does not check if i >= size. In this
	 *         case it will throw an exception.
	 */
	public abstract SetMaintainer<T> getSet(int i);
	
	/**
	 * Find a set maintainer equals to this one.
	 * @param element
	 * @return
	 */
	public SetMaintainer<T> getEqual(SetMaintainer<T> otherSet); 
}
