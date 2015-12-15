/*
 * Copyright 2006-2013 Wellington Ricardo Pinheiro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package br.com.wrpinheiro.jgraphlib.familyset;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ArrayFamilySet<T> implements FamilySet<T> {
    /**
     * The initial size of this familySet.
     */
    private static final int INITIAL_SIZE = 100;

    /**
     * A family of sets.
     */
    private List<SetMaintainer<T>> familySet;

    /**
     * Create an instance of familySet with the capacity of INITIAL_SIZE sets.
     */
    public ArrayFamilySet() {
        this.familySet = new ArrayList<SetMaintainer<T>>(INITIAL_SIZE);
    }

    /**
     * Create an instance of FamilySet with the sets familySet.
     * 
     * @param sets
     *            a set of sets.
     */
    public ArrayFamilySet(Set<T>... sets) {
        this();

        if (sets != null) {
            for (Set<T> sm : sets) {
                this.add(sm);
            }
        }
    }

    /**
     * Add a new set to this family set.
     * 
     * @param set
     *            a new set to be added.
     */
    public void add(Set<T> set) {
        SetMaintainer<T> s = new SetMaintainer<T>(set);

        if (this.contains(s))
            return;

        this.familySet.add(s);
    }

    /**
     * Return the list of sets maintained by this FamilySet. The family set will
     * never be null so, we don't need to check that in this method.
     * 
     * @return array of sets.
     */
    @SuppressWarnings("unchecked")
    public SetMaintainer<T>[] getFamilySet() {
        SetMaintainer<T>[] newFamilySet = (SetMaintainer<T>[]) new SetMaintainer<?>[this.size()];
        for (int i = 0; i < this.size(); i++) {
            SetMaintainer<T> set = this.familySet.get(i);
            if (set != null) {
                newFamilySet[i] = set.clone();
            } else {
                newFamilySet[i] = null;
            }
        }
        return newFamilySet;
    }

    /**
     * Returns a set whose intersection with otherSet is empty.
     * 
     * @param otherSet
     *            a set.
     * @return a set.
     */
    public SetMaintainer<T> findEmptyIntersection(SetMaintainer<T> otherSet) {
        for (int i = 0; i < this.size(); i++) {
            if (this.familySet.get(i).isEmptyIntersection(otherSet))
                return this.familySet.get(i).clone();
        }
        return new SetMaintainer<T>();
    }

    /**
     * Check if set is maintained by this familySet.
     * 
     * @param set
     *            a set.
     * @return TRUE if set is maintained by this familySet or FALSE otherwise.
     */
    public boolean contains(SetMaintainer<?> set) {
        for (int i = 0; i < this.size(); i++) {
            if (this.familySet.get(i).equals(set))
                return true;
        }
        return false;
    }

    /**
     * Return the number of sets maintained by this familySet.
     * 
     * @return the number of sets maintained by this familySet.
     */
    public int size() {
        return this.familySet.size();
    }

    /**
     * Return the ith set maintained by this familySet.
     * 
     * @param i
     *            an integer representing the ith set.
     * @return the ith set. This method does not check if i >= size. In this
     *         case it will throw an exception.
     */
    public SetMaintainer<T> getSet(int i) {
        return this.familySet.get(i);
    }

    /**
     * (non-Javadoc)
     * 
     * @see br.com.wrpinheiro.jgraphlib.hittingset.AbstractFamilySet#getFamilySetAsSet()
     */
    @SuppressWarnings("unchecked")
    public Set<T[]> getFamilySetAsSet() {
        Set<T[]> newSet = new LinkedHashSet<T[]>();
        SetMaintainer<T>[] sm = this.getFamilySet();

        for (SetMaintainer<T> s : sm) {
            T[] temp = null;
            if (s != null) {
                temp = (T[]) s.toArray(new Integer[0]);
            }
            newSet.add(temp);
        }

        return newSet;
    }

    /**
     * (non-Javadoc)
     * 
     * @see br.com.wrpinheiro.jgraphlib.familyset.FamilySet#getEqual(br.com.wrpinheiro.jgraphlib.familyset.SetMaintainer)
     */
    public SetMaintainer<T> getEqual(final SetMaintainer<T> otherSet) {
        for (int i = 0; i < this.size(); i++) {
            if (this.familySet.get(i).equals(otherSet))
                return this.familySet.get(i);
        }
        return null;
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
        if (!(obj instanceof ArrayFamilySet<?>))
            return false;

        ArrayFamilySet<?> other = (ArrayFamilySet<?>) obj;
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
