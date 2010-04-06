/*******************************************************************************
 * Copyright 2010, Topic Maps Lab
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/**
 * 
 */
package de.topicmapslab.kuria.runtime;

/**
 * <p>Interface for the factory which creates the binding.</p>
 * 
 * <p>The binding can be created based on any meta data. </p> 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public interface IBindingFactory {
	
	/**
	 * Creates and returns an instance of {@link BindingContainer}.
	 * 
	 * @return the created instance of {@link BindingContainer}
	 */
	public IBindingContainer getBindingContainer();
}
