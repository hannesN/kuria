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
package de.topicmapslab.kuria.runtime.widget;

import java.util.List;

import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.PropertyBinding;

public interface IEditableBinding {

	/**
	 * Returns the {@link IPropertyBinding}s of this binding.
	 * <p>This list is unmodifiable. </p>
	 * 
	 * @return an unmodifiable list of the {@link PropertyBinding}s
	 */
	public abstract List<IPropertyBinding> getPropertieBindings();

	/**
	 * If a class is a inherited class of an annotated superclass it the value
	 * of this method returns its binding.
	 * 
	 * @return the binding of the super class if exists or <code>null</code>
	 */
	public abstract IEditableBinding getParentBinding();

}
