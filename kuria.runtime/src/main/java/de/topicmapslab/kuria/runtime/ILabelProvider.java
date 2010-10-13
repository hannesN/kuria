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
package de.topicmapslab.kuria.runtime;

/**
 * A label provider is registered in a {@link IBindingContainer}. The provider transforms labels from bindings to another
 * label, which may be an internationalized one.
 * 
 * @author Hannes Niederhausen
 *
 */
public interface ILabelProvider {

	
	/**
	 * Returns a label based on the given label or if no transformation is specified the parameter will be returned.
	 * 
	 * @param label the label to transform must not be <code>null</code>
	 * @return  the transformed or the source label
	 */
	public String getLabel(String label);
	
}
