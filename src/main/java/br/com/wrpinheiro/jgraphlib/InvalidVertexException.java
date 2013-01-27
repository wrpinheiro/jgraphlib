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
 * Exception throwed when trying to access or set vertex in the graph.
 * 
 * @author wrp
 */
public class InvalidVertexException extends RuntimeException {

	private static final long serialVersionUID = -2773726727343963145L;

	public InvalidVertexException() {
		super("Error accessing/setting the required vertex.");
	}
}
