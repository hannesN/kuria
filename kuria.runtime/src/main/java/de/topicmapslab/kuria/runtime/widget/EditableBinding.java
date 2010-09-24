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
package de.topicmapslab.kuria.runtime.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * The {@link EditableBinding} is the binding which connects a class to its different {@link PropertyBinding}s.
 * <p>A generator takes this Binding to generate a input mask for this binding. It creates a control to edit every 
 * property with an bound by a {@link PropertyBinding}.</p>
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class EditableBinding implements IEditableBinding {

	private Class<?> objectType;
	private List<IPropertyBinding> propertieBindings;

	private IEditableBinding parentBinding;
	
	/**
	 *  {@inheritDoc}
	 */
	public List<IPropertyBinding> getPropertieBindings() {
		if (propertieBindings == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(propertieBindings);
	}

	/**
	 * Adds a new {@link PropertyBinding} to the list.
	 * <p>If the binding is already in the list, it is added again.</p>
	 * @param b the new {@link PropertyBinding}
	 */
	public void addPropertyBinding(PropertyBinding b) {
		if (propertieBindings == null) {
			propertieBindings = new ArrayList<IPropertyBinding>();
		}
		propertieBindings.add(b);
	}
	
	/**
	 * Removes a {@link PropertyBinding} from the list.
	 * <p>If the given {@link PropertyBinding} is more in the list more than one time
	 * only the first element in the list will be removed.</p>
	 * @param b the {@link PropertyBinding} to remove
	 */
	public void removePropertyBinding(PropertyBinding b) {
		if (propertieBindings != null) {
			propertieBindings.remove(b);
		}
	}
	
	/**
	 * Returns the bound type.
	 * @return the bound type
	 */
	public Class<?> getObjectType() {
		return objectType;
	}
	
	
	/**
	 * Sets the bound type.
	 * <p>Make sure all {@link PropertyBinding}s in this binding bind fields in this type.
	 * @param objectType the bound type
	 */
	public void setObjectType(Class<?> objectType) {
		this.objectType = objectType;
	}

	/**
	 * Sets the parent binding o the type
	 * @param parentBinding the new parent binding
	 */
	public void setParentBinding(IEditableBinding parentBinding) {
	    this.parentBinding = parentBinding;
    }
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	public IEditableBinding getParentBinding() {
		return parentBinding;
	}
}
