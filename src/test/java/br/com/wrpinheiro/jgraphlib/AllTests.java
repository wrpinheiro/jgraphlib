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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.wrpinheiro.jgraphlib.base.test.GraphTest;
import br.com.wrpinheiro.jgraphlib.hittingset.test.ArrayFamilySetTest;
import br.com.wrpinheiro.jgraphlib.hittingset.test.MinimalHittingSetTest;
import br.com.wrpinheiro.jgraphlib.hittingset.test.SetMaintainerTest;
import br.com.wrpinheiro.jgraphlib.search.test.BreadthFirstSearchTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GraphTest.class,
        SetMaintainerTest.class,
        ArrayFamilySetTest.class,
        MinimalHittingSetTest.class,
        BreadthFirstSearchTest.class
        })
public class AllTests {
	// does not need implementation.
}
